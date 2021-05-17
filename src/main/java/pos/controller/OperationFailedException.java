package pos.controller;
/**
 * Thrown when something goes wrong wile performing an operation in 
 * the <code>integration</code>.
 */
public final class OperationFailedException extends Exception {
    /**
     * Creates an instance representing the condition described in the specified
     * message and encapsulating the root cause exception.
     * 
     * @param msg       a message specifying the exception.     
     * @param causeExc  the root cause exception
     */
    public OperationFailedException(String msg, Exception causeExc) {
        super(msg, causeExc);
    }
}