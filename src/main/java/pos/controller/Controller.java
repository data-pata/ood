package pos.controller;

import pos.integration.IntegrationHandler;
import pos.integration.NoSuchItemException;
import pos.integration.dataobjects.EAN;
import pos.integration.dataobjects.Item;
import pos.model.CashRegister;
import pos.model.Sale;

public class Controller {
    private final CashRegister cashRegister;
    private IntegrationHandler integrationsHandler;
    private Sale sale;

	public Controller(IntegrationHandler integrationHandler) {
        this.integrationsHandler = integrationHandler;
        this.cashRegister = new CashRegister(3000);
    }

    public void startNewSale() {
        System.out.println("ctrl: starting new sale");
        this.sale = new Sale();
    }

    public String enterItem(EAN ean) throws NoSuchItemException {
        Item item = integrationsHandler.retrieveItemData(ean);
        sale.enterItem(item);
        return sale.toString();
    }  
    
    public int pay(int amount) throws IllegalArgumentException  {
        int total = ringUpTotal();
        if(total > amount)
            throw new IllegalArgumentException(String.format("Insufficient amount: %d", amount));
        updateCashRegisterBalance();
        logSale();
        endSale();
        return amount - total;
    }

    private void updateCashRegisterBalance() {
        cashRegister.put(ringUpTotal());
    }

    public int ringUpTotal() {
        return (int) Math.round(sale.getRunningTotal());
    }

    private void logSale() {
        integrationsHandler.logSale(this.sale.toDTO());
    }

    private void endSale() {
        this.sale = null;
    }
}