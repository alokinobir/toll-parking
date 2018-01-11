package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

/**
 * PricingPolicy interface.
 *
 * A pricing policy computes a price for given usage.
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public interface PricingPolicy {

    public final long ONE_HOUR_IN_SECONDS = ChronoUnit.HOURS.getDuration().getSeconds();

    /**
     * 2 decimals after the coma
     */
    public static final int BIG_DECIMAL_SCALE = 2;

    /**
     * Compute the price based on the slot usage
     *
     * @param slotUsage usage information to compute the price
     * @return the billed amount the usage
     */
    public BigDecimal computePrice(ParkingSlotUsage slotUsage);
}
