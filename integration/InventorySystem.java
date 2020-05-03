package integration;

import java.util.HashMap;
import java.util.Map;

import integration.dataobjects.EAN;
import integration.dataobjects.Item;
import integration.dataobjects.SaleLog;

public class InventorySystem {
    private Map<EAN, Item> itemRegistry;

    public InventorySystem(){
        this.itemRegistry = new HashMap<EAN, Item>();
        addItemtoRegistry("7310090348139", 20.98, "Blandsaft Jordgubb BOB 0,95 l", 0.12);
        addItemtoRegistry("7300156486424", 8.88, "Leverpastej Gurka Svensk Coop. 200 GR", 0.12);
        addItemtoRegistry("77315009042", 39.92, "Vax Wave & Groom Röd DAX. 99g.", 0.25);
        addItemtoRegistry("8717418553401", 168.87, "Lejonkungen Walt Disney. 1st.", 0.06);
    }
    
    private void addItemtoRegistry(String eanCode, double priceExcludingVat, String itemDescription, double vatRate) {
        var ean = new EAN(eanCode);
        var item = new Item(ean, priceExcludingVat, itemDescription, vatRate);
        this.itemRegistry.put(ean, item);
    }

    Item retrieveItemData(EAN ean) throws NoSuchItemException {
    
        if( itemRegistry.containsKey(ean) ) {
            return itemRegistry.get(ean);
        }
        else {
            throw new NoSuchItemException(
                String.format("EAN %s was not found in the product catalog", ean));
        }
    }



    void update(SaleLog saleLog) {
        System.out.println("[Inventory System update successful]");
    }
    
}