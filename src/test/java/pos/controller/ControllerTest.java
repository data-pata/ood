package pos.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import pos.integration.IntegrationHandler;
import pos.model.Sale;
import pos.controller.*;
import pos.dataobjects.EAN;

public class ControllerTest {
    private Controller ctrl;
    
    @BeforeEach
    public void setUp() {
        var integrationHandler = new IntegrationHandler();
        ctrl = new Controller(integrationHandler);
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
        } catch (Exception e) {
        }
    }
}