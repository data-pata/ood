package integration;

import integration.dataobjects.SaleLog;

public class Printer {
    public Printer() {

    }

    void printReceipt(SaleLog saleLog) {
        System.out.println("[Printing receipt]");
    }
}
