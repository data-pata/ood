package pos.controller;

import pos.integration.IntegrationHandler;
import pos.integration.InventorySystemFailureException;
import pos.integration.NoSuchItemException;
import pos.integration.dataobjects.EAN;
import pos.integration.dataobjects.Item;

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

    public String enterItem(EAN ean, int qty) throws NoSuchItemException, OperationFailedException {
        if(sale == null) {
            throw new IllegalStateException("Call to enterItem before starting new sale.");
        }
        if(sale.hasItem(ean)) {
            sale.increaseQuantityBy(ean, qty);
        }
        else {
            try {
                Item item = integrationHandler.retrieveItemData(ean);
                sale.enterItem(item, qty);

            } catch(NoSuchItemException exc) { 
              throw new OperationFailedException("Item not in inventory", exc);
            }
            catch (InventorySystemFailureException exc) {
                throw new OperationFailedException("Inventory system is not responding", exc);
            }
        }
        return sale.toString(); 
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
}