package pos.integration;

/**
 * Encapsulates a value-added tax (VAT) rate in an object with input
 * validation in the constructor. Immutable object.
 */
public class VAT {
    private final double vatRate;
    /**
     * Creates an instance representing a VAT rate code of either 6, 12 or 25 percent. Throws
     * an exception if argument rate is invalid.
     *
     * @param code the EAN code
     * @throws InvalidEanException if invalid EAN code is given.
     */
    public VAT(Double rate) {
        if(rate.equals(0.12) || rate.equals(0.06) || rate.equals(0.25)) {
            this.vatRate = rate;
        } else {
          throw new IllegalArgumentException(
              String.format("Invalid VAT rate: %.2f", rate));
        }
    }
    /**
     * get the VAT rate of this instance. 
     * @return the vatRate
     */
    public double getVatRate() {
        return vatRate;
    }
    
}