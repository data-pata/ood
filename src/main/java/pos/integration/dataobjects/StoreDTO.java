package pos.integration.dataobjects;

public final class StoreDTO {
    private final String storeName;
    private final String street;

    public StoreDTO(String storeName, String street) {
        this.storeName = storeName;
        this.street = street;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public String getStreet() {
        return this.street;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s",getStoreName(),getStreet());
    }

}