package pos.dataobjects;

import org.junit.jupiter.api.*;

import pos.integration.VAT;

import static org.junit.jupiter.api.Assertions.*;

public class VATtest {
    
    @Test
    public void testLegalConstructorArgs() {
        var vat12 = new VAT(0.12);
        var vat6 = new VAT(0.06);
        var vat25 = new VAT(0.25);
        
        assertEquals(0.12, vat12.getVatRate(), "vat 12% gives incorrect rate");
        assertEquals(0.06, vat6.getVatRate(), "vat 6% gives incorrect rate");
        assertEquals(0.25, vat25.getVatRate(), "vat 25% gives incorrect rate");
    }

    @Test
    public void testillegalConstructorArgThrowsIllegalArgExc() {
        
        var expectedExcClass = IllegalArgumentException.class;

        Exception ex = assertThrows(expectedExcClass, () -> {
            new VAT(0.01);
        });
        Exception ex1 = assertThrows(expectedExcClass, () -> {
            new VAT(12.0);
        });

        String expectedMsg = "Invalid VAT rate";
        assertTrue(ex.getMessage().contains(expectedMsg));
        assertTrue(ex1.getMessage().contains(expectedMsg));

    
    }

}
