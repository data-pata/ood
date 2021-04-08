
package pos.integration;

import pos.integration.dataobjects.EAN;

public class NoSuchItemException extends Exception {
    private final EAN noSuchItemEAN;

    public NoSuchItemException(EAN noSuchItemEAN) {
        super(String.format("No Item for EAN %s exists in the inventory", noSuchItemEAN.getCode()));
        this.noSuchItemEAN = noSuchItemEAN;
    }

    public EAN getNoSuchItemEAN() {
        return noSuchItemEAN;
    }

}