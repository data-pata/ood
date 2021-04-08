package pos.integration.dataobjects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EANTest {
    private EAN ean13;
    
    @BeforeEach
    public void setUp() {
        ean13 = new EAN("1234567891234");
    }

    @AfterEach
    public void tearDown() {
         ean13 = null;
    }

    @Test
    public void testLegalConstructorArgs() {
        var ean13 = new EAN("1234567891234");
        var ean8 = new EAN("12345678");

        assertEquals(ean13.getCode(), new String("1234567891234"));
        assertEquals(ean8.getCode(), new String("12345678"));
    }

    @Test
    public void testillegalConstructorArgThrowsExc() {

        var expectedExcClass = IllegalArgumentException.class;
        Exception ex = assertThrows(expectedExcClass, () -> { 
            new EAN("");
        });
        Exception ex1 = assertThrows(expectedExcClass, () -> { 
            new EAN("illegalcharac");
        });
        Exception ex2 = assertThrows(expectedExcClass, () -> { 
            new EAN("12");
        });
        Exception ex3 = assertThrows(expectedExcClass, () -> { 
            new EAN("illegalCharsAndLen");
        });
        assertThrows(Exception.class, () -> { 
            new EAN(null);
        });

        String expectedMsg = "is not a valid EAN";
        assertTrue(ex.getMessage().contains(expectedMsg));
        assertTrue(ex1.getMessage().contains(expectedMsg));
        assertTrue(ex2.getMessage().contains(expectedMsg));
        assertTrue(ex3.getMessage().contains(expectedMsg));
    }
    
    @Test
    public void testEqualHash() {
        var otherEAN = new EAN(this.ean13.getCode());
        assertEquals(otherEAN.hashCode(), this.ean13.hashCode(),
            "same eancode gives different hashcode");
    }
    @Test
    public void testNotEqualHash() {
        var otherStr13 = "1313131313131"; 
        var otherEan13 = new EAN(otherStr13);
        
        assertNotEquals(otherEan13.hashCode(), this.ean13.hashCode(),
            "different eancode String gives same hashcode");

        assertNotEquals((Integer) ean13.hashCode(), null,
            "null should not give equals" 
            );
    }

    @Test
    public void testEqual() {
        var otherEAN = new EAN("1234567891234");
        assertTrue(this.ean13.equals(otherEAN),
            "Other EAN instance with the same code should be equal.");
    }
    @Test
    public void testNotEqual() {
        var otherStr13 = "1313131313131"; 
        var otherEan13 = new EAN(otherStr13);
        
        assertFalse(this.ean13.equals(otherEan13),
            "Other EAN instance with the same code is not equal.");
        
        assertNotEquals(this.ean13, null,
            "null should not give equals" 
            );
    }
}