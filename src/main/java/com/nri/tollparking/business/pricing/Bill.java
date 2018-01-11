package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;

/**
 * This class represents a Bill.
 * A bill has an amount which corresponds to Parking slot usage
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class Bill {

    /**
     * The amount of the bill
     */
    private final BigDecimal amount;

    /**
     * The corresponding usage
     */
    private final ParkingSlotUsage slotUsage;

    /**
     * Construct a new bill
     *
     * @param amount    the bill amount
     * @param slotUsage the parking usage
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public Bill(BigDecimal amount, ParkingSlotUsage slotUsage) {
        if (amount == null || slotUsage == null) {
            throw new IllegalArgumentException("amount and slotUsage must not be null");
        }

        this.amount = amount;
        this.slotUsage = slotUsage;
    }

    /**
     * @return the bill amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return the parking usage
     */
    public ParkingSlotUsage getSlotUsage() {
        return slotUsage;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "amount=" + amount +
                ", slotUsage=" + slotUsage +
                '}';
    }
}
