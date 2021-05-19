
package pos.integration;
import pos.dataobjects.EAN;

/**
 * Thrown when inventory lacks item data for corresponding EAN identifier.  
 */
public class NoSuchItemException extends Exception {
    private final EAN noSuchItemEAN;
    
    /**
     * Creates an instance representing the condition described in the specified
     * message and encapsulating sought after EAN identifier.
     * 
     * @param msg      a message specifying the exception.
     * @param noSuchItemEAN the EAN of the sought after item.  
     */
    public NoSuchItemException(String msg, EAN noSuchItemEAN) {
        super(String.format("%s: EAN code: %s", msg, noSuchItemEAN.getCode()));
        this.noSuchItemEAN = noSuchItemEAN;
    }
    public NoSuchItemException(EAN noSuchItemEAN) {
        this("No item corresponding to the given EAN found", noSuchItemEAN);
    }

    /** 
     * Get the EAN object of missing item. 
     * @return EAN
     */
    public EAN getNoSuchItemEAN() {
        return noSuchItemEAN;
    }

}