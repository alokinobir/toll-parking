package com.nri.tollparking.business;

import com.nri.tollparking.business.pricing.Bill;

import java.math.BigDecimal;

/**
 * This classes manages the accounting of a parking
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class ParkingAccountingManager {

    /**
     * Default constructor
     */
    public ParkingAccountingManager() {

    }

    /**
     * Bill the customer for its parking usage
     *
     * @param slotUsage the slot usage to be billed
     * @return the billed amount
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public Bill billCustomer(ParkingSlotUsage slotUsage) {

        if (slotUsage == null) {
            throw new IllegalArgumentException("slotUsage must not be null");
        }

        BigDecimal price = slotUsage.getPricingPolicy().computePrice(slotUsage);

        Bill bill = new Bill(price, slotUsage);
        slotUsage.getCustomer().addBill(bill);

        return bill;
    }
}
