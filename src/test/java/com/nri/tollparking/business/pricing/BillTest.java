package com.nri.tollparking.business.pricing;

import com.nri.tollparking.business.ParkingSlotUsage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class BillTest {

    @Mock
    private ParkingSlotUsage mockSlotUsage;

    private Bill bill;

    @Before
    public void setUp() throws Exception {
        bill = new Bill(new BigDecimal("543.87"), mockSlotUsage);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new Bill(null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetAmount() {

        Assert.assertEquals(new BigDecimal("543.87"), bill.getAmount());
    }

    @Test
    public void getSlotUsage() {

        Assert.assertEquals(mockSlotUsage, bill.getSlotUsage());
    }
}