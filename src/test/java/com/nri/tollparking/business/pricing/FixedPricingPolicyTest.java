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

@RunWith(MockitoJUnitRunner.class)
public class FixedPricingPolicyTest {

    private FixedPricingPolicy pricingPolicy;

    @Before
    public void setUp() throws Exception {

        pricingPolicy = new FixedPricingPolicy("5.28");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testComputePrice() {

        ParkingSlotUsage mockedSlotUsage = Mockito.mock(ParkingSlotUsage.class);

        BigDecimal price = pricingPolicy.computePrice(mockedSlotUsage);
        Assert.assertEquals(new BigDecimal("5.28"), price);

        try {
            pricingPolicy.computePrice(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }
}