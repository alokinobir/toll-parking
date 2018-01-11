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
public class PerHourPricingPolicyTest {

    private PerHourPricingPolicy pricingPolicy;

    @Before
    public void setUp() {
        pricingPolicy = new PerHourPricingPolicy("5.28");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComputePrice() {

        ParkingSlotUsage slotUsage = Mockito.mock(ParkingSlotUsage.class);

        ZonedDateTime startDateTime = ZonedDateTime.now();

        Mockito.when(slotUsage.getStartDateTime()).thenReturn(startDateTime);

        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusSeconds(1));
        slotUsage.setEndDateTime(startDateTime);
        Assert.assertEquals(new BigDecimal("5.28"), pricingPolicy.computePrice(slotUsage));

        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusHours(2));
        Assert.assertEquals(new BigDecimal("10.56"), pricingPolicy.computePrice(slotUsage));

        Mockito.when(slotUsage.getEndDateTime()).thenReturn(startDateTime.plusMinutes(70));
        Assert.assertEquals(new BigDecimal("10.56"), pricingPolicy.computePrice(slotUsage));
    }

    @Test
    public void testConstructor() {

        try {
            new PerHourPricingPolicy("5.234");
        } catch (ArithmeticException e) {
            Assert.assertEquals("Rounding necessary", e.getMessage());
        }
    }
}
