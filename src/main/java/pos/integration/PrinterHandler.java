package pos.integration;

import java.time.format.DateTimeFormatter;

import pos.dataobjects.SaleLog;
/**
 * This is a mock class handling printers and printing format of connected
 * devices. It recieves all data on a completed sale to format different
 * receipts or other documents and prints them.
 */
public class PrinterHandler {
    /**
     * Creates a PrinterHandler instance.
     */
    public PrinterHandler() {}

    
    /** 
     * Formats and prints a receipt (currently to standard out) according to
     * predefined rules. 
     * @param saleLog   all data on a completed sale. 
     */
    void printReceipt(SaleLog saleLog) {
        var receipt = formatReceipt(saleLog);
        System.out.println("\n[Printing receipt...]");
        System.out.println(receipt);
    }

    private String formatReceipt(SaleLog saleLog) {
        var sb = new StringBuilder();
        sb.append("==========================\n");
        sb.append(saleLog.getStoreInfo().toString()+"\n");
        sb.append(formatTime(saleLog)+"\n");

        var saleInfo = saleLog.getSaleInfo();
        var lineItems = saleInfo.getLineItemsDTO();
        for (var lineItem : lineItems) {
            var item = lineItem.getItemData();
            var qty = lineItem.getItemQuantity();
            sb.append(String.format("%s\n      x %d  =  %.2f:-\n", item.getDescription(), qty, item.getPriceIncludingVat()));
        }
        sb.append(String.format("Total:  %.2f:- varav %.2f kr moms \n", saleInfo.getTotalSum(), saleInfo.getTotalVAT()));
        sb.append("==========================");
        return sb.toString();
    }

    /** 
     * @param saleLog
     * @return String
     */
    private String formatTime(SaleLog saleLog) {
        var timeOfSale = saleLog.getTimeOfSale();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return timeOfSale.format(formatter);
    }


}
