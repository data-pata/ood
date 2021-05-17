package pos.integration;

import pos.dataobjects.EAN;
import pos.dataobjects.Item;
import pos.dataobjects.SaleDTO;
import pos.dataobjects.SaleLog;
import pos.dataobjects.StoreDTO;
/**
 * This class is a controller and facade of the integration layer of the Store
 * POS system. It instantiates and initializes handlers for all external
 * systems: The inventorySystem, accountingSystem and printer(s). All
 * communication with these systems goes through an object of this class. 
 * 
 * @see StoreDTO
 * @see AccountingSystem
 * @see InventorySystem
 * @see PrinterHandler
 */
public class IntegrationHandler {
    private static final String ERROR_TRIGGING_EAN = "0000000000000";
    private final StoreDTO storeInfo;
    private final AccountingSystem accountingSystem;
    private final InventorySystem inventorySystem;
    private final PrinterHandler PrinterHandler;
    /**
     * Creates the objects representing the external systems and a data object
     * encapsulating the info of the store in which this POS system instance is
     * running. @see StoreDTO
     */
    public IntegrationHandler() {
        this.storeInfo = new StoreDTO("POOC", "PoocStreet");
        this.accountingSystem = new AccountingSystem();
        this.inventorySystem = new InventorySystem();
        this.PrinterHandler = new PrinterHandler();
    }     

    /** 
     * A method querying the inventory system for data on an Item.
     *
     * @param ean   an EAN object identifying the queried item.
     * @return Item a data object representing the queried item, @see Item. 
     * @throws NoSuchItemException              when the inventory has no item matching given EAN.
     * @throws InventorySystemFailureException  when the inventory system is failuring. 
     */
    public Item retrieveItemData(EAN ean) throws NoSuchItemException, InventorySystemFailureException {
        
        if(ean.getCode() == ERROR_TRIGGING_EAN) {
            throw new InventorySystemFailureException();
        }
        return inventorySystem.retrieveItemData(ean);
    }
    
    /** 
     * Updates all external systems with data from a completed sale and calls on
     * the printerHandler to print receipt(s) according to it's own
     * configuration, @see PrinterHandler.   
     *
     * @param saleDTO   all data pertaining the completed sale.    
     */
    public void logSale(SaleDTO saleDTO) {
        var saleLog = new SaleLog(saleDTO, this.storeInfo);
        this.accountingSystem.update(saleLog);
        this.inventorySystem.update(saleLog);
        this.PrinterHandler.printReceipt(saleLog);
    }

}