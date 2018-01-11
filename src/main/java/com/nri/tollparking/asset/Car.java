package com.nri.tollparking.asset;

import java.util.Objects;

/**
 * Class which represents a Car.
 * <p>
 * In this simple asset, we only represent the license plate and the engine type of the car
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class Car {

    /**
     * The car license plate
     * Uniquely identifies the car
     */
    private final String licensePlate;

    /**
     * The car engine type
     */
    private final CarEngineType engineType;

    /**
     * Car owner
     */
    private final Customer owner;

    /**
     *
     * @param licensePlate the car license plate
     * @param engineType the engine type
     * @param owner the car owner
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public Car(String licensePlate, CarEngineType engineType, Customer owner) {

        if (licensePlate == null || licensePlate.isEmpty() || engineType == null || owner == null) {
            throw new IllegalArgumentException("Missing mandadtory arguments");

        }

        this.licensePlate = licensePlate;
        this.engineType = engineType;
        this.owner = owner;
    }

    /**
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @return the engine type
     */
    public CarEngineType getEngineType() {
        return engineType;
    }

    /**
     * @return the car owner
     */
    public Customer getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(licensePlate);
    }

    @Override
    public String toString() {

        return "Car{" +
                "licensePlate='" + licensePlate + '\'' +
                ", engineType=" + engineType +
                ", owner=" + owner +
                '}';
    }
}
