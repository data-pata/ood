package pos.model;

import java.lang.Exception;
/**
 * Thrown when a payment is made with an insufficient amount.
 */
public class InsufficientPaymentException extends Exception {
    private final int amountToPay;
    private final int amountPaid;

    /**
     * Creates an instance representing the condition specified by the
     * parameters.
     *
     * @param amountPaid    a payment amount
     * @param amountToPay   an amount required to finalize sale
     */
    public InsufficientPaymentException(int amountPaid, int amountToPay) {
        super("Amount payed is less than total price");
        this.amountToPay = amountToPay;
        this.amountPaid = amountPaid;
    }

    /** 
     * @return int
     */
    public int getAmountToPay() {
        return amountToPay;
    }
    
    /** 
     * @return int
     */
    public int getamountPaid() {
        return amountPaid;
    }
}