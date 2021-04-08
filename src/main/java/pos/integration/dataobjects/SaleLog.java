package pos.integration.dataobjects;

import java.time.LocalDateTime;

public final class SaleLog {
    private final SaleDTO saleInfo;
    private final StoreDTO storeInfo;
    private final LocalDateTime timeOfSale;

    public SaleLog(SaleDTO saleInfo, StoreDTO storeInfo) {
        this.saleInfo = saleInfo;
        this.storeInfo = storeInfo;
        this.timeOfSale = LocalDateTime.now();
    }

    public SaleDTO getSaleInfo() {
        return this.saleInfo;   
    }

    public StoreDTO getStoreInfo() {
        return this.storeInfo;
    }

    public LocalDateTime getTimeOfSale() {
        return this.timeOfSale;
    }

    @Override
    public String toString() {
        return "{" +
            " saleInfo='" + getSaleInfo() + "'" +
            ", storeInfo='" + getStoreInfo() + "'" +
            ", timeOfSale='" + getTimeOfSale() + "'" +
            "}";
    }
    
}