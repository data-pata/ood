package pos.model;

import pos.dataobjects.EAN;
import pos.dataobjects.Item;
import pos.dataobjects.LineItemDTO;
/**
 * 
 * This class represents a lineitem in an ongoing sale. It encapsulates an item
 * (@see Item) and quantity entered. Supplies a cli 
 */
public class LineItem {
    private final Item item;
    private int quantity;

    /**
     * Creates lineitem of given item with a quantity of one.
     * @param item  item of this line item.
     */
    public LineItem(Item item) {
     this(item, 1);
    }
    /**
     * Creates lineitem of given item and quantity.
     * 
     * @param item      item of this line item.
     * @param quantity  how many of the item registered.
     */
    public LineItem(Item item, int quantity) {
        this.item = item;
        this.setQuantity(quantity);
     }
    
    /** 
     * Increase the quantity of the item in the lineitem
     * @param qty   the quantity to increase by.
     */
    void addQuantity(int qty) {
        this.quantity += qty;
    }
    
    /** 
     * Set the quantity of the item in this lineitem.
     * @param qty
     */
    void setQuantity(int qty)  {
        if (qty < 0)
            throw new IllegalArgumentException("Quantity " + qty +", less than zero is now valid.");
        this.quantity = qty;
    }

    /** 
     * Get the quantity entered in this lineitem.
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }
    
    /** 
     * Get the total price including VAT of this lineitem.
     * @return double
     */
    double getPriceIncludingVat() {
        return getVatAmount() + getPrice();
    }
    
    /** 
     * Get the total VAT of this lineitem. 
     * @return double
     */
    double getVatAmount() {
        return getPrice() * item.getVatRate();
    }
    
    /** 
     * Get the price of this lineitem excluding VAT. 
     * @return double
     */
    double getPrice() {
        return item.getPrice() * this.quantity; 
    }

    /** 
     * Get the EAN identifying this lineitem.
     * @return EAN
     */
    EAN getEan() {
        return item.getEan();
    }
     /**
      * Get the item.
     * @return the item
     */
    public Item getItem() {
        return item;
    }
    
    /** 
     * Turn this lineitem into an immutable data transfer object.
     * @return LineItemDTO
     */
    public LineItemDTO toDto() {
        return new LineItemDTO(this.getItem(), this.getQuantity());
    }

    /** 
     * String representation of a line item.
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s\n      x %d  =  %.2f:-", getItem(), getQuantity(), getPriceIncludingVat());
    }

}