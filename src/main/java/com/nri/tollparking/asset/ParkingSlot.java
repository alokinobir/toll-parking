package com.nri.tollparking.asset;

import java.util.Objects;

/**
 * ParkingSlot is a class which represents a parking slot.
 * <p>
 * In this simple asset, the parking slot only characteristic is
 * <p>
 * - a slot type.
 * <p>
 * - a state (FREE or BUSY).
 * <p>
 *
 * It is uniquely identifiable by its slotId.
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingSlot {

    /**
     * The unique indentifier of the parking slot (in the parking
     */
    private final int slotId;

    /**
     * The parking slotType
     */
    private final ParkingSlotType slotType;

    /**
     * the status of the slot
     */
    private ParkingSlotState slotState;

    /**
     * Constructs a parking slot with the given parameters
     *
     * @param slotId   the unique identifier of the slot
     * @param slotType the type of the slot
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public ParkingSlot(int slotId, ParkingSlotType slotType) {

        if (slotId < 0 || slotType == null) {
            throw new IllegalArgumentException("slotId must be > 0 and slotType must not be null");
        }

        this.slotId = slotId;
        this.slotType = slotType;
        this.slotState = ParkingSlotState.FREE;
    }

    /**
     * @return the slot id
     */
    public int getSlotId() {
        return slotId;
    }

    /**
     * @return the slot type
     */
    public ParkingSlotType getSlotType() {
        return slotType;
    }

    /**
     *
     * @return the slot state
     */
    public ParkingSlotState getSlotState() {
        return slotState;
    }

    /**
     * Set the slot state (FREE, BUSY)
     *
     * @param slotState the state of the slot
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setSlotState(ParkingSlotState slotState) {
        if (slotState == null) {
            throw new IllegalArgumentException("slotState cannot be null");
        }

        this.slotState = slotState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlot that = (ParkingSlot) o;
        return slotId == that.slotId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(slotId);
    }

    @Override
    public String toString() {

        return "ParkingSlot{" +
                "slotId=" + slotId +
                ", slotType=" + slotType +
                ", slotState=" + slotState +
                '}';
    }
}
