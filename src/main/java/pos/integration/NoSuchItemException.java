
package pos.integration;

import pos.integration.dataobjects.EAN;

public class NoSuchItemException extends Exception {
    private final EAN noSuchItemEAN;

    public NoSuchItemException(String msg, EAN noSuchItemEAN) {
        super(String.format(msg, noSuchItemEAN.getCode()));
        this.noSuchItemEAN = noSuchItemEAN;
    }
    
    public EAN getNoSuchItemEAN() {
        return noSuchItemEAN;
    }

}