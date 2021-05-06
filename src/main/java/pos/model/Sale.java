package pos.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pos.dataobjects.EAN;
import pos.dataobjects.Item;
import pos.dataobjects.LineItemDTO;
import pos.dataobjects.SaleDTO;
import pos.integration.NoSuchItemException;

public class Sale {
    private Map<EAN, LineItem> lineItems;
    private double runningTotal;
    
    private int amountPaid;
    private int change;

    private  List<SaleObserver> saleObservers = new ArrayList<>();

    public Sale() {
        this.lineItems = new HashMap<EAN, LineItem>();
        this.runningTotal = 0.0;
    }
    
    public SaleDTO enterItem(EAN ean, int itemQty) throws NoSuchItemException {
        if(!hasItem(ean))
            throw new NoSuchItemException("No such item data in current sale", ean); 
        increaseQuantityBy(ean, itemQty);
        updateRunningTotal();
        return this.toDTO();
    }
    
    public SaleDTO enterNewItem(Item item, int itemQty) {
        var newLineItem = new LineItem(item, itemQty);
        var ean = item.getEan();
        lineItems.put(ean, newLineItem);
        updateRunningTotal();
        return this.toDTO();
    }

    private void increaseQuantityBy(EAN ean, int qty) {
        var lineItem = lineItems.get(ean);
        lineItem.addQuantity(qty);
    } 
    private boolean hasItem(EAN ean) {
        return lineItems.containsKey(ean);
    }

    /**
     * @return the runningTotal
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    public int getTotalPrice() {
        return (int) Math.round(getRunningTotal());
    }

    private void updateRunningTotal() {
        var total = 0.0;
        for (var lineItem : getLineItems()) {
            total += lineItem.getPriceIncludingVat();
        }
        runningTotal = total;
    }

    public int pay(int amountPaid) throws InsufficientPaymentException {
        int amountToPay = getTotalPrice();
        if(getTotalPrice() > amountPaid) {
            throw new InsufficientPaymentException(amountPaid, amountToPay);
        }
        this.amountPaid = amountPaid;
        this.change = amountPaid - amountToPay;
        notifyObservers();
        return this.change;
    }

    public void addSaleObserver(SaleObserver observer) {
        this.saleObservers.add(observer); 
    }
    private void notifyObservers() {
        for(var observer : saleObservers)
            observer.updateTotalRevenue(getTotalPrice());
    }

    private double getTotalVat() {
        double totalVat = 0.0;
        for (var lineItem : getLineItems()) {
            totalVat += lineItem.getVatAmount();
        }
        return totalVat;
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
        return new SaleDTO(this.runningTotal, lineItemDTOList, this.getTotalVat());
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
