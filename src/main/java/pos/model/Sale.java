package pos.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pos.integration.dataobjects.EAN;
import pos.integration.dataobjects.Item;
import pos.integration.dataobjects.LineItemDTO;
import pos.integration.dataobjects.SaleDTO;

public class Sale {
    private Map<EAN, LineItem> lineItems;
    private double runningTotal;

    public Sale() {
        this.lineItems = new HashMap<EAN, LineItem>();
        this.runningTotal = 0.0;
    }
    
    public void enterItem(Item item) {
        if (this.hasItem(item))
            this.incrementItemQuantity(item.getEan());  
        else
            this.enterNewItem(item);
        updateRunningTotal();
    }

    private boolean hasItem(Item item) {
        return lineItems.containsKey(item.getEan());
    }

    private void enterNewItem(Item item) {
        var newLineItem = new LineItem(item);
        var ean = item.getEan();
        lineItems.put(ean, newLineItem);
    }
    
    private void incrementItemQuantity(EAN ean)  {
        var item = lineItems.get(ean);
        item.incrementQuantity();
    }

    /**
     * @return the runningTotal
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    
    private double getTotalVat() {
        double totalVat = 0.0;
        for(var lineItem : getLineItems()) {
            totalVat += lineItem.getVatAmount();
        }
        return totalVat;
    }

    private void updateRunningTotal() {
        var total = 0.0;
        for (var lineItem : getLineItems()) {
            total += lineItem.getPriceIncludingVat();
        }
        runningTotal = total;
    }
    
    private Collection<LineItem> getLineItems() {
        return this.lineItems.values();
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
