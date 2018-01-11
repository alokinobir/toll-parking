package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.asset.ParkingSlotState;

import java.util.HashMap;
import java.util.logging.Logger;

import static com.nri.tollparking.asset.ParkingSlotState.BUSY;

/**
 * This classes implements a simple availability manager.
 * The rule is "First come, first served".
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class SimpleParkingAvailabilityManager implements ParkingAvailabilityManager {

    private static Logger LOG = Logger.getLogger(SimpleParkingAvailabilityManager.class.getName());

    // List of available parking slots
    private HashMap<Integer, ParkingSlot> slots;

    /**
     * Build a simple availability manager
     *
     * @param parking the parking for which we manage the availabilities
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public SimpleParkingAvailabilityManager(Parking parking) {

        if (parking == null) {
            throw new IllegalArgumentException("parking must no be null");
        }

        slots = new HashMap<Integer, ParkingSlot>();

        for (ParkingSlot slot : parking.getParkingSlots()) {

            slots.put(slot.getSlotId(), slot);

        }
    }

    @Override
    public ParkingSlot bookSlot(Car car) {

        if (car == null) {
            throw new IllegalArgumentException("car must no be null");
        }

        for (ParkingSlot slot : slots.values()) {

            if (slot != null && slot.getSlotState() == ParkingSlotState.FREE) {
                slot.setSlotState(ParkingSlotState.BUSY);
                return slot;
            }
        }

        // No availability
        return null;
    }

    /**
     * Note: A warning message is printed in the logs is the slot is not found in the internal store
     *
     * @param slot the slot to be freed
     */
    @Override
    public void freeSlot(ParkingSlot slot) {

        if (slot == null) {
            throw new IllegalArgumentException("slot must not be null");
        }

        if (slots.get(slot.getSlotId()) != null) {
            slots.get(slot.getSlotId()).setSlotState(ParkingSlotState.FREE);
        } else {
            LOG.warning(String.format("Slot %d not found in internal store", slot.getSlotId()));
        }
    }
}
