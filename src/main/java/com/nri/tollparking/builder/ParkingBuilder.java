package com.nri.tollparking.builder;

import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.asset.ParkingSlotType;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for a Parking
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingBuilder {

    /**
     * the associated parking slots
     */
    List<ParkingSlot> parkingSlots;

    /**
     * Default constructor
     */
    public ParkingBuilder() {
        parkingSlots = new ArrayList<ParkingSlot>();
    }

    /**
     * Create a parking base in the information set in the builder
     *
     * @param name name of the parking
     * @return a new Parking contains all the previously added ParkingSlots
     */
    public Parking createParking(String name) {

        return new Parking(name, parkingSlots);
    }

    /**
     * Add a parking slot with  slot type
     *
     * @param slotType type of the slot to be added
     * @return the current builder instance
     */
    public ParkingBuilder addParkingSlot(ParkingSlotType slotType) {
        int nextSlotId = parkingSlots.size();
        ParkingSlot slot = new ParkingSlot(nextSlotId, slotType);

        return this;
    }

    /**
     * Add ParkingSlots in batch
     *
     * @param slotsCount number of slots to add
     * @param slotType type of slot to add
     * @return the current builder instance
     */
    public ParkingBuilder addParkingSlots(int slotsCount, ParkingSlotType slotType) {

        int nextSlotId = parkingSlots.size();
        for (int i = nextSlotId; i < nextSlotId + slotsCount; i++) {

            ParkingSlot slot = new ParkingSlot(i, slotType);
            parkingSlots.add(slot);
        }

        return this;
    }

}
