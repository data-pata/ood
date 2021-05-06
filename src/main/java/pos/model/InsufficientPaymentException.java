package pos.model;

import java.lang.Exception;

public class InsufficientPaymentException extends Exception {
    private final int amountToPay;
    private final int amountPaid;

    public InsufficientPaymentException(int amountPaid, int amountToPay) {
        super("Amount payed is less than total price");
        this.amountToPay = amountToPay;
        this.amountPaid = amountPaid;
    }

    public int getAmountToPay() {
        return amountToPay;
    }
    public int getamountPaid() {
        return amountPaid;
    }
}