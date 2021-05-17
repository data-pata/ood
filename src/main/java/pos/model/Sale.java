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
/**
 * This class represents an ongoing sale in the POS system. Consequently it
 * holds all lineitems of the sale and the runningtotal. As a
 * convenience it supplies methods returning calculated total with VAT included
 * and excluded.  Added observers are notified on succesful payment. 
 */
public class Sale {
    private Map<EAN, LineItem> lineItems;
    private double runningTotal;
    private int amountPaid;
    private int change;
    private  List<SaleObserver> saleObservers = new ArrayList<>();
    /**
     * Creates a new instance of this class. Ready to keep track of a sale in
     * the POS system.
     */
    public Sale() {
        this.lineItems = new HashMap<EAN, LineItem>();
        this.runningTotal = 0.0;
    }
    
    /**
     * Enters an item of quantity, itemQty, to this sale and updates the
     * runningtotal. A new data object representing the updated current sale is
     * returned. As opposed to {@link #enterNewItem(EAN, int)}, this method enters
     * an item already registered in this sale.
     * 
     * @param ean       entered item EAN identifyer. 
     * @param itemQty   the quantity of the entered item.
     * @return SaleDTO  data object representing the updated current sale.
     * @throws NoSuchItemException if there if this item hasn't been entered in this sale before.
     */
    public SaleDTO enterItem(EAN ean, int itemQty) throws NoSuchItemException {
        if(!hasItem(ean))
            throw new NoSuchItemException("No such item data in current sale", ean); 
        increaseQuantityBy(ean, itemQty);
        updateRunningTotal();
        return this.toDTO();
    }
    
    /**
     * Enters a new item of quantity, itemQty, to this sale and updates the
     * runningtotal. A new data object representing the updated current sale is
     * returned. As opposed to {@link #enterItem(EAN, int)}, this method enters an
     * item not already registered in this sale.
     *
     * @param item      entered item. 
     * @param itemQty   the quantity of the entered item. 
     * @return SaleDTO  data object representing the updated current sale.
     */
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
    
    /** 
     * Does this sale has an item of EAN ean?
     * @param ean       the EAN of sought-after item.
     * @return boolean  true if the item is present. False otherwise.
     */
    private boolean hasItem(EAN ean) {
        return lineItems.containsKey(ean);
    }

    /** 
     * Get the running total including VAT of this sale.
     * @return double
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    
    /** 
     * @return int
     */
    private int getRunningTotalRounded() {
        return (int) Math.round(getRunningTotal());
    }

    private void updateRunningTotal() {
        var total = 0.0;
        for (var lineItem : getLineItems()) {
            total += lineItem.getPriceIncludingVat();
        }
        runningTotal = total;
    }

    /** 
     * Register payment for the current sale and return the change. Notifies
     * observers, if any, and updates state of the sale.
     *
     * @param amountPaid    the amount of the payment
     * @return int          the change after paying.
     * @throws InsufficientPaymentException when the payment doesn't cover the
     * cost of the sale.
     */
    public int pay(int amountPaid) throws InsufficientPaymentException {
        int amountToPay = getRunningTotalRounded();
        if(getRunningTotalRounded() > amountPaid) {
            throw new InsufficientPaymentException(amountPaid, amountToPay);
        }
        this.amountPaid = amountPaid;
        this.change = amountPaid - amountToPay;
        notifyObservers();
        return this.change;
    }

    /** 
     * Add a SaleObserver to observe this sale.
     * @see SaleObserver
     *  
     * @param observer SaleObserver to add.  
     */
    public void addSaleObserver(SaleObserver observer) {
        this.saleObservers.add(observer); 
    }
    private void notifyObservers() {
        for(var observer : saleObservers)
            observer.updateTotalRevenue(getRunningTotalRounded());
    }

    /** 
     * Calculate the total VAT of this sale.
     * @return double   the total VAT of this sale
     */
    private double getTotalVat() {
        double totalVat = 0.0;
        for (var lineItem : getLineItems()) {
            totalVat += lineItem.getVatAmount();
        }
        return totalVat;
    }
    
    /** 
     * Gets all 
     * @return Collection<LineItem>
     */
    private Collection<LineItem> getLineItems() {
        return this.lineItems.values();
    }

    /** 
     * Get an immutable data object representation of the current state of
     * this sale.
     * @return SaleDTO
     */
    public SaleDTO toDTO() {
        var numOfLineItems = lineItems.size();
        var lineItemDTOList = new ArrayList<LineItemDTO>(numOfLineItems);
        for(var lineItem : lineItems.values()) {
            lineItemDTOList.add(new LineItemDTO(lineItem.getItem(), lineItem.getQuantity()));
        }
        return new SaleDTO(this.runningTotal, lineItemDTOList, this.getTotalVat());
    } 

    /**
     * A string representation of this sale.  
     * @return String
     */
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
