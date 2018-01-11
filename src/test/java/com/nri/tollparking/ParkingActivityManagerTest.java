package com.nri.tollparking;

import com.nri.tollparking.ParkingActivityManager;
import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.CarEngineType;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlot;
import com.nri.tollparking.asset.ParkingSlotType;
import com.nri.tollparking.business.ParkingAccountingManager;
import com.nri.tollparking.business.ParkingAvailabilityManager;
import com.nri.tollparking.business.ParkingSlotUsage;
import com.nri.tollparking.business.pricing.PricingPolicy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ParkingActivityManagerTest {

    @Mock
    private Parking mockParking;

    @Mock
    private PricingPolicy mockPricingPolicy;

    @Mock
    private Customer mockCustomer;

    @Mock
    private ParkingAvailabilityManager mockParkingAvailabilityManager;

    @Mock
    private ParkingAccountingManager mockParkingAccountingManager;

    @Mock
    private ParkingSlotUsage mockParkingSlotUsage;


    private ParkingActivityManager parkingActivityManager;

    @Before
    public void setUp() throws Exception {

        parkingActivityManager = new ParkingActivityManager(mockParking, mockPricingPolicy, mockParkingAvailabilityManager, mockParkingAccountingManager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructor() {

        try {
            new ParkingActivityManager(null, null, null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetParking() {

        Assert.assertEquals(mockParking, parkingActivityManager.getParking());
    }

    @Test
    public void testGetPricingPolicy() {

        Assert.assertEquals(mockPricingPolicy, parkingActivityManager.getPricingPolicy());
    }

    @Test
    public void testSetPricingPolicy() {

        try {
            parkingActivityManager.setPricingPolicy(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }

        PricingPolicy newPricingPolicy = Mockito.mock(PricingPolicy.class);
        Assert.assertNotEquals(newPricingPolicy, parkingActivityManager.getPricingPolicy());
        parkingActivityManager.setPricingPolicy(newPricingPolicy);
        Assert.assertEquals(newPricingPolicy, parkingActivityManager.getPricingPolicy());
    }

    @Test
    public void testGetParkingAccountingManager() {

        Assert.assertEquals(mockParkingAccountingManager, parkingActivityManager.getParkingAccountingManager());
    }

    @Test
    public void testSetParkingAccountingManager() {

        try {
            parkingActivityManager.setParkingAccountingManager(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }

        ParkingAccountingManager newParkingAccountingManager = Mockito.mock(ParkingAccountingManager.class);
        Assert.assertNotEquals(newParkingAccountingManager, parkingActivityManager.getParkingAccountingManager());
        parkingActivityManager.setParkingAccountingManager(newParkingAccountingManager);
        Assert.assertEquals(newParkingAccountingManager, parkingActivityManager.getParkingAccountingManager());
    }


    @Test
    public void testGetParkingAvailabilityManager() {

        Assert.assertEquals(mockParkingAvailabilityManager, parkingActivityManager.getParkingAvailabilityManager());
    }

    @Test
    public void testSetParkingAvailabilityManager() {

        try {
            parkingActivityManager.setParkingAvailabilityManager(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }

        ParkingAvailabilityManager newParkingAvailabilityManager = Mockito.mock(ParkingAvailabilityManager.class);
        Assert.assertNotEquals(newParkingAvailabilityManager, parkingActivityManager.getParkingAvailabilityManager());
        parkingActivityManager.setParkingAvailabilityManager(newParkingAvailabilityManager);
        Assert.assertEquals(newParkingAvailabilityManager, parkingActivityManager.getParkingAvailabilityManager());
    }

    @Test
    public void testGetParkingSlotUsages() {

        Assert.assertTrue(parkingActivityManager.getParkingSlotUsages().isEmpty());

        Data data = new Data();

        // Register gasolineCar1 as occupying a slot in the parking
        try {
            Field field = parkingActivityManager.getClass().getDeclaredField("parkingSlotUsages");
            field.setAccessible(true);

            Map<Car, ParkingSlotUsage> slotsUsagesRegistry = (Map<Car, ParkingSlotUsage>) field.get(parkingActivityManager);
            slotsUsagesRegistry.put(data.electric20Car1, mockParkingSlotUsage);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, parkingActivityManager.getParkingSlotUsages().size());
    }

    @Test
    public void testComeIn_OK() {

        Data data = new Data();
        Mockito.when(mockParkingAvailabilityManager.bookSlot(Matchers.any())).thenReturn(data.gasolineSlot1);

        ZonedDateTime startDateTime = ZonedDateTime.now();
        ParkingSlotUsage slotUsage = parkingActivityManager.comeIn(data.gasolineCar1, startDateTime);

        Assert.assertNotNull(slotUsage);
        Assert.assertEquals(mockParking, slotUsage.getParking());
        Assert.assertEquals(data.gasolineSlot1, slotUsage.getParkingSlot());
        Assert.assertTrue(data.gasolineSlot1 == slotUsage.getParkingSlot());
        Assert.assertEquals(data.gasolineCar1, slotUsage.getCar());
        Assert.assertEquals(mockPricingPolicy, slotUsage.getPricingPolicy());
        Assert.assertEquals(startDateTime, slotUsage.getStartDateTime());
        Assert.assertNull(slotUsage.getEndDateTime());

    }

    @Test
    public void testComeIn_Refused() {

        Data data = new Data();
        Mockito.when(mockParkingAvailabilityManager.bookSlot(Matchers.any())).thenReturn(null);

        ZonedDateTime startDateTime = ZonedDateTime.now();
        ParkingSlotUsage slotUsage = parkingActivityManager.comeIn(data.gasolineCar1, startDateTime);

        Assert.assertNull(slotUsage);
    }

    @Test
    public void testComeIn_InvalidInput() {

        try {
            parkingActivityManager.comeIn(null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }


    }

    @Test
    public void testComeIn_DuplicateCar() {

        Data data = new Data();

        // Register gasolineCar1 as occupying a slot in the parking
        try {
            Field field = parkingActivityManager.getClass().getDeclaredField("parkingSlotUsages");
            field.setAccessible(true);

            Map<Car, ParkingSlotUsage> slotsUsagesRegistry = (Map<Car, ParkingSlotUsage>) field.get(parkingActivityManager);
            slotsUsagesRegistry.put(data.electric20Car1, mockParkingSlotUsage);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            parkingActivityManager.comeIn(data.electric20Car1, ZonedDateTime.now());
            Assert.fail("Should have raised IllegalStateException");
        } catch (IllegalStateException e) {

        }
    }

    @Test
    public void testComeOut() {

        Data data = new Data();

        // Register gasolineCar1 as occupying a slot in the parking
        try {
            Field field = parkingActivityManager.getClass().getDeclaredField("parkingSlotUsages");
            field.setAccessible(true);

            Map<Car, ParkingSlotUsage> slotsUsagesRegistry = (Map<Car, ParkingSlotUsage>) field.get(parkingActivityManager);
            slotsUsagesRegistry.put(data.electric20Car1, mockParkingSlotUsage);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Mockito.doNothing().when(mockParkingAvailabilityManager).freeSlot(Matchers.any());
        Mockito.doReturn(null).when(mockParkingAccountingManager).billCustomer(Matchers.any());


        ZonedDateTime endDateTime = ZonedDateTime.now();
        ParkingSlotUsage slotUsage = parkingActivityManager.comeOut(data.electric20Car1, endDateTime);

        Assert.assertEquals(mockParkingSlotUsage, slotUsage);

    }

    @Test
    public void testComeOut_InvalidInput() {

        try {
            parkingActivityManager.comeOut(null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
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