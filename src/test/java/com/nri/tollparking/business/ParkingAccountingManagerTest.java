package com.nri.tollparking.business;

import com.nri.tollparking.asset.Customer;
import com.nri.tollparking.business.pricing.Bill;
import com.nri.tollparking.business.pricing.PricingPolicy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class ParkingAccountingManagerTest {

    private ParkingAccountingManager accountingManager;

    @Before
    public void setUp() throws Exception {
        accountingManager = new ParkingAccountingManager();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBillCustomer() {

        try {
            accountingManager.billCustomer(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {

        }

        ParkingSlotUsage mockSlotUsage = Mockito.mock(ParkingSlotUsage.class);
        PricingPolicy mockPricingPolicy = Mockito.mock(PricingPolicy.class);

        Mockito.when(mockPricingPolicy.computePrice(mockSlotUsage)).thenReturn(new BigDecimal("87.93"));
        Mockito.when(mockSlotUsage.getPricingPolicy()).thenReturn(mockPricingPolicy);

        Customer customer = new Customer("customer1");

        Mockito.when(mockSlotUsage.getCustomer()).thenReturn(customer);


        Assert.assertTrue(customer.getBills().isEmpty());

        Bill bill = accountingManager.billCustomer(mockSlotUsage);

        Assert.assertEquals(new BigDecimal("87.93"), bill.getAmount());
        Assert.assertEquals(1, customer.getBills().size());
        Assert.assertEquals(bill, customer.getBills().get(0));
    }
}