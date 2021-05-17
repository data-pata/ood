package pos.controller;

import pos.dataobjects.EAN;
import pos.dataobjects.Item;
import pos.integration.IntegrationHandler;
import pos.integration.InventorySystemFailureException;
import pos.integration.NoSuchItemException;
import pos.model.CashRegister;
import pos.model.Sale;
import pos.model.SaleObserver;
import pos.model.InsufficientPaymentException;

/**
 * This class controls the program flow of the system. All calls between view,
 * model and integration passes through here. Any call modifying the state of a
 * current sale returns a DTO with all information on said sale.
 * <p>
 * An object of this class holds a cashRegister object, the integrationHandler
 * and a sale object if a sale is currently processed.   
 * <p>
 * There is a required order of execution wherein <code>startNewSale</code> must
 * be invoked before any calls to <code>enterItem</code> or <code>pay</code> may
 * execute successfully.
 */
public class Controller {
    private final CashRegister cashRegister;
    private IntegrationHandler integrationHandler;
    private Sale sale; 
    /**
     * Sole constructor.  
     * @param   integrationHandler  an interface to the integrationlayer
     */
	public Controller(IntegrationHandler integrationHandler) {
        this.integrationHandler = integrationHandler;
        this.cashRegister = new CashRegister(3000);
    }
    /**
     * Initializes a new sale.
     */
    public void startNewSale()  {
        this.sale = new Sale();
    }
    
    /**
     * Registers item(s) in the current sale. 
     * 
     * @param   ean     an EAN, uniquely identifying an item.
     * @param   qty     the quantity of the item entered.
     * @return  SaleDTO all information on the current sale, updated.     
     * @throws OperationFailedException if an error occurred during item data retrieval.  
     * @throws IllegalArgumentException if qty is outside range [1,9999].
     * @throws IllegalStateException    if a sale is not initialized.
     */
    public String enterItem(EAN ean, int qty) throws OperationFailedException {
        if(sale == null) {
            throw new IllegalStateException("Start new sale before entering item.");
        }
        if(qty < 1 || qty > 9999) {
            throw new IllegalArgumentException("qty must be in range (1,9999)");
        }
        try {
            sale.enterItem(ean, qty);
        } catch (NoSuchItemException e) {
            var itemData = getItemDataFromIntegration(ean);
            sale.enterNewItem(itemData, qty);
        }
        return sale.toString(); 
    }

    
    /** 
     * @param ean
     * @return Item
     * @throws OperationFailedException
     */
    private Item getItemDataFromIntegration(EAN ean) throws OperationFailedException {
        try {
            Item item = integrationHandler.retrieveItemData(ean);
            return item;
        } catch (NoSuchItemException exc) {
            throw new OperationFailedException("Item not in inventory", exc);
        } catch (InventorySystemFailureException exc) {
            throw new OperationFailedException("Inventory system is not responding", exc);
        }
    }

    /** 
     * Makes a payment for the current sale, forwards sale data to integration
     * and ends (nullifies) the current sale.
     *
     * @param   amount  the amount of the payment. 
     * @return  int     the change from payment. 
     * @throws InsufficientPaymentException if the payment amount is not enough
     * to cover the sale. 
     */
    public int pay(int amount) throws InsufficientPaymentException  { // should be extended to handle payment in increments
        int change = sale.pay(amount);
        addToCashRegister(amount - change);
        logSale();
        endSale();
        return change;
    }
    
    /** 
     * Adds a saleObserver to the current sale object. 
     * 
     * @param saleObs a SaleObserver instance.
     */
    public void addSaleObserver(SaleObserver saleObs) {
        sale.addSaleObserver(saleObs);
    }

    
    /** 
     * @param amount
     */
    private void addToCashRegister(int amount) {
        cashRegister.put(amount);
    }
    
    /** 
     * Gets the running total of the current sale.
     * 
     * @return double   the running total.
     */
    public double ringUpTotal() {
        return sale.getRunningTotal();
    }

    private void logSale() {
        integrationHandler.logSale(this.sale.toDTO());
    }

    private void endSale() {
        this.sale = null;
    }

    /** 
     * @return CashRegister
     */
    public CashRegister getCashRegister() {
        return this.cashRegister;
    }
    
    /** 
     * @return IntegrationHandler
     */
    public IntegrationHandler getIntegrationHandler() {
        return this.integrationHandler;
    }
    
    /** 
     * @return Sale
     */
    public Sale getSale() {
        return this.sale;
    }
}