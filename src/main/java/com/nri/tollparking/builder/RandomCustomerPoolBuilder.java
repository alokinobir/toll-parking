package com.nri.tollparking.builder;

import com.nri.tollparking.asset.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder for a pool of random customer.
 *
 * Each customer is assigned a unique name whose pattern is similar to "Customer_231".
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class RandomCustomerPoolBuilder {

    /**
     * The customer pool
     */
    private List<Customer> customers;


    /**
     * Default constructor
     */
    public RandomCustomerPoolBuilder() {

        this.customers = new ArrayList<>();
    }

    /**
     * @return a random list of customers
     */
    public List<Customer> createCustomerPool() {
        // Make a copy of the internal list
        List<Customer> shuffledCustomers = new ArrayList<>(this.customers);
        Collections.shuffle(shuffledCustomers);

        return shuffledCustomers;
    }

    /**
     * Add customers in batch
     *
     * @param customersCount the number of customers to create
     * @return the current builder instance
     */
    public RandomCustomerPoolBuilder addCustomers(int customersCount) {

        int nextCustomerId = customers.size();

        for (int i = nextCustomerId; i < nextCustomerId + customersCount; i++) {
            Customer customer = new Customer("Customer_" + i);
            customers.add(customer);
        }

        return this;
    }
}
