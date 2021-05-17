package pos.dataobjects;

/**
 * Thrown when a client tries to create an EAN object with an invalid
 * EAN code string.
 */
public class InvalidEanException extends Exception {
    private final String eanCode;
    
    /**
     * Creates an instance representing the condition described in the specified
     * messaga, encapsulating the cause invalid EAN code.
     * 
     * @param msg      a message specifying the exception.
     * @param eanCode  the causing ean code
     */
    public InvalidEanException(String msg, String eanCode) {
        super(msg);
        this.eanCode = eanCode;
    }
    
    /** 
     * Get the invalid EAN code.
     * @return String
     */
    public String getEanCode() {
        return eanCode;
    }
}
