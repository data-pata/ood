package pos.dataobjects;
import pos.integration.VAT;
/**
 * An immutable representation of a consumer item, essentially a DTO.
 * Encapsulates item data: EAN code, price excl. VAT, description and VAT rate.
 * Provides getter methods for all of the these and a getter for the calculated
 * data: price including VAT. 
 */
public class Item {
	private final EAN ean;
	private final double price;
	private final String description;
	private final VAT vatRate;
	
	/**
	 * Sole constructor. Creates a VAT object to validate and contain given VAT rate. 
	 * 
	 * @param ean			an EAN identifier.
	 * @param price			a price excluding VAT.
	 * @param description	an item description.
	 * @param vatRate		a VAT rate. 
	 */
	public Item(EAN ean, double price, String description, double vatRate) {
		this.ean = ean;
		this.price = price;
		this.description = description;
		this.vatRate = new VAT(vatRate);		
	}

	/** 
	 * Get the EAN identifier.
	 * @return EAN
	 */
	public EAN getEan() {
		return ean;
	}
	
	/** 
	 * Get the price of this item.
	 * @return double
	 */
	public double getPrice() {
		return this.price;
	}

	/** 
	 * Calculates the item price including VAT.
	 * @return double	price including VAT.
	 */
	public double getPriceIncludingVat() {
		return getPrice() + (getPrice()*getVatRate());
	}

	/** 
	 * Get the description of this item.
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}
	
	/** 
	 * Get the VAT rate of this item.
	 * @return double
	 */
	public double getVatRate() {
		return vatRate.getVatRate();
	}

	/** 
	 * A string representation of this item. 
	 * @return String
	 */
	@Override
	public String toString() {
		return String.format("%-40s %.2f:-", getDescription(), getPriceIncludingVat());
	}		
}