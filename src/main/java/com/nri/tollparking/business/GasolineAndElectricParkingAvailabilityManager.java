package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.asset.ParkingSlotState;
import com.nri.tollparking.asset.ParkingSlotType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * This classes implements a simple availability manager.
 *
 * It has the following rules:
 * <p>
 * - Slots are assigned based on the slot type and the car type.
 * <p>
 * - gasoline cars use standard slots.
 * <p>
 * - electric cars use parking slots with power supply.
 * <p>
 * - 20kw electric cars cannot use 50kw power supplies and vice-versa..
 * <p>
 * - For a given slot type, the one with lower slotId (if any) is used first.
 * <p>
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class GasolineAndElectricParkingAvailabilityManager implements ParkingAvailabilityManager {

    private static Logger LOG = Logger.getLogger(GasolineAndElectricParkingAvailabilityManager.class.getName());

    /**
     * Current usages
     */
    private List<ParkingSlotUsage> currentUsages;

    // List of available parking slots
    // Data structure optimized for parking slots management under the rules of this availability manager
    private Map<ParkingSlotType, PriorityQueue<ParkingSlot>> freeSlots;

    /**
     * Build a simple availability manager
     *
     * Note: warning message is printed in the logs if some type of slots are not sopported by this manager
     *
     * @param parking the parking for which we manage the availabilities
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public GasolineAndElectricParkingAvailabilityManager(Parking parking) {

        if (parking == null) {
            throw new IllegalArgumentException("parking must no be null");
        }

        // For a given type, available slots with lower id are used
        freeSlots = new HashMap<ParkingSlotType, PriorityQueue<ParkingSlot>>();

        Comparator<ParkingSlot> parkinSlotComparator = new Comparator<ParkingSlot>() {
            @Override
            public int compare(ParkingSlot ps1, ParkingSlot ps2) {

                return (ps1.getSlotId() < ps2.getSlotId()) ? -1 : ((ps1.getSlotId() == ps2.getSlotId()) ? 0 : 1);

            }
        };

        // Supported slot type
        freeSlots.put(ParkingSlotType.SEDAN_GASOLINE, new PriorityQueue<>(parkinSlotComparator));
        freeSlots.put(ParkingSlotType.POWER_SUPPLY_20KW, new PriorityQueue<>(parkinSlotComparator));
        freeSlots.put(ParkingSlotType.POWER_SUPPLY_50KW, new PriorityQueue<>(parkinSlotComparator));

        // Organize the parking slots to optimize the management of free slots
        for (ParkingSlot slot : parking.getParkingSlots()) {

            if (!freeSlots.containsKey(slot.getSlotType())) {
                LOG.warning(String.format("Slot with slotType %s not supported", slot.getSlotType().name()));
            } else {
                freeSlots.get(slot.getSlotType()).add(slot);
            }
        }

    }

    /**
     * Note: A warning is printed if a car engine type is not supported by this manager
     *
     * @param car the car which needs a slot
     * @return the booked slot or null if refused
     */
    @Override
    public ParkingSlot bookSlot(Car car) {

        if (car == null) {
            throw new IllegalArgumentException("car must no be null");
        }

        ParkingSlotType approriateParkingSlotType = null;

        switch (car.getEngineType()) {
            case GASOLINE:
                approriateParkingSlotType = ParkingSlotType.SEDAN_GASOLINE;
                break;
            case ELECTRIC_20KW:
                approriateParkingSlotType = ParkingSlotType.POWER_SUPPLY_20KW;
                break;
            case ELECTRIC_50KW:
                approriateParkingSlotType = ParkingSlotType.POWER_SUPPLY_50KW;
                break;
            default:
                LOG.warning(String.format("Car with engineType %s not supported", car.getEngineType().name()));
        }

        // Remove the slot from free slots
        PriorityQueue<ParkingSlot> slots = freeSlots.get(approriateParkingSlotType);
        ParkingSlot firstAvailableSlot = slots.poll();

        if (firstAvailableSlot != null) {
            firstAvailableSlot.setSlotState(ParkingSlotState.BUSY);
        }

        return firstAvailableSlot;
    }

    /**
     * Note: A warning is printed in the logs if the slot is not found in the internal registry
     *
     * @param slot the slot to be freed
     */
    @Override
    public void freeSlot(ParkingSlot slot) {

        if (slot == null) {
            throw new IllegalArgumentException("slot must not be null");
        }

        // Add the slot again to available slots
        if (freeSlots.containsKey(slot.getSlotType())) {
            freeSlots.get(slot.getSlotType()).add(slot);
            slot.setSlotState(ParkingSlotState.FREE);
        } else {
            LOG.warning(String.format("Slot type %s not found in internal store", slot.getSlotType().name()));
        }


    }

}
