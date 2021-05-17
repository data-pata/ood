package pos.dataobjects;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    
    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    } 

    @Test
    public void testCorrectPriceIncludingVAT() throws InvalidEanException {
        var item = new Item(new EAN("12312312"), 10, "item with 11.2 kr total price inc. vat", 0.12);
        double expPrice = 11.2;
        double notExpPrice = 10;
        
        assertEquals(expPrice, item.getPriceIncludingVat(), "wrong calculation of price including vat");
        assertNotEquals(notExpPrice, item.getPriceIncludingVat(), "wrong calculation of price including vat");
    }

}