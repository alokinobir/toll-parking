package com.nri.tollparking.business;

import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.CarEngineType;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.asset.ParkingSlotState;
import com.nri.tollparking.asset.ParkingSlotType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class GasolineAndElectricParkingAvailabilityManagerTest {

    @Mock
    private Parking mockParking;

    @Mock
    private Customer mockCustomer;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new GasolineAndElectricParkingAvailabilityManager(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testBookAndFreeSlots() {

        Data testData = new Data();
        Mockito.when(mockParking.getParkingSlots()).thenReturn(Arrays.asList(testData.gasolineSlot1, testData.electric20Slot2, testData.electric50Slot3));

        GasolineAndElectricParkingAvailabilityManager availabilityManager = new GasolineAndElectricParkingAvailabilityManager(mockParking);


        // Slot available
        Assert.assertEquals(ParkingSlotState.FREE, testData.gasolineSlot1.getSlotState());
        ParkingSlot slot = availabilityManager.bookSlot(testData.gasolineCar1);
        Assert.assertNotNull(slot);
        Assert.assertTrue(testData.gasolineSlot1 == slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        // Slot not available
        slot = availabilityManager.bookSlot(testData.gasolineCar2);
        Assert.assertNull(slot);

        // Slot available
        Assert.assertEquals(ParkingSlotState.FREE, testData.electric20Slot2.getSlotState());
        slot = availabilityManager.bookSlot(testData.electric20Car1);
        Assert.assertNotNull(slot);
        Assert.assertTrue(testData.electric20Slot2 == slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        // Slot not available
        slot = availabilityManager.bookSlot(testData.electric20Car2);
        Assert.assertNull(slot);

        // Slot available
        Assert.assertEquals(ParkingSlotState.FREE, testData.electric50Slot3.getSlotState());
        slot = availabilityManager.bookSlot(testData.electric50Car1);
        Assert.assertNotNull(slot);
        Assert.assertTrue(testData.electric50Slot3 == slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        // Slot not available
        slot = availabilityManager.bookSlot(testData.electric50Car2);
        Assert.assertNull(slot);

        // Free slots and try booking again
        Assert.assertEquals(ParkingSlotState.BUSY, testData.gasolineSlot1.getSlotState());
        availabilityManager.freeSlot(testData.gasolineSlot1);
        Assert.assertEquals(ParkingSlotState.FREE, testData.gasolineSlot1.getSlotState());
        slot = availabilityManager.bookSlot(testData.gasolineCar2);
        Assert.assertNotNull(slot);
        Assert.assertEquals(testData.gasolineSlot1, slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        Assert.assertEquals(ParkingSlotState.BUSY, testData.electric20Slot2.getSlotState());
        availabilityManager.freeSlot(testData.electric20Slot2);
        Assert.assertEquals(ParkingSlotState.FREE, testData.electric20Slot2.getSlotState());
        slot = availabilityManager.bookSlot(testData.electric20Car2);
        Assert.assertNotNull(slot);
        Assert.assertEquals(testData.electric20Slot2, slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

        Assert.assertEquals(ParkingSlotState.BUSY, testData.electric50Slot3.getSlotState());
        availabilityManager.freeSlot(testData.electric50Slot3);
        Assert.assertEquals(ParkingSlotState.FREE, testData.electric50Slot3.getSlotState());
        slot = availabilityManager.bookSlot(testData.electric50Car2);
        Assert.assertNotNull(slot);
        Assert.assertEquals(testData.electric50Slot3, slot);
        Assert.assertEquals(ParkingSlotState.BUSY, slot.getSlotState());

    }

    private class Data {

        public final Car gasolineCar1 = new Car("gasolineCar1", CarEngineType.GASOLINE, mockCustomer);
        public final Car gasolineCar2 = new Car("gasolineCar2", CarEngineType.GASOLINE, mockCustomer);
        public final Car electric20Car1 = new Car("electric20Car1", CarEngineType.ELECTRIC_20KW, mockCustomer);
        public final Car electric20Car2 = new Car("electric20Car2", CarEngineType.ELECTRIC_20KW, mockCustomer);
        public final Car electric50Car1 = new Car("electric50Car1", CarEngineType.ELECTRIC_50KW, mockCustomer);
        public final Car electric50Car2 = new Car("electric50Car2", CarEngineType.ELECTRIC_50KW, mockCustomer);

        public final ParkingSlot gasolineSlot1 = new ParkingSlot(1, ParkingSlotType.SEDAN_GASOLINE);
        public final ParkingSlot electric20Slot2 = new ParkingSlot(2, ParkingSlotType.POWER_SUPPLY_20KW);
        public final ParkingSlot electric50Slot3 = new ParkingSlot(3, ParkingSlotType.POWER_SUPPLY_50KW);
    }
}