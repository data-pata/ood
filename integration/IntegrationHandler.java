package integration;

import integration.dataobjects.EAN;
import integration.dataobjects.Item;
import integration.dataobjects.SaleDTO;
import integration.dataobjects.SaleLog;
import integration.dataobjects.StoreDTO;

public class IntegrationHandler {
    private final StoreDTO storeInfo;
    private final AccountingSystem accountingSystem;
    private final InventorySystem inventorySystem;
    private final Printer receiptPrinter;

    public IntegrationHandler() {
        this.storeInfo = new StoreDTO("POOC", "PoocStreet");
        this.accountingSystem = new AccountingSystem();
        this.inventorySystem = new InventorySystem();
        this.receiptPrinter = new Printer();
    }    

    public Item retrieveItemData(EAN ean) throws NoSuchItemException {
        return inventorySystem.retrieveItemData(ean);
    }
    
    public void logSale(SaleDTO saleDTO) {
        var saleLog = new SaleLog(saleDTO, this.storeInfo);
        this.accountingSystem.update(saleLog);
        this.inventorySystem.update(saleLog);
        this.receiptPrinter.printReceipt(saleLog);
    }

}