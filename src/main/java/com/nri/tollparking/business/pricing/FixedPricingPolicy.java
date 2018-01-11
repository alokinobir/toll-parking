package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;

/**
 * This class implement a Pricing policy which does charge a fix amount
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class FixedPricingPolicy implements PricingPolicy {

    /**
     * The fixed amount of this policy
     */
    private final BigDecimal fixedAmount;

    public FixedPricingPolicy(String fixedAmountAsString) {

        this.fixedAmount = new BigDecimal(fixedAmountAsString).setScale(BIG_DECIMAL_SCALE);
    }

    @Override
    public BigDecimal computePrice(ParkingSlotUsage slotUsage) {

        if (slotUsage == null) {
            throw new IllegalArgumentException("slotUsage cannot be null");
        }

        return this.fixedAmount;
    }

    @Override
    public String toString() {
        return "FixedPricingPolicy{" +
                "fixedAmount=" + fixedAmount +
                '}';
    }
}
