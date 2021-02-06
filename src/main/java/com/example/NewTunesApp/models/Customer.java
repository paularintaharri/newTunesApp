package com.example.NewTunesApp.models;

public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    public Customer(){

    }

    public Customer(int customerId, String firstName, String lastName, String country, String postalCode, String phone, String email){
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
    }

    public int getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

}