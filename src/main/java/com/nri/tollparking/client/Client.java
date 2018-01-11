package com.nri.tollparking.client;

import com.nri.tollparking.ParkingActivityManager;
import com.nri.tollparking.asset.Car;
import com.nri.tollparking.asset.CarEngineType;
import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.asset.Parking;
import com.nri.tollparking.asset.ParkingSlotType;
import com.nri.tollparking.builder.ParkingActivityManagerBuilder;
import com.nri.tollparking.builder.ParkingBuilder;
import com.nri.tollparking.builder.RandomCarFleetBuilder;
import com.nri.tollparking.builder.RandomCustomerPoolBuilder;
import com.nri.tollparking.business.GasolineAndElectricParkingAvailabilityManager;
import com.nri.tollparking.business.ParkingAccountingManager;
import com.nri.tollparking.business.ParkingSlotUsage;
import com.nri.tollparking.business.pricing.Bill;
import com.nri.tollparking.business.pricing.FixedAmountAndPerHourPricingPolicy;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A simple client to test the Parking API
 *
 * @author Nicolas Ribaud
 * @version 1.0
 */
public class Client {


    public static void main(String[] args) {

        // Parking
        ParkingBuilder parkingBuilder = new ParkingBuilder();

        Parking parking = parkingBuilder
                .addParkingSlots(2, ParkingSlotType.SEDAN_GASOLINE)
                .addParkingSlots(2, ParkingSlotType.POWER_SUPPLY_20KW)
                .addParkingSlots(2, ParkingSlotType.POWER_SUPPLY_50KW)
                .createParking("Polygone Riviera");

        // Parking activity
        ParkingActivityManagerBuilder parkingActivityManagerBuilder = new ParkingActivityManagerBuilder();

        // API entry point
        ParkingActivityManager parkingActivityManager = parkingActivityManagerBuilder
                .setParking(parking)
//                .setPricingPolicy(new PerHourPricingPolicy("5.2"))
                .setPricingPolicy(new FixedAmountAndPerHourPricingPolicy("5.2", "3.56"))
                .setParkingAvailabilityManager(new GasolineAndElectricParkingAvailabilityManager(parking))
                .setParkingAccountingManager(new ParkingAccountingManager())
                .createParkingActivity();

        // Builds a random customer pool
        final int CUSTOMERS_COUNT = 10;
        RandomCustomerPoolBuilder customerBuilder = new RandomCustomerPoolBuilder();
        List<Customer> customerPool = customerBuilder
                .addCustomers(CUSTOMERS_COUNT)
                .createCustomerPool();

        // Builds a random cars fleet
        RandomCarFleetBuilder randomCarFleetBuilder = new RandomCarFleetBuilder(customerPool);
        List<Car> carFleet = randomCarFleetBuilder
                .addCars(5, CarEngineType.GASOLINE)
                .addCars(5, CarEngineType.ELECTRIC_20KW)
                .addCars(5, CarEngineType.ELECTRIC_50KW)
                .createCarFleet();

        LinkedList<Car> carsIn = new LinkedList<Car>();

        Random random = new Random();
        final int MAX_IN_COUNT = 10;
        final int MAX_OUT_COUNT = 5;
        final int MAX_HOURS = 100;
        LinkedList<Car> ll = new LinkedList<>(carFleet);
        while (!ll.isEmpty()) {

            int inCount = nextCarsCount(ll, MAX_IN_COUNT, random);
            int outCount = nextCarsCount(carsIn, MAX_IN_COUNT, random);

            // Make some car come in
            for (int i = 0; i < inCount; ++i) {

                Car car = ll.removeFirst();
                ParkingSlotUsage slotUsage = parkingActivityManager.comeIn(car, ZonedDateTime.now());
                if (slotUsage != null) {
                    carsIn.add(car);
                    System.out.println(String.format("Car %s (%s, %s) in slot %d (%s) at %s",
                            car.getLicensePlate(),
                            car.getOwner().getName(),
                            car.getEngineType().name(),
                            slotUsage.getParkingSlot().getSlotId(),
                            slotUsage.getParkingSlot().getSlotType().name(),
                            slotUsage.getStartDateTime()));
                } else {
                    System.out.println(String.format("Car %s (%s) refused", car.getLicensePlate(), car.getEngineType().name()));
                }
            }

            // Make some cars come out
            Collections.shuffle(carsIn);

            for (int i = 0; i < outCount; ++i) {
                ParkingSlotUsage slotUsage = parkingActivityManager.comeOut(carsIn.removeFirst(), ZonedDateTime.now().plusHours(random.nextInt(MAX_HOURS)));
                Car car = slotUsage.getCar();
                System.out.println(String.format("Car %s (%s) has leaved slot %d (%s) at %s. Customer %s billed for duration=%d min", car.getLicensePlate(),
                        car.getEngineType().name(),
                        slotUsage.getParkingSlot().getSlotId(),
                        slotUsage.getParkingSlot().getSlotType().name(),
                        slotUsage.getEndDateTime(),
                        slotUsage.getCustomer().getName(),
                        ChronoUnit.MINUTES.between(slotUsage.getStartDateTime(), slotUsage.getEndDateTime())));
            }


        }


        // Print billing report
        System.out.println("---------------------- BILLING REPORT ------------------------");

        System.out.println(String.format("Applicable pricing policy: %s\n\n", parkingActivityManager.getPricingPolicy()));

        for (Customer customer : customerPool) {
            System.out.println("- CUSTOMER " + customer.getName());
            for (Bill bill : customer.getBills()) {
                long durationMin = ChronoUnit.MINUTES.between(bill.getSlotUsage().getStartDateTime(), bill.getSlotUsage().getEndDateTime());
                float durationHour = durationMin / 60;
                System.out.println(String.format("-- BILL AMOUNT = %s for duration %d min (%f hours)", bill.getAmount(), durationMin, durationHour));
            }
        }


    }

    private static int nextCarsCount(List<Car> queuedCars, int maxCarsCount, Random random) {
        if (queuedCars.isEmpty()) {
            return 0;
        }

        int maxCount = queuedCars.size() >= maxCarsCount ? maxCarsCount : queuedCars.size();

        int count = random.nextInt(maxCount);
        if (count == 0) {
            count++;
        }

        return count;
    }
}
