package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.ParkingSlot;

/**
 * This interface defines the methods to handle the parking availabilities
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public interface ParkingAvailabilityManager {

    /**
     * Book a slot for a car
     *
     * @param car the car which needs a slot
     * @return the booked slot or null if no slot available
     */
    ParkingSlot bookSlot(Car car);

    /**
     * Indicate that the slot is available again
     *
     * @param slot the slot to be freed
     */
    void freeSlot(ParkingSlot slot);
}
