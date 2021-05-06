package pos.integration;

import pos.dataobjects.EAN;
import pos.dataobjects.Item;
import pos.dataobjects.SaleDTO;
import pos.dataobjects.SaleLog;
import pos.dataobjects.StoreDTO;

public class IntegrationHandler {
    private static final String ERROR_TRIGGING_EAN = "0000000000000";
    private final StoreDTO storeInfo;
    private final AccountingSystem accountingSystem;
    private final InventorySystem inventorySystem;
    private final PrinterHandler PrinterHandler;

    public IntegrationHandler() {
        this.storeInfo = new StoreDTO("POOC", "PoocStreet");
        this.accountingSystem = new AccountingSystem();
        this.inventorySystem = new InventorySystem();
        this.PrinterHandler = new PrinterHandler();
    }     

    public Item retrieveItemData(EAN ean) throws NoSuchItemException, InventorySystemFailureException {
        
        if(ean.getCode() == ERROR_TRIGGING_EAN) {
            throw new InventorySystemFailureException();
        }
        return inventorySystem.retrieveItemData(ean);
    }
    
    public void logSale(SaleDTO saleDTO) {
        var saleLog = new SaleLog(saleDTO, this.storeInfo);
        this.accountingSystem.update(saleLog);
        this.inventorySystem.update(saleLog);
        this.PrinterHandler.printReceipt(saleLog);
    }

}