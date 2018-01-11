package com.nri.tollparking.builder;

import com.nri.tollparking.business.ParkingAccountingManager;
import com.nri.tollparking.ParkingActivityManager;
import com.nri.tollparking.business.ParkingAvailabilityManager;
import com.nri.tollparking.business.SimpleParkingAvailabilityManager;
import com.nri.tollparking.business.pricing.FixedPricingPolicy;
import com.nri.tollparking.business.pricing.PricingPolicy;
import com.nri.tollparking.asset.Parking;

/**
 * Builder for a ParkingActivityManager
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingActivityManagerBuilder {

    /**
     * The associated parking
     */
    private Parking parking;

    /**
     * The pricing policy
     */
    private PricingPolicy pricingPolicy;

    /**
     * The availability manager
     */
    private ParkingAvailabilityManager parkingAvailabilityManager;

    /**
     * The accounting manager
     */
    private ParkingAccountingManager parkingAccountingManager;

    /**
     * Default Constructor
     */
    public ParkingActivityManagerBuilder() {

        pricingPolicy = new FixedPricingPolicy("0");

        Parking parking = new ParkingBuilder().createParking("default");
        parkingAvailabilityManager = new SimpleParkingAvailabilityManager(parking);

        parkingAccountingManager = new ParkingAccountingManager();
    }


    /**
     * Returns a new ParkingActivityManager configured with the set info
     *
     * @return a new ParkingActivityManager
     */
    public ParkingActivityManager createParkingActivity() {

        return new ParkingActivityManager(parking, pricingPolicy, parkingAvailabilityManager, parkingAccountingManager);
    }

    /**
     * Sets the managed parking
     *
     * @param parking the managed parking
     * @return the current builder itself
     */
    public ParkingActivityManagerBuilder setParking(Parking parking) {

        this.parking = parking;

        return this;
    }

    /**
     * Sets the associated pricing policy
     *
     * @param pricingPolicy the applicable pricing policy
     * @return the current builder itself
     */
    public ParkingActivityManagerBuilder setPricingPolicy(PricingPolicy pricingPolicy) {

        this.pricingPolicy = pricingPolicy;

        return this;
    }

    /**
     * Sets the availability manager
     *
     * @param parkingAvailabilityManager the used availability manager
     * @return the current builder itself
     */
    public ParkingActivityManagerBuilder setParkingAvailabilityManager(ParkingAvailabilityManager parkingAvailabilityManager) {

        this.parkingAvailabilityManager = parkingAvailabilityManager;

        return this;
    }

    /**
     * Sets the accounting manager
     *
     * @param parkingAccountingManager the used account manager
     * @return the current builder itself
     */
    public ParkingActivityManagerBuilder setParkingAccountingManager(ParkingAccountingManager parkingAccountingManager) {

        this.parkingAccountingManager = parkingAccountingManager;

        return this;
    }


}
