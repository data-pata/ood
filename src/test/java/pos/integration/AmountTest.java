package pos.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void testSetGetLegalAmount() {
        double legal = 0.000;
        var amt = new Amount(legal);
        assertEquals(amt.getAmount(), legal, "not same value as input");
        amt.setAmount(25);
        assertEquals(amt.getAmount(),25, "not same value as input");
    }

    @Test
    public void testSetIllegalValueThrowsExc() {
        var expMsg = "may not be negative or larger than 10e9";
        var expExc = IllegalArgumentException.class;
        var amt = new Amount(0);
        
        var thrownExc = assertThrows(expExc, () -> { 
            amt.setAmount(-1);
        });
        assertTrue(thrownExc.getMessage().contains(expMsg));
       
        thrownExc = assertThrows(expExc, () -> { 
            amt.setAmount(10e9+1);
        });
        assertTrue(thrownExc.getMessage().contains(expMsg));
    }

    @Test
    public void testGetRoundedAmount() {
        var amt = new Amount(1.499999);
        assertTrue(amt.getRoundedAmount() == 1);
        amt.setAmount(1.500000);
        assertTrue(amt.getRoundedAmount() == 2);
        amt.setAmount(0.0000001);
        assertTrue(amt.getRoundedAmount() == 0);
    }

    @Test
    public void doubles() {
        
    double a = +0f;
    double b = -0f;
    double c = -0d;
    assertTrue(b == 0);
    assertEquals(b, c);
    assertTrue(a == b, "a == b false");
    assertNotEquals(a, b);
    
    assertTrue(1/a == Double.POSITIVE_INFINITY);
    assertTrue(1/b == Double.NEGATIVE_INFINITY);

    assertTrue(1/a != 1/b);
    }

}