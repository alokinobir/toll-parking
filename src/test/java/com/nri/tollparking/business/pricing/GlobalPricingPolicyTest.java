package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class GlobalPricingPolicyTest {

    @Mock
    PricingPolicy mockPricingPolicy1;

    @Mock
    PricingPolicy mockPricingPolicy2;

    @Mock
    ParkingSlotUsage mockParkingSlotUsage;


    private GlobalPricingPolicy globalPricingPolicy;

    @Before
    public void setUp() throws Exception {

        Mockito.when(mockPricingPolicy1.computePrice(Matchers.any())).thenReturn(new BigDecimal("3.48"));
        Mockito.when(mockPricingPolicy2.computePrice(Matchers.any())).thenReturn(new BigDecimal("7.13"));

        globalPricingPolicy = new GlobalPricingPolicy();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddAndComputePrice() {

        Assert.assertEquals(new BigDecimal(0), globalPricingPolicy.computePrice(mockParkingSlotUsage));
        globalPricingPolicy.add(mockPricingPolicy1);
        Assert.assertEquals(new BigDecimal("3.48"), globalPricingPolicy.computePrice(mockParkingSlotUsage));
        globalPricingPolicy.add(mockPricingPolicy2);
        // 3.48 + 7.13
        Assert.assertEquals(new BigDecimal("10.61").setScale(2), globalPricingPolicy.computePrice(mockParkingSlotUsage));
    }
}