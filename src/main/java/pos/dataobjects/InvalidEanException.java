package pos.dataobjects;

public class InvalidEanException extends Exception {
    private final String eanCode;

    public InvalidEanException(String msg, String eanCode) {
        super(msg);
        this.eanCode = eanCode;
    }
    public String getEanCode() {
        return eanCode;
    }
}
