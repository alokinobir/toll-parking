package com.nri.tollparking.asset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class ParkingTest {

    @Mock
    private ParkingSlot slot1;

    @Mock
    private ParkingSlot slot2;

    private Parking parking;



    @Before
    public void setUp() throws Exception {

        parking = new Parking("lkjlkkljlk", Arrays.asList(slot1, slot2));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new Parking(null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("lkjlkkljlk", parking.getName());
    }

    @Test
    public void testGetParkingSlots() {

        Assert.assertArrayEquals(Arrays.asList(slot1, slot2).toArray(), parking.getParkingSlots().toArray());
    }

    @Test
    public void testEquals() {

        Assert.assertEquals(new Parking("lkjlkkljlk", Collections.emptyList()), parking);
    }
}