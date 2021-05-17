package pos.dataobjects;

import pos.model.LineItem;

/**
 * DTO for object of class <code>LineItem</code>
 * @see LineItem
 */
public final class LineItemDTO {
    private final Item itemData;
    private final int itemQuantity;

    public LineItemDTO(LineItem lineItem) {
        this(lineItem.getItem(), lineItem.getQuantity());
    }
    public LineItemDTO(Item ItemData, int itemQuantity) {
        this.itemData = ItemData;
        this.itemQuantity = itemQuantity;
    }

    /** 
     * @return Item
     */
    public Item getItemData() {
        return this.itemData;
    }

    /** 
     * @return int
     */
    public int getItemQuantity() {
        return this.itemQuantity;
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
            " ItemData='" + getItemData() + "'" +
            ", itemQuantity='" + getItemQuantity() + "'" +
            "}";
    }

}
