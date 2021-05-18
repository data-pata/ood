package pos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import pos.dataobjects.Item;
import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;
import pos.integration.InventorySystemFailureException;
import pos.integration.NoSuchItemException;

public class SaleTest {
    private Sale sale;

    @BeforeEach
    public void setUp() throws InvalidEanException {
        this.sale = new Sale();
        var ean = new EAN("1234567891234");
        var item = new Item(ean, 10.0, "item 11.2 kr including vat", 0.12);
        sale.enterNewItem(item, 3);
    }

    @Test 
    public void testThrowsNoSuchItemExceptionOnMissingItem() 
        throws InvalidEanException, InventorySystemFailureException {
 
        var missingItemEAN = new EAN("1231231231231");

        Exception exception = assertThrows(NoSuchItemException.class, () -> {
            sale.enterItem(missingItemEAN, 1);
        }, "no exception thrown on exception triggering ean");

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPayThrowsInsufficientPaymentException() throws InvalidEanException {    
        
        Exception exception = assertThrows(InsufficientPaymentException.class, () -> {
            sale.pay(15);
        }, "no exception thrown on exception triggering payment");
        
        String expectedMessage = "Amount payed is less than total price";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }   

    @Test
    public void testPayReturnsCorrChange() throws InvalidEanException, InsufficientPaymentException {
        var cost = 10 * 1.12 * 3; 
        var payment = 40;
        var expChange = (int) Math.round(payment - cost);
        var actualChange = sale.pay(payment);
        assertEquals(expChange, actualChange, "pay returns wrong change");
        
        var wrongExpChange = 33;
        assertNotEquals(wrongExpChange, actualChange);
    }

    @Test void testCorrectRunningTotal() {
        var expTotal = 10 * 1.12 * 3;
        assertEquals(expTotal, sale.getRunningTotal());
    }

}