package pos.integration;

import pos.integration.dataobjects.SaleLog;

public class Printer {
    
    public Printer() {
    }

    void printReceipt(SaleLog saleLog) {
        System.out.println("[Printing receipt]");
    }
}
