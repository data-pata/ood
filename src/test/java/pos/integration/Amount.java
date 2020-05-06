package pos.integration;

public class Amount {
    private double amount;
    
    public Amount(double amount) {
        setAmount(amount);
    }

    private void check(double amount) throws IllegalArgumentException {
        if( amount < 0.0 || amount > 10e9)
            throw new IllegalArgumentException("amount ( = %.2f) may not be negative or larger than 10e9");
    }
    public void setAmount(double amount) {
        check(amount);
        this.amount = amount;
    }
    public double getAmount() {
        return this.amount;
    }
    public int getRoundedAmount() {
        return (int) Math.round(getAmount());
    }
}