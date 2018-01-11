package com.nri.tollparking.builder;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.CarEngineType;
import com.nri.tollparking.asset.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Builder for fleet of random cars.
 * Each car gets a unique increasing license plate. Ex: CAR_154
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class RandomCarFleetBuilder {

    /**
     * The cars in the fleet
     */
    private List<Car> cars;

    /**
     * The customer pool from which we pick
     */
    private ArrayList<Customer> customers;


    /**
     * A car will be associated to customer picked at random from the customer pool
     *
     * @param customers the customer pool from which we randomly pick to be the owner of the created cars
     */
    public RandomCarFleetBuilder(List<Customer> customers) {

        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>(customers);
    }

    /**
     * @return a random list of car
     */
    public List<Car> createCarFleet() {
        // Make a copy of the internal list
        List<Car> shuffledCars = new ArrayList<>(this.cars);
        Collections.shuffle(shuffledCars);

        return shuffledCars;
    }

    /**
     * Add cars of type engineType in batch
     *
     * @param carsCount  the number of cars to create
     * @param engineType the engineType of the cars
     * @return the current instance
     */
    public RandomCarFleetBuilder addCars(int carsCount, CarEngineType engineType) {

        int nextCarId = cars.size();

        Random randomCustomer = new Random();

        for (int i = nextCarId; i < nextCarId + carsCount; ++i) {
            Car car = new Car("CAR_" + i, engineType, customers.get(randomCustomer.nextInt(customers.size())));
            cars.add(car);
        }

        return this;
    }
}
