package model;

public class CashRegister {
    private int balance;

    public CashRegister(int balance) {
        // check that balance is >=0
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    void setBalance(int balance) throws IllegalArgumentException {
        if(balance <= 0)
            throw new IllegalArgumentException(String.format("%d is not a valid balance amount", balance));
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CashRegister [balance=" + balance + "]";
    }
    

}