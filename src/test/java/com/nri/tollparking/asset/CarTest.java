package com.nri.tollparking.asset;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CarTest {

    private Car car;

    @Before
    public void setup() {
        String licensePlate = "kjhwiey";
        CarEngineType carEngineType = CarEngineType.ELECTRIC_20KW;
        Customer customer = new Customer("oiuoioiouio");
        car = new Car(licensePlate, carEngineType, customer);
    }

    @Test
    public void testConstructor() {

        try {
            new Car(null, null, null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetLicensePlate() {
        Assert.assertEquals("kjhwiey", car.getLicensePlate());
    }

    @Test
    public void testGetEngineType() {
        Assert.assertEquals(CarEngineType.ELECTRIC_20KW, car.getEngineType());
    }

    @Test
    public void testGetOwner() {
        Assert.assertEquals(new Customer("oiuoioiouio"), car.getOwner());
    }

    @Test
    public void testEquals() {

        Assert.assertEquals(new Car("kjhwiey", CarEngineType.ELECTRIC_20KW, new Customer("dsfsdf")), car);
    }
}