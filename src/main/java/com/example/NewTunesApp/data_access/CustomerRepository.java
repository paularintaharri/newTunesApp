package com.example.NewTunesApp.data_access;

import com.example.NewTunesApp.models.Customer;
import com.example.NewTunesApp.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CustomerRepository {

    private Logger logger = new Logger();

    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    //Get all customers
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            // Connect to DB
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            // Make SQL query
            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email FROM Customer");
            // Execute Query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getInt("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        ));
            }
            logger.log("Select all customers successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return customers;
    }

    //Add new customer
    public Boolean addCustomer(Customer customer) {
        Boolean success = false;
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO customer(CustomerId, FirstName, LastName, Country, PostalCode, Phone, Email) VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getCountry());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhone());
            preparedStatement.setString(7, customer.getEmail());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            logger.log("Add customer successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return success;
    }

    //Update an existing customer information
    public Boolean updateCustomer(Customer customer) {
        Boolean success = false;
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("UPDATE customer SET FirstName = ?, LastName = ?, Country = ?, PostalCode = ?, Phone = ?, Email = ? WHERE customerId=?");
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCountry());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getEmail());
            preparedStatement.setInt(7, customer.getCustomerId());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);
            logger.log("Update customer successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return success;
    }

    //Get number of customers in each country
    public LinkedHashMap<String, Integer> getAllCustomersCountryTotal() {
        LinkedHashMap<String, Integer> customersCountry = new LinkedHashMap<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT DISTINCT Country, COUNT(*) FROM Customer  GROUP BY Country ORDER BY count(CustomerId) DESC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customersCountry.put(
                        resultSet.getString("Country"),
                        resultSet.getInt("Count(*)")
                );
            }
            logger.log("Return number of customer in each country successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return customersCountry;
    }

    //Get highest spender customer
    public LinkedHashMap<String, Double> getHighestSpenders() {
        LinkedHashMap<String, Double> highestSpender = new LinkedHashMap<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT Customer.FirstName, Customer.LastName, SUM(Invoice.Total) FROM Customer INNER JOIN Invoice WHERE Customer.CustomerId = Invoice.CustomerId GROUP BY Invoice.CustomerId ORDER BY SUM(Invoice.Total) DESC;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String custName = resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
                highestSpender.put(
                        custName,
                        resultSet.getDouble("SUM(Invoice.Total)")
                );
            }
            logger.log("Return highest spender customers successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return highestSpender;
    }

    //Get most popular genre for given customer
    public LinkedHashMap<String, Integer> getMostPopularGenre(int id) {
        LinkedHashMap<String, Integer> popularGenre = new LinkedHashMap<>();
        try {
            conn = DriverManager.getConnection(URL);
            logger.log("Connection to SQLite has been established.");

            PreparedStatement preparedStatement =
                    conn.prepareStatement("WITH CountQuery AS (SELECT c.FirstName, c.LastName, g.Name,\n" +
                            "count(g.GenreId) as GenreCount FROM Customer AS c\n" +
                            "JOIN Invoice AS iv ON iv.CustomerId = c.CustomerId\n" +
                            "JOIN InvoiceLine AS il ON il.InvoiceId = iv.InvoiceId\n" +
                            "JOIN Track AS t ON t.TrackId = il.TrackId\n" +
                            "JOIN Genre AS g ON g.GenreId = t.GenreId\n" +
                            "WHERE c.CustomerId=? GROUP BY g.GenreId\n" +
                            "ORDER BY GenreCount) SELECT FirstName, LastName, Name, GenreCount\n" +
                            "FROM CountQuery WHERE (SELECT MAX(GenreCount) from CountQuery) = GenreCount");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                    popularGenre.put(
                            resultSet.getString("Name"),
                            resultSet.getInt("GenreCount")
                    );
            }
            logger.log("Return most popular genre successful");
        } catch (Exception exception) {
            logger.log(exception.toString());
        } finally {
            try {
                conn.close();
            } catch (Exception exception) {
                logger.log(exception.toString());
            }
        }
        return popularGenre;
    }
}