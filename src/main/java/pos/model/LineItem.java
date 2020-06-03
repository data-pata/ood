package pos.model;

import pos.integration.dataobjects.EAN;
import pos.integration.dataobjects.Item;
import pos.integration.dataobjects.LineItemDTO;

public class LineItem {
    private final Item item;
    private int quantity;
    
    public LineItem(Item item) {
     this(item, 1);
    }
    public LineItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
     }
    
    void incrementQuantity() {
        this.quantity++;
    }
    void addQuantity(int qty) {
        this.quantity += qty;
    }
    void setQuantity(int quantity)  {
        this.quantity = quantity;
    }

    int getQuantity() {
        return this.quantity;
    }
    double getPriceIncludingVat() {
        return getVatAmount() + getPrice();
    }
    double getVatAmount() {
        return getPrice() * item.getVatRate();
    }
    double getPrice() {
        return item.getPrice() * this.quantity; 
    }

    EAN getEan() {
        return item.getEan();
    }
     /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    public LineItemDTO toDto() {
        return new LineItemDTO(this.getItem(), this.getQuantity());
    }

    @Override
    public String toString() {
        return String.format("%s\n      x %d  =  %.2f:-", getItem(), getQuantity(), getPriceIncludingVat());
    }

}