package pos.model;

/**
 * This class represents a cash register in the POS system. It is instantiated
 * with a balance and throws IllegalArgumentException when a negative balance is
 * set.
 */
public class CashRegister {
    private int balance;
    /**
     * Creates an instance.
     * @param balance   The current amount of cash in the register. 
     */
    public CashRegister(int balance) throws IllegalArgumentException {
        this.setBalance(balance);
    }
    
    /** 
     * Add amount to the cash register. 
     * @param amount    value to be added to the register.
     */
    public void put(int amount) {
        this.balance += amount;
    }

    
    /** 
     * Get the balance of the cash register.
     * @return int
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Sets the balance.
     * 
     * @param balance new amount in the register.
     * @throws IllegalArgumentException when a negative balance is set.
     */
    public void setBalance(int balance) throws IllegalArgumentException {
        if(balance < 0)
            throw new IllegalArgumentException(String.format("%d is not a valid balance amount", balance));
        this.balance = balance;
    }

    /** 
     * A string represantation.
     * @return String
     */
    @Override
    public String toString() {
        return "CashRegister [balance=" + balance + "]";
    }
    

}