package model;

import integration.EAN;
import integration.ExtSysHandler;
import integration.Item;
import integration.NoSuchItemException;

public class Model {
    private CashRegister cashRegister;
    private ExtSysHandler extSysHandler;
    private Sale sale;

	public Model(ExtSysHandler extSysHandler, CashRegister cashRegister) {
        this.extSysHandler = extSysHandler;
        this.cashRegister = cashRegister;
    }

    public void startNewSale() {
        this.sale = new Sale();
    }

    public String enterItem(EAN ean) throws NoSuchItemException {

        if (sale.hasItem(ean)) {
            sale.incrementItemQuantity(ean);  
        }
        else {
            // retrieve data - this could result in exception if EAN is not in stores registry
            // try {
                Item item = extSysHandler.retrieveItemData(ean);
                sale.addNewItem(item);
                
            // } catch (NoSuchItemException e) {
            //     throw e;
            // }
        }
        return sale.toString();
    }  
    
    public int pay(int amount) {
        
        int total = ringUpTotal();
        int balance = cashRegister.getBalance();    
        int newBalance = balance - total;
        cashRegister.setBalance(newBalance);

        int cashback = amount - total;
        
        return cashback;
    }

    public int ringUpTotal() {
        return (int) Math.round(sale.getRunningTotal());
    }

    public void logSale(int paidAmount) {
        
    }


}