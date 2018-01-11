package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * This class implements a  Per hour Pricing Policy.
 * A customer is billed for each hour spent in the parking (a started hour is fully due)
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class PerHourPricingPolicy implements PricingPolicy {


    /**
     * Hourly rate
     */
    private final BigDecimal hourPrice;

    /**
     * Policy with a given hourly rate
     *
     * @param hourPriceAsString the hourly rate (maximum 2 decimals after the point
     */
    public PerHourPricingPolicy(String hourPriceAsString) {

        this.hourPrice = new BigDecimal(hourPriceAsString).setScale(BIG_DECIMAL_SCALE);
    }

    @Override
    public BigDecimal computePrice(ParkingSlotUsage slotUsage) {
        if (slotUsage == null) {
            throw new IllegalArgumentException("slotUsage cannot be null");
        }

        // Started hour is counted as a whole (to the second precise)
        long durationInSeconds = ChronoUnit.SECONDS.between(slotUsage.getStartDateTime(), slotUsage.getEndDateTime());
        long hours = durationInSeconds / ONE_HOUR_IN_SECONDS;
        if (durationInSeconds % ONE_HOUR_IN_SECONDS != 0) {
            hours++;
        }

        return hourPrice.multiply(new BigDecimal(hours));
    }

    @Override
    public String toString() {
        return "PerHourPricingPolicy{" +
                "hourPrice=" + hourPrice +
                '}';
    }
}
