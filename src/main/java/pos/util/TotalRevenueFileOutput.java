package pos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import pos.model.SaleObserver;

/**
 * This class will log the total revenue to file, updated after each finalized
 * sale.
 */
public class TotalRevenueFileOutput implements SaleObserver {
    private int totalRevenue;
    private PrintWriter logWriter;
    private static final String LOG_FILE = "total-revenue-log.txt";

    /**
     * Creates an instance of <code>TotalRevenueFileOutput</code>
     * 
     */
    public TotalRevenueFileOutput() {
        this.totalRevenue = 0;
        try {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }    
    }

    /**
     * Updates the total revenue.
     * 
     * @param total the total that will be added to this <code>totalRevenue</code>
     */
    @Override
    public void updateTotalRevenue(int total) {
        this.totalRevenue += total;
        logTotalRevenue();
    }

    private void logTotalRevenue() {
        logWriter.printf("Total revenue:   %d\n", this.totalRevenue);
    }

}