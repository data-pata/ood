package pos.view;

import pos.model.SaleObserver;

/**
     * This view will display the total revenue, 
     * updated after each finalized sale.
     */
public class TotalRevenueView implements SaleObserver {
        private int totalRevenue = 0;
    
        /**
         * Creates an instance of <code>TotalRevenueView</code>
         */
        public TotalRevenueView(){ 
        }
    
        /**
         * Updates the total revenue.
         * @param total the total that will be added to this <code>totalRevenue</code>
         */
        @Override
        public void updateTotalRevenue(int total) {
            this.totalRevenue += total;
            printCurrentRevenue();
        }
    
        private void printCurrentRevenue(){
            System.out.println("=-=-= TOTAL REVENUE DISPLAY =-=-=");
            System.out.printf("=-=-=        %d          =-=-=\n", this.totalRevenue);
            System.out.println("=-=-==-=-==-=-==-=-==-=-==-=-==-=");
        }
    
    }
