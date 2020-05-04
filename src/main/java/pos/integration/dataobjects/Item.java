package pos.integration.dataobjects;

public class Item {
	private final EAN ean;
	private final double price;
	private final String description;
	private final double vatRate;
	
	public Item(EAN ean, double price, String description, double vatRate) {
		this.ean = ean;
		this.price = price;
		this.description = description;
		this.vatRate = vatRate;
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
		return vatRate;
	}

	@Override
	public String toString() {
		return String.format("%-40s %.2f:-", getDescription(), getPriceIncludingVat());
	}	
	
		
}