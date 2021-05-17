package pos.integration;

import java.util.HashMap;
import java.util.Map;

import pos.dataobjects.EAN;
import pos.dataobjects.InvalidEanException;
import pos.dataobjects.Item;
import pos.dataobjects.SaleLog;
/**
 * This class represents the (external) inventory system. It maintains all items
 * on sale in a store as data objects mapped by their corresponding EAN. It lets
 * a client query for an item and throws a NoSuchItemException if it is not in
 * the system. 
 */
public class InventorySystem {
    private Map<EAN, Item> itemRegistry;
    
    /**
     * Creates an InventorySystem instance.
     */
    public InventorySystem(){
        this.itemRegistry = new HashMap<EAN, Item>();
        this.addItems();
    }
    
    /**
     * Package private method getting item data from it's corresponding EAN.
     * 
     * @param ean   EAN object identifying the sought after item.
     * @return Item The sought-after item data object.
     * @throws NoSuchItemException if there is no item corresponding to the given EAN.
     */
    Item retrieveItemData(EAN ean) throws NoSuchItemException {
        if (!itemRegistry.containsKey(ean))
            throw new NoSuchItemException("No such item exists in the inventory", ean);
        return itemRegistry.get(ean);
    }

    /**
     * Updates the inventory system after a completed sale.
     * @param saleLog   all data on the completed sale. 
     */
    void update(SaleLog saleLog) {
        System.out.println("[Inventory System update successful]");
    }

    private void addItemtoRegistry(String eanCode, double priceExcludingVat, String itemDescription, double vatRate) {
        EAN ean;
        try {
            ean = new EAN(eanCode);
            var item = new Item(ean, priceExcludingVat, itemDescription, vatRate);
            this.itemRegistry.put(ean, item);
        
        } catch (InvalidEanException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } 
    }

    private void addItems() {
        addItemtoRegistry("7310090348139", 20.98, "Blandsaft Jordgubb BOB 0,95 l", 0.12);
        addItemtoRegistry("7300156486424", 8.88, "Leverpastej Gurka Svensk Coop. 200 GR", 0.12);
        addItemtoRegistry("77315009", 39.92, "Vax Wave & Groom Röd DAX. 99g.", 0.25);
        addItemtoRegistry("8717418553401", 168.87, "Lejonkungen Walt Disney. 1st.", 0.06);
    }
}