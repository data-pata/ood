package pos.dataobjects;

/**
 * An immutable data object of data related to a store. Encapsulates the name of
 * the store and the street address.
 *
 */
public final class StoreDTO {
    private final String storeName;
    private final String street;
    
    /**
     * Creates a StoreDTO instance. Sets the time of sale to now.
     *
     * @param storeName  the name of the store.
     * @param street     the street name of the store.
     */
    public StoreDTO(String storeName, String street) {
        this.storeName = storeName;
        this.street = street;
    }

    /** 
     * Get the name of the store.
     * @return String
     */
    public String getStoreName() {
        return this.storeName;
    }

    
    /** 
     * Get the name of the street of the store.
     * @return String
     */
    public String getStreet() {
        return this.street;
    }

    
    /** 
     * A string representation.
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s\n%s",getStoreName(),getStreet());
    }

}