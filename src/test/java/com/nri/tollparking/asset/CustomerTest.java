package com.nri.tollparking.asset;

import com.nri.tollparking.business.ParkingSlotUsage;
import com.nri.tollparking.business.pricing.Bill;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {

    @Mock
    private ParkingSlotUsage mockParkingSlotUsage;

    private Customer customer;

    @Before
    public void setUp() throws Exception {

        customer = new Customer("poiopiop");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new Customer(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetName() {

        Assert.assertEquals("poiopiop", customer.getName());
    }

    @Test
    public void testAddBill() {
        Bill bill = new Bill(new BigDecimal("0"), mockParkingSlotUsage);

        Assert.assertTrue(customer.getBills().isEmpty());
        customer.addBill(bill);

        Assert.assertEquals(1, customer.getBills().size());
        Assert.assertEquals(bill, customer.getBills().get(0));
    }



    @Test
    public void testGetBills() {

        Assert.assertTrue(customer.getBills().isEmpty());
    }

    @Test
    public void equals() {

        Assert.assertEquals(new Customer("poiopiop"), customer);
    }
}