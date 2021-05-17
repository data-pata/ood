package pos.integration;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;

public class IntegrationHandlerTest {
    private IntegrationHandler integr;

    @BeforeEach
    public void setUp() {
        integr = new IntegrationHandler();
    }
    @AfterEach
    public void tearDown() {
        integr = null;
    }

    @Test
    public void testRetrieveItemDataReturnsCorrectItem()
            throws InvalidEanException, NoSuchItemException, InventorySystemFailureException {
        var ean = new EAN("7310090348139");
        var item = integr.retrieveItemData(ean);
        assertEquals(ean, item.getEan(), "retrieved item from inventory does not match");
    }

    @Test
    public void testThrowsNoSuchItemExceptionOnMissingItem() throws InvalidEanException, InventorySystemFailureException {
        var missingItemEAN = new EAN("1231231231231");
        try {
            integr.retrieveItemData(missingItemEAN);
            fail("NoSuchItemException weren't thrown for missing item");
        } catch (NoSuchItemException e) {
            var exceptEAN = e.getNoSuchItemEAN();
            assertEquals(missingItemEAN, exceptEAN, "wrong EAN in exception");
            assertEquals("No such item exists in the inventory", e.getMessage(), "exception message is not correct");
        }
    }

    @Test
    public void testThrowsInventorySystemFailureException() throws InvalidEanException {

        final String ERROR_TRIGGING_EAN = "0000000000000";
        final String ANOTHER_EAN = "7310090348139";

        var error_ean = new EAN(ERROR_TRIGGING_EAN);
        var ean = new EAN(ANOTHER_EAN);

        var expectedExcClass = InventorySystemFailureException.class;

        assertThrows(expectedExcClass, () -> {
            integr.retrieveItemData(error_ean);
        }, "no exception thrown on exception triggering ean");

        assertDoesNotThrow(() -> {
            integr.retrieveItemData(ean);
        }, "throws except when it shouldn't");

    }

}
