package pos.integration;

import pos.dataobjects.SaleLog;
/**
 * This class is a mock representation of the accounting system. It's
 * only method takes a SaleLog as argument and prints to standard out that the
 * accounting system has been updated with the related data.
 *  
 * @see SaleLog
 */
public class AccountingSystem {
    /**
     * Sole constructor.
     */
    public AccountingSystem() {}

    /** 
     * Mock update method of the accounting system.
     * 
     * @param salelog
     */
    void update(SaleLog salelog) {
        System.out.println("[Accounting System update successful]");
    }

}