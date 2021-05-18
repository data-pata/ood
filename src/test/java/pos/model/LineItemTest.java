package pos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;
import pos.dataobjects.Item;


public class LineItemTest {
    LineItem lineItem;
    Item item;

    @BeforeEach
    public void setUp() throws InvalidEanException {
        item = new Item(new EAN("12312312"), 10, "item with 11.2 kr total price inc. vat", 0.12);
        lineItem = new LineItem(item);
    }

    @Test
    public void testQuantity() {
        var expQty = 1;
        assertEquals(expQty, lineItem.getQuantity(), "wrong initial quantity");
        lineItem.addQuantity(3);
        expQty = 4;
        assertEquals(expQty, lineItem.getQuantity(), "qty wrongly incremented");
    }
    @Test
    public void throwsIllegalArgExceptOnNegativeQty() {

        var expectedExcClass = IllegalArgumentException.class;

        assertThrows(expectedExcClass, () -> {
            lineItem.setQuantity(-1);
        }, "no exception thrown on set negative quantity");

        Exception ex = assertThrows(expectedExcClass, () -> {
            new LineItem(item, -1);
        }, "no exception thrown on set negative quantity");

        var expectedMsg = String.format("Quantity %d, less than zero", -1);
        var containsMsg = ex.getMessage().contains(expectedMsg);
        assertTrue(containsMsg, "wrong error specification"); 
    }


}