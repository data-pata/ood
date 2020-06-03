package pos.controller;

public final class OperationFailedException extends Exception {
    
    public OperationFailedException(String msg, Exception causeExc) {
        super(msg, causeExc);
    }

}
