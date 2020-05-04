package pos.integration.dataobjects;

// add constraints to code!
// e.g. only 11 or 13 or whatever digits and no letters
// final and no setters for immutability

public class EAN {
    private final String code;
    
    public EAN(String code) {
        this.code = code;       
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
        EAN otherEAN = (EAN) obj;
        return otherEAN.getCode().equals(this.getCode());
    }

    @Override
	public int hashCode() {
		return this.getCode().hashCode();
	}

}