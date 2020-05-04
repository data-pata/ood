package pos.integration.dataobjects;
public class LineItemDTO {
    private final Item itemData;
    private final int itemQuantity;

    public LineItemDTO(Item ItemData, int itemQuantity) {
        this.itemData = ItemData;
        this.itemQuantity = itemQuantity;
    }

    public Item getItemData() {
        return this.itemData;
    }

    public double getItemQuantity() {
        return this.itemQuantity;
    }

    @Override
    public String toString() {
        return "{" +
            " ItemData='" + getItemData() + "'" +
            ", itemQuantity='" + getItemQuantity() + "'" +
            "}";
    }

}
