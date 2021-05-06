package pos.dataobjects;
import pos.integration.VAT;

public class Item {
	private final EAN ean;
	private final double price;
	private final String description;
	private final VAT vatRate;

	
	public Item(EAN ean, double price, String description, double vatRate) {
		this.ean = ean;
		this.price = price;
		this.description = description;
		this.vatRate = new VAT(vatRate);		
	}
	
	// should be hidden?
	public EAN getEan() {
		return ean;
	}
	public double getPrice() {
		return this.price;
	}

	public double getPriceIncludingVat() {
		return getPrice()+getPrice()*getVatRate();
	}

	public String getDescription() {
		return this.description;
	}
	public double getVatRate() {
		return vatRate.getVatRate();
	}

	@Override
	public String toString() {
		return String.format("%-40s %.2f:-", getDescription(), getPriceIncludingVat());
	}

	// @Override
	// public boolean equals(Object otherObject) {
	// 	if (otherObject == null || !(otherObject instanceof Item)) {
	// 		return false;
	// 	}
	// 	var otherItem = (Item) otherObject;
	// 	return this.getEan().equals(otherItem.getEan());
	// }
	
		
}