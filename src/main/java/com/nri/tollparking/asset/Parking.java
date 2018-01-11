package com.nri.tollparking.asset;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class which represents a Parking.
 *
 * A parking has a name and a list of parking slots
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class Parking {

    /**
     * Name of the customer
     */
    private final String name;

    /**
     * All the slots in the parking
     */
    private final List<ParkingSlot> parkingSlots;

    /**
     * Construct a customer with the given parmaters
     *
     * @param name name of the parking
     * @param parkingSlots all the parking slots of the parking
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public Parking(String name, List<ParkingSlot> parkingSlots) {
        if (name == null || name.isEmpty() || parkingSlots == null) {
            throw new IllegalArgumentException("Missing mandatory parameters");
        }

        this.name = name;
        this.parkingSlots = new ArrayList<ParkingSlot>(parkingSlots);
    }

    /**
     * @return the name of the customer
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return a copy of the list of all parking slots
     */
    public List<ParkingSlot> getParkingSlots() {

        return new ArrayList<ParkingSlot>(this.parkingSlots);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return Objects.equals(name, parking.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Parking{" +
                "name='" + name + '\'' +
                '}';
    }
}
