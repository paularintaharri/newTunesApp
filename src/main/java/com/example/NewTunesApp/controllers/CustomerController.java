package com.example.NewTunesApp.controllers;

import com.example.NewTunesApp.data_access.CustomerRepository;
import com.example.NewTunesApp.models.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@RestController
public class CustomerController {
    private CustomerRepository customerRepository = new CustomerRepository();

    //Returns all the customers in the database
    @RequestMapping(value="/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }

    //Adds a new customer.It takes the new customer from the body of the request.
    @RequestMapping(value = "/api/customers", method = RequestMethod.POST)
    public Boolean addNewCustomer(@RequestBody Customer customer){
        return customerRepository.addCustomer(customer);
    }

    //Updates an existing customer. It takes the new customer data from the body
    @RequestMapping(value = "/api/customers", method = RequestMethod.PUT)
    public Boolean updateExistingCustomer(@RequestBody Customer customer){
        return customerRepository.updateCustomer(customer);
    }

    //Return the number of customer in each country, order descending
    @RequestMapping(value="/api/customers/country/total", method = RequestMethod.GET)
    public LinkedHashMap<String, Integer> getAllCustomersCountryTotal(){
        return customerRepository.getAllCustomersCountryTotal();
    }

    //Return the highest spenders
    @RequestMapping(value="/api/customers/max/spender", method = RequestMethod.GET)
    public LinkedHashMap<String, Double> getHighestSpenders(){
        return customerRepository.getHighestSpenders();
    }

    //Return the most popular genre for given customer
    @RequestMapping(value="/api/customers/popular/genre/{CustomerId}", method = RequestMethod.GET)
    public LinkedHashMap<String, Integer> getMostPopularGenre(@PathVariable int CustomerId){
        return customerRepository.getMostPopularGenre(CustomerId);
    }
}