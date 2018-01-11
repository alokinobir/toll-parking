package com.nri.tollparking.asset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSlotTest {

    private ParkingSlot slot;

    @Before
    public void setUp() throws Exception {

        slot = new ParkingSlot(4267, ParkingSlotType.POWER_SUPPLY_50KW);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new ParkingSlot(0, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void getSlotId() {

        Assert.assertEquals(4267, slot.getSlotId());
    }

    @Test
    public void getSlotType() {

        Assert.assertEquals(ParkingSlotType.POWER_SUPPLY_50KW, slot.getSlotType());
    }

    @Test
    public void getSlotState() {

        Assert.assertEquals(ParkingSlotState.FREE, slot.getSlotState());
    }

    @Test
    public void setSlotState() {

        Assert.assertEquals(ParkingSlotState.FREE, slot.getSlotState());
        slot.setSlotState(ParkingSlotState.BUSY);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        try {
            slot.setSlotState(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void equals() {

        Assert.assertEquals(new ParkingSlot(4267, ParkingSlotType.POWER_SUPPLY_20KW), slot);
    }
}