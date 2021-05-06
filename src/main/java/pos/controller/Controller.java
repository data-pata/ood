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

public class Controller {
    private final CashRegister cashRegister;
    private IntegrationHandler integrationHandler;
    private Sale sale; 

	public Controller(IntegrationHandler integrationHandler) {
        this.integrationHandler = integrationHandler;
        this.cashRegister = new CashRegister(3000);
    }

    public void startNewSale()  {
        this.sale = new Sale();
    }

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

    public int pay(int amount) throws InsufficientPaymentException  {
        int change = sale.pay(amount);
        addToCashRegister(amount - change);
        logSale();
        endSale();
        return change;
    }
    
    public void addSaleObserver(SaleObserver saleObs) {
        sale.addSaleObserver(saleObs);
    }

    private void addToCashRegister(int amount) {
        cashRegister.put(amount);
    }

    public double ringUpTotal() {
        return sale.getRunningTotal();
    }

    private void logSale() {
        integrationHandler.logSale(this.sale.toDTO());
    }

    private void endSale() {
        this.sale = null;
    }

    public CashRegister getCashRegister() {
        return this.cashRegister;
    }
    public IntegrationHandler getIntegrationHandler() {
        return this.integrationHandler;
    }
    public Sale getSale() {
        return this.sale;
    }
}