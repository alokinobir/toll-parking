package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a pricing policy which is composed of other pricing policies.
 * It delegates the computation of the price to the underlying pricing policies and sums all the prices.
 *
 * If no underlying policy, then the computed amount is 0.
 *
 * @author nribaud
 * @version 1.0
 */
public class GlobalPricingPolicy implements PricingPolicy {

    private final List<PricingPolicy> pricingPolicies;

    public GlobalPricingPolicy() {

        pricingPolicies = new ArrayList<>();

    }

    /**
     * Adds a new pricing policy
     *
     * @param pricingPolicy the pricing policy to be added
     * @return the current pricing policy instance
     */
    public GlobalPricingPolicy add(PricingPolicy pricingPolicy) {

        pricingPolicies.add(pricingPolicy);

        return this;
    }


    @Override
    public BigDecimal computePrice(ParkingSlotUsage slotUsage) {

        BigDecimal totalAmount = new BigDecimal("0");
        for (PricingPolicy pricingPolicy : pricingPolicies) {
            totalAmount = totalAmount.add(pricingPolicy.computePrice(slotUsage));
        }

        return totalAmount;
    }

    @Override
    public String toString() {
        return "GlobalPricingPolicy{" +
                "pricingPolicies=" + pricingPolicies +
                '}';
    }
}
