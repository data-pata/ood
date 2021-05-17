package pos.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;

import pos.integration.IntegrationHandler;
import pos.model.InsufficientPaymentException;
import pos.model.Sale;
import pos.controller.*;
import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;
import pos.dataobjects.SaleDTO;

public class ControllerTest {
    private Controller ctrl;
    private Controller ctrlWithItems;
    
    @BeforeEach
    public void setUp() {
        var integrationHandler = new IntegrationHandler();
        var integrationHandlerTwo = new IntegrationHandler();

        ctrl = new Controller(integrationHandler);
        ctrlWithItems = new Controller(integrationHandlerTwo);
        ctrlWithItems.startNewSale();
        try {
            ctrlWithItems.enterItem(new EAN("7300156486424"), 3); // 3 st leverpastej
            ctrlWithItems.enterItem(new EAN("77315009"), 2); // dax wax EAN-8
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    @AfterEach
    public void tearDown() {
        ctrl = null;
    }

    @Test
    public void testConstructor() {
        var intHandl = new IntegrationHandler();
        var ctrl = new Controller(intHandl);
        assertEquals(intHandl, ctrl.getIntegrationHandler(), "controller has correct integrationhandler");
        assertNull(ctrl.getSale(), "new controller should have no Sale object");
        assertNotNull(ctrl.getCashRegister(), "new controller should have CashRegister object");
    }
    
    @Test
    public void testStartsNewSale() {
        assertNull(ctrl.getSale(), "new controller should have no Sale object");
        ctrl.startNewSale();
        assertEquals(Sale.class, ctrl.getSale().getClass());
    }
    @Test
    public void testEnterItemWithoutSaleThrowsException() {
        try {
            ctrl.enterItem(new EAN("7310090348139"), 1);
            fail("exception not thrown");
        } catch (Exception e) {        }
    }
    @Test
    public void testEnterItemBadArgThrowsException() {
        try {
            ctrl.enterItem(new EAN("7310090348139"), 1);
            fail("exception not thrown on low qty");
        } catch (Exception e) {}
        try {
            ctrl.enterItem(new EAN("7310090348139"), -1);
            fail("exception not thrown on high qty");
        } catch (Exception e) {}
        try {
            ctrl.enterItem(new EAN("1234567891111"), 1);
            fail("OperationFailedException not thrown on item not in inventory");
        } catch (Exception e) { }
        try {
            ctrl.enterItem(new EAN("1234567891111"), 1);
            fail("OperationFailedException not thrown on item not in inventory");
        } catch (Exception e) {}
        try {
            ctrl.enterItem(new EAN("0000000000000"), 1);
            fail("OperationFailedException not thrown on data base failure");
        } catch (Exception e) {
        }
    }

    @Test
    public void testEnterItemReturnsSaleDTO() {
        try {
            var aSaleDTO = ctrl.getSale().toDTO();
            var saleDTO = ctrl.enterItem(new EAN("7310090348139"), 1);
            assertEquals(aSaleDTO, saleDTO);
        } catch (Exception e) {}
    }
    
    @Test
    public void testPayThrowsInsufficientFundsError() {
        try {
            var total = (int) Math.round(ctrlWithItems.getSale().getRunningTotal());
            ctrlWithItems.pay(total - 25);
            fail("Doesnt throw exception");
        } catch (InsufficientPaymentException e) {
        }
    }
    @Test
    public void testPayReturnsCorrectChange() {
        try {
            var total = (int) Math.round( ctrlWithItems.getSale().getRunningTotal() );
            var change = ctrlWithItems.pay(total+25);
            assertEquals(25, change, "paid returns wrong change");
        } catch (InsufficientPaymentException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPayEndsSale() {
        try {
            var total = (int) Math.round(ctrlWithItems.getSale().getRunningTotal());
            ctrlWithItems.pay(total);
            assertNull(ctrlWithItems.getSale(), "paid in full but sale isn't nullified");
        } catch (InsufficientPaymentException e) {
            e.printStackTrace();
        }
    }
}