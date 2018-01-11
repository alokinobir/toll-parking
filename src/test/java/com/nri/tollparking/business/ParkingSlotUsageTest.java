package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.business.pricing.PricingPolicy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSlotUsageTest {

    @Mock
    private ParkingSlot slot;

    @Mock
    private Parking parking;

    @Mock
    private Car car;

    @Mock
    private Customer customer;

    @Mock
    private PricingPolicy pricingPolicy;

    private ParkingSlotUsage slotUsage;

    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    @Before
    public void setUp() {
        startDateTime = ZonedDateTime.now();
        endDateTime = startDateTime.plusHours(4);
        slotUsage = new ParkingSlotUsage(slot, parking, car, customer, pricingPolicy, startDateTime, endDateTime);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor() {

        try {
            new ParkingSlotUsage(null, null, null, null, null, null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetParkingSlot() {

        Assert.assertEquals(slot, slotUsage.getParkingSlot());
    }

    @Test
    public void testGetParking() {

        Assert.assertEquals(parking, slotUsage.getParking());
    }

    @Test
    public void testGetCar() {

        Assert.assertEquals(car, slotUsage.getCar());
    }

    @Test
    public void getCustomer() {

        Assert.assertEquals(customer, slotUsage.getCustomer());
    }

    @Test
    public void getPricingPolicy() {

        Assert.assertEquals(pricingPolicy, slotUsage.getPricingPolicy());
    }

    @Test
    public void testGetStartDateTime() {

        Assert.assertEquals(startDateTime, slotUsage.getStartDateTime());
    }

    @Test
    public void testSetStartDateTime() {

        slotUsage.setStartDateTime(endDateTime);
        Assert.assertEquals(endDateTime, slotUsage.getStartDateTime());

        try {
            slotUsage.setStartDateTime(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetEndDateTime() {

        Assert.assertEquals(endDateTime, slotUsage.getEndDateTime());
    }

    @Test
    public void testSetEndDateTime() {

        slotUsage.setEndDateTime(startDateTime);
        Assert.assertEquals(startDateTime, slotUsage.getEndDateTime());

        try {
            slotUsage.setEndDateTime(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testEquals() {

        Assert.assertEquals(new ParkingSlotUsage(slot, parking, car, customer, pricingPolicy, startDateTime, endDateTime), slotUsage);
        Assert.assertEquals(new ParkingSlotUsage(slot, parking, car, customer, pricingPolicy, startDateTime, null), slotUsage);

    }
}