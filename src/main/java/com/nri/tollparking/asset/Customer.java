package com.nri.tollparking.asset;

import com.nri.tollparking.business.pricing.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class which represents a Customer.
 *
 * A customer has a name and receives bills for its parking usages
 *
 * @author Nicolas  Ribaud
 * @version 1.0
 */
public class Customer {

    /**
     * Name of the customer
     */
    private final String name;

    /**
     * In this simple asset we simply store the list of bills.
     */
    private final List<Bill> bills;

    /**
     * Construct a customer with the given parmaters
     *
     * @param name name of the cutomer
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public Customer(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name is mandatory");
        }

        this.name = name;
        this.bills = new ArrayList<Bill>();
    }

    /**
     * @return the name of the customer
     */
    public String getName() {

        return this.name;
    }

    /**
     * Add a bill
     *
     * @param bill bill recevied by the customer
     */
    public void addBill(Bill bill) {

        this.bills.add(bill);
    }

    /**
     * @return the list of bills received by the customer
     */
    public List<Bill> getBills() {

        return new ArrayList<>(bills);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                '}';
    }
}
