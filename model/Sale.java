package model;

import integration.EAN;
import integration.Item;
import integration.LineItemDTO;
import integration.SaleDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Sale {
    private Map<EAN, LineItem> lineItems;
    private double runningTotal;
    private int cashPaymentReceived;

    public Sale() {
        this.lineItems = new HashMap<EAN, LineItem>();
        this.runningTotal = 0.0;
        this.cashPaymentReceived = 0;
    }

    boolean hasItem(EAN ean) {
        return lineItems.containsKey(ean);
    }

    void addNewItem(Item item) {
        var newLineItem = new LineItem(item);
        var ean = item.getEAN();
        lineItems.put(ean, newLineItem);
        updateRunningTotal();
    }
    
    void incrementItemQuantity(EAN ean)  {
        var item = lineItems.get(ean);
        item.incrementQuantity();
        updateRunningTotal();
    }

    private void updateRunningTotal() {
        var total = 0.0;
        for (var lineItem : lineItems.values()) {
            total += lineItem.getPriceIncludingVat();
        }
        runningTotal = total;
    }

    public Collection<LineItem> getLineItems() {
        return this.lineItems.values();
    }

    /**
     * @return the runningTotal
     */
    public double getRunningTotal() {
        return runningTotal;
    }

    public double getTotalVat() {
        double totalVat = 0.0;
        for(var lineItem : getLineItems()) {
            totalVat += lineItem.getVatAmount();
        }
        return totalVat;
    }

    public SaleDTO toDTO() {
        var numOfLineItems = lineItems.size();
        var lineItemDTOList = new ArrayList<LineItemDTO>(numOfLineItems);
        for(var lineItem : lineItems.values()) {
            lineItemDTOList.add(new LineItemDTO(lineItem.getItem(), lineItem.getQuantity()));
        }
        return new SaleDTO(this.runningTotal, lineItemDTOList);
    } 

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for(var lineItem : getLineItems()) {
            sb.append(lineItem.toString()).append("\n");
        } 
        sb.append(String.format("Total:  %.2f:- varav %.2f kr moms \n", getRunningTotal(), getTotalVat()));
        return sb.toString();
    }
    
}
