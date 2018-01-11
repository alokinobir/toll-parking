package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.business.pricing.PricingPolicy;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * This class represents the usage of a parking slot
 * It has:
 * - a Parking slot.
 * <p>
 * - A car parked on the parking slot.
 * <p>
 * - A customer associated to it.
 * <p>
 * - The billing policy which is applied.
 * <p>
 * - A start date.
 * <p>
 * - A end date.
 * <p>
 * Start and end date times of the usage can be modified in case some kind of regulation is needed.
 * <p>
 * Note: For start and end date time, ZonedDateTime is used to correctly handle Daylight Saving Time
 * when computing the duration between the two dates.
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingSlotUsage {

    /**
     * The parking containing the ParkingSlot
     */
    private final Parking parking;

    /**
     * The parking slot involved in the slot usage
     */
    private final ParkingSlot parkingSlot;

    /**
     * The car involved in the slot usage
     */
    private final Car car;

    /**
     * The customer who is using the parking slot
     */
    private final Customer customer;

    /**
     * The pricing policy applicable for the slot usage
     */
    private final PricingPolicy pricingPolicy;

    /**
     * start date and time of the usage
     */
    private ZonedDateTime startDateTime;

    /**
     * end date and time of the usage
     */
    private ZonedDateTime endDateTime;

    /**
     * Constructs a usage starting at a given startDateTime
     * The endDateTime is not know yet and remains null.
     *
     * @param parkingSlot   the used parking slot
     * @param parking       the parking
     * @param car           the car which uses the slot
     * @param customer      the customer to be billed
     * @param pricingPolicy the application pricing policy
     * @param startDateTime when the usage starts
     */
    public ParkingSlotUsage(ParkingSlot parkingSlot, Parking parking, Car car, Customer customer, PricingPolicy pricingPolicy, ZonedDateTime startDateTime) {
        this(parkingSlot, parking, car, customer, pricingPolicy, startDateTime, null);
    }

    /**
     * Constructs a usage starting at the specified date and finishing at the specified date
     *
     * @param parkingSlot   the used parking slot
     * @param parking       the parking
     * @param car           the car which uses the slot
     * @param customer      the customer to be billed
     * @param pricingPolicy the application pricing policy
     * @param startDateTime when the usage starts
     * @param endDateTime   when the usage ends
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public ParkingSlotUsage(ParkingSlot parkingSlot, Parking parking, Car car, Customer customer, PricingPolicy pricingPolicy, ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        if (parkingSlot == null || parking == null || car == null || customer == null || pricingPolicy == null || startDateTime == null) {
            throw new IllegalArgumentException("Missing mandatory arguments");
        }

        this.parkingSlot = parkingSlot;
        this.parking = parking;
        this.car = car;
        this.customer = customer;
        this.pricingPolicy = pricingPolicy;

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * @return the slot used
     */
    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    /**
     * @return the associated parking
     */
    public Parking getParking() {
        return parking;
    }

    /**
     * @return the car parked on the slot
     */
    public Car getCar() {
        return car;
    }

    /**
     * @return the custumer using the parking
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return the pricing policy of the usage
     */
    public PricingPolicy getPricingPolicy() {
        return pricingPolicy;
    }

    /**
     * @return the start date time of the usage
     */
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * @param startDateTime the start date time of the usage
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setStartDateTime(ZonedDateTime startDateTime) {
        if (startDateTime == null) {
            throw new IllegalArgumentException("startDateTime cannot be null");

        }
        this.startDateTime = startDateTime;
    }

    /**
     * @return the endDateTime. Null is not set yet
     */
    public ZonedDateTime getEndDateTime() {

        return endDateTime;
    }

    /**
     * @param endDateTime the end date time of the usage (null if not set yet)
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setEndDateTime(ZonedDateTime endDateTime) {
        if (endDateTime == null) {
            throw new IllegalArgumentException("endDateTime cannot be null");

        }

        this.endDateTime = endDateTime;
    }

    @Override
    public boolean equals(Object o) {

        // Do not take into account the end date time
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlotUsage that = (ParkingSlotUsage) o;
        return Objects.equals(parking, that.parking) &&
                Objects.equals(parkingSlot, that.parkingSlot) &&
                Objects.equals(car, that.car) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(pricingPolicy, that.pricingPolicy) &&
                Objects.equals(startDateTime, that.startDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parking, parkingSlot, car, customer, pricingPolicy, startDateTime);
    }

    @Override
    public String toString() {
        return "ParkingSlotUsage{" +
                "parking=" + parking +
                ", parkingSlot=" + parkingSlot +
                ", car=" + car +
                ", customer=" + customer +
                ", pricingPolicy=" + pricingPolicy +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
