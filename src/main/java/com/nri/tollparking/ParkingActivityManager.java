package com.nri.tollparking;

import com.nri.tollparking.business.ParkingAccountingManager;
import com.nri.tollparking.business.ParkingAvailabilityManager;
import com.nri.tollparking.business.ParkingSlotUsage;
import com.nri.tollparking.business.pricing.Bill;
import com.nri.tollparking.business.pricing.PricingPolicy;
import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class is the API entry point.
 *
 * It orchestrates the activities of a parking.
 * <p>
 * Cars can come in the parking if there are available slots.
 * Cars in the parking can leave.
 * <p>
 * A snapshot of the current slot usages can also be asked.
 * <p>
 * Business logic can also be changed if required:
 * <p>
 * - Pricing policy.
 * <p>
 * - Accounting manager.
 * <p>
 * - Availability manager.
 * <p>
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingActivityManager {

    private static Logger LOG = Logger.getLogger(ParkingActivityManager.class.getName());

    /**
     * The managed parking
     */
    private final Parking parking;

    /**
     * The current application pricing policy
     */
    private PricingPolicy pricingPolicy;

    /**
     * The accounting manager of the parking
     */
    private ParkingAccountingManager parkingAccountingManager;

    /**
     * The availability manager of the parking
     */
    private ParkingAvailabilityManager parkingAvailabilityManager;

    /**
     * The current slot usages
     */
    private final Map<Car, ParkingSlotUsage> parkingSlotUsages;


    /**
     * Construct a new parking manager
     *
     * @param parking the managed parking
     * @param pricingPolicy the applicable pricing policy
     * @param parkingAvailabilityManager the availability manager
     * @param parkingAccountingManager the accounting manager
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public ParkingActivityManager(Parking parking, PricingPolicy pricingPolicy, ParkingAvailabilityManager parkingAvailabilityManager, ParkingAccountingManager parkingAccountingManager) {

        if (parking == null || pricingPolicy == null || parkingAvailabilityManager == null || parkingAccountingManager == null) {
            throw new IllegalArgumentException("Missing mandatory arguments");
        }

        this.parking = parking;
        this.pricingPolicy = pricingPolicy;

        this.parkingAvailabilityManager = parkingAvailabilityManager;
        this.parkingAccountingManager = parkingAccountingManager;

        this.parkingSlotUsages = new HashMap<>();
    }

    /**
     * @return the managed parking
     */
    public Parking getParking() {

        return parking;
    }

    /**
     * @return the current pricing policy
     */
    public PricingPolicy getPricingPolicy() {

        return pricingPolicy;
    }

    /**
     * Changes the current pricing policy
     *
     * @param pricingPolicy pricing policy to be applied
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setPricingPolicy(PricingPolicy pricingPolicy) {

        if (pricingPolicy == null) {
            throw new IllegalArgumentException("policy cannot be null");
        }

        this.pricingPolicy = pricingPolicy;
    }

    /**
     * @return the current accounting manager
     */
    public ParkingAccountingManager getParkingAccountingManager() {

        return parkingAccountingManager;
    }

    /**
     * Set the parking accounting manager
     *
     * @param parkingAccountingManager the accounting manager to be used
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setParkingAccountingManager(ParkingAccountingManager parkingAccountingManager) {

        if (parkingAccountingManager == null) {
            throw new IllegalArgumentException("parkingAccountingManager cannot be null");
        }

        this.parkingAccountingManager = parkingAccountingManager;
    }

    /**
     * @return the current availability manager
     */
    public ParkingAvailabilityManager getParkingAvailabilityManager() {

        return parkingAvailabilityManager;
    }

    /**
     * Sets the current availability manager
     *
     * @param parkingAvailabilityManager the availability manager to be used
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public void setParkingAvailabilityManager(ParkingAvailabilityManager parkingAvailabilityManager) {

        if (parkingAvailabilityManager == null) {
            throw new IllegalArgumentException("parkingAvailabilityManager must not be null");
        }

        this.parkingAvailabilityManager = parkingAvailabilityManager;
    }

    /**
     * @return A snapshot of the current slot usages
     */
    public Map<Car, ParkingSlotUsage> getParkingSlotUsages() {

        return new HashMap<>(parkingSlotUsages);
    }

    /**
     * A cars comes in at a specified start date time
     *
     * @param car the cars which needs a slot
     * @param startDateTime the arrival time
     * @return A ParkingSlotUsage if the car has been assigned a slot, null if the car has been refused
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public ParkingSlotUsage comeIn(Car car, ZonedDateTime startDateTime) {

        if (car == null || startDateTime == null) {
            throw new IllegalArgumentException("car and startDatetime must not be null");
        }

        // Car already inside!
        if (parkingSlotUsages.containsKey(car)) {
            throw new IllegalStateException(String.format("Car %s is already in the parking!", car.getLicensePlate()));
        }

        // Try to book a slot
        ParkingSlot assignedSlot = parkingAvailabilityManager.bookSlot(car);
        if (assignedSlot != null) {
            // Add the usage in the local registry
            return addSlotUsage(assignedSlot, parking, car, car.getOwner(), pricingPolicy, startDateTime);
        }

        return null;
    }

    /**
     * A car comes out at a specified date time
     *
     * Note: warning message is printed in the logs if a corresponding slot usage is not found
     *
     * @param car the cars which leaves the parking
     * @param endDateTime when the cars leaves the parking
     * @return the corresponding slot usage
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public ParkingSlotUsage comeOut(Car car, ZonedDateTime endDateTime) {

        if (car == null || endDateTime == null) {
            throw new IllegalArgumentException("car and endDateTime must not be null");
        }

        ParkingSlotUsage slotUsage = removeSlotUsage(car, endDateTime);

        if (slotUsage != null) {
            parkingAvailabilityManager.freeSlot(slotUsage.getParkingSlot());
            Bill bill = parkingAccountingManager.billCustomer(slotUsage);
        } else {
            // Inconsistency error
            LOG.warning(String.format("Slot usage for car %s not found in internal registry", car.getLicensePlate()));
        }

        return slotUsage;
    }

    /**
     * Register a new slot usage
     *
     * @param parkingSlot the slot used
     * @param parking the corresponding parking
     * @param car the car which uses the slot
     * @param customer the customer to be billed
     * @param pricingPolicy the applicable pricing policy for this usage
     * @return the parking slot usage
     */
    private ParkingSlotUsage addSlotUsage(ParkingSlot parkingSlot, Parking parking, Car car, Customer customer, PricingPolicy pricingPolicy, ZonedDateTime startDate) {

        ParkingSlotUsage slotUsage = new ParkingSlotUsage(parkingSlot, parking, car, customer, pricingPolicy, startDate);
        parkingSlotUsages.put(car, slotUsage);

        return slotUsage;

    }

    /**
     * Terminates the slot usage
     *
     * @param car the car which leaves the parking
     * @param endDateTime when the car leaves
     * @return the ended slot usage
     */
    private ParkingSlotUsage removeSlotUsage(Car car, ZonedDateTime endDateTime) {

        ParkingSlotUsage slotUsage = parkingSlotUsages.remove(car);
        slotUsage.setEndDateTime(endDateTime);

        return slotUsage;

    }
}
