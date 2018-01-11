package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;

/**
 * This class implements  the following policy: a fixed amount + a Per hour rate.
 * It is a "sugar-syntactic version" of GlobalPricingPolicy configured with FixedPricingPolicy + PerHourPricingPolicy
 * <p>
 * In addition to a fixed amount, a customer is billed for each hour spent in the parking (a started hour is fully due)
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class FixedAmountAndPerHourPricingPolicy extends GlobalPricingPolicy {

    /**
     * Policy with a given hourly rate
     *
     * @param fixedAmountAsString the fixed amount
     * @param hourPriceAsString   the hourly rate
     */
    public FixedAmountAndPerHourPricingPolicy(String fixedAmountAsString, String hourPriceAsString) {

        this.add(new FixedPricingPolicy(fixedAmountAsString))
                .add(new PerHourPricingPolicy(hourPriceAsString));
    }
}
