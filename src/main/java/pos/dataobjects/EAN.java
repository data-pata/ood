package pos.dataobjects;

/**
 * Encapsulates a European Article Number (EAN) in an object with input validation in
 * the constructor. Immutable object. 
 */
public final class EAN {
    private final String code;
    
    /**
     * Creates an object representing an EAN code of either 9 or 13 digits.
     * Throws an InvalidEanException if argument EAN code is invalid.
     *
     * @param code the EAN code
     * @throws InvalidEanException if invalid EAN code is given.
     */
    public EAN(String code) throws InvalidEanException {
        validateEan(code); 
        this.code = code;       
    }

    /** 
     * An EAN code must be either 9 or 13 digits long. Throws an exception if
     * input code is invalid.
     *
     * @param code  the EAN code 
     * @throws InvalidEanException if invalid EAN code is given.
     */
    private void validateEan(String code) throws InvalidEanException {
        int len = code.length();
        if((len == 13 || len == 8) && code.matches("[0-9]+"))
            return;
        throw new InvalidEanException(String.format("%s is not a valid EAN", code), code);
    }

    /**
     * Get EAN code of this EAN object.
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /** 
     * String representation of this EAN. 
     * 
     * @return String
     */
    @Override
    public String toString() {
        return this.getCode();
    }
    /** 
     * Is this object equal to other obj?
     * 
     * @param obj   other object
     * @return boolean  true if objects are equal 
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof EAN))
            return false;
        EAN otherEAN = (EAN) obj;
        return otherEAN.getCode().equals(this.getCode());
    }
    /** 
     * This objects hashcode.
     * 
     * @return int  the hashcode
     */
    @Override
	public int hashCode() {
		return this.getCode().hashCode();
	}

}