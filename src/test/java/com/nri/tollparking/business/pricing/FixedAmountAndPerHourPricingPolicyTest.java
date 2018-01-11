package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class FixedAmountAndPerHourPricingPolicyTest {

    private FixedAmountAndPerHourPricingPolicy pricingPolicy;

    @Before
    public void setUp() {
        pricingPolicy = new FixedAmountAndPerHourPricingPolicy("5.0", "3.47");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComputePrice() {

        ParkingSlotUsage slotUsage = Mockito.mock(ParkingSlotUsage.class);

        ZonedDateTime startDateTime = ZonedDateTime.now();

        Mockito.when(slotUsage.getStartDateTime()).thenReturn(startDateTime);

        // Expected price: 5.0 + 1*3.47 = 8.47
        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusSeconds(1));
        slotUsage.setEndDateTime(startDateTime);
        Assert.assertEquals(new BigDecimal("8.47"), pricingPolicy.computePrice(slotUsage));

        // Expected price: 5.0 + 2*3.47 =
        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusHours(2));
        Assert.assertEquals(new BigDecimal("11.94"), pricingPolicy.computePrice(slotUsage));

        // Expected price: 5.0 + 2*3.47 = 11.94
        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusMinutes(70));
        Assert.assertEquals(new BigDecimal("11.94"), pricingPolicy.computePrice(slotUsage));
    }
}
