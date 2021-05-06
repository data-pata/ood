package pos.integration.dataobjects;

import pos.integration.NoSuchItemException;

public final class EAN {
    private final String code;
    
    public EAN(String code) throws InvalidEanException {
        validateEan(code); 
        this.code = code;       
    }

    private void validateEan(String code) throws InvalidEanException {
        int len = code.length();
        if((len == 13 || len == 8) && code.matches("[0-9]+"))
            return;
        throw new InvalidEanException(String.format("%s is not a valid EAN", code), code);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof EAN))
            return false;
        EAN otherEAN = (EAN) obj;
        return otherEAN.getCode().equals(this.getCode());
    }

    @Override
	public int hashCode() {
		return this.getCode().hashCode();
	}

}