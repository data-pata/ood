package pos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class CashRegisterTest {
    private CashRegister cashReg;

    @BeforeEach
    public void setUp() {
        cashReg = new CashRegister(0);
    }

    @Test
    public void testPuttingBalance() {
        var initialBal = 0;
        var expectedBalance = 99999;

        assertEquals(initialBal, cashReg.getBalance(), "initial balance is wrong");
        
        cashReg.setBalance(expectedBalance);
        assertEquals(expectedBalance, cashReg.getBalance(), "wrong balance after put(amount)");
    }

    @Test
    public void throwsIllegalArgExceptOnInvalidBalance() {

        var expectedExcClass = IllegalArgumentException.class;

        assertThrows(expectedExcClass, () -> {
            cashReg.setBalance(-1);
        }, "no exception thrown on exception triggering set balance");

        assertThrows(expectedExcClass, () -> {
            new CashRegister(-1);
        }, "no exception thrown on exception triggering set balance");

        assertDoesNotThrow(() -> {
            cashReg.setBalance(9999999);
        }, "throws exceptiont when it shouldn't");

    }
}
