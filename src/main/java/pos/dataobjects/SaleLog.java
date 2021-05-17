package pos.dataobjects;

import java.time.LocalDateTime;

/**
 * An immutable data object of all existing data related to a sale.
 * Encapsulates a SaleDTO, StoreDTO and the time of the sale as a LocalDateTime object.
 * The time of sale is recorded on the creation of a SaleLog instance.
 * @see SaleDTO
 * @see StoreDTO 
 */
public final class SaleLog {
    private final SaleDTO saleInfo;
    private final StoreDTO storeInfo;
    private final LocalDateTime timeOfSale;
    
    /**
     * Creates a Salelog instance. Sets the time of sale to now. 
     *
     * @param saleInfo  the SaleDTO of the sale. 
     * @param storeInfo The StoreDTO of the store. 
     */    
    public SaleLog(SaleDTO saleInfo, StoreDTO storeInfo) {
        this.saleInfo = saleInfo;
        this.storeInfo = storeInfo;
        this.timeOfSale = LocalDateTime.now();
    }

    
    
    /** 
     * Get the SaleInfo.
     * @return SaleDTO
     */
    public SaleDTO getSaleInfo() {
        return this.saleInfo;   
    }

    
    /** 
     * Get the StoreInfo.
     * @return StoreDTO
     */
    public StoreDTO getStoreInfo() {
        return this.storeInfo;
    }

    
    /** 
     * Get the timeOfSale.
     * @return LocalDateTime
     */
    public LocalDateTime getTimeOfSale() {
        return this.timeOfSale;
    }

    
    /** 
     * A string representation of this SaleLog.
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
            " saleInfo='" + getSaleInfo() + "'" +
            ", storeInfo='" + getStoreInfo() + "'" +
            ", timeOfSale='" + getTimeOfSale() + "'" +
            "}";
    }
    
}