package pos.integration;

public class VAT {
    private final double vatRate;

    public VAT(Double rate) {
        
        if(rate.equals(0.12) || rate.equals(0.06) || rate.equals(0.25)) {
            this.vatRate = rate;
        } else {
          throw new IllegalArgumentException(
              String.format("Invalid VAT rate: %.2f", rate));
        }
    }
    /**
     * @return the vatRate
     */
    public double getVatRate() {
        return vatRate;
    }
    
}