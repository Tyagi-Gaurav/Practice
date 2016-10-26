package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DefaultItemRepository implements ItemRepository {
    private static final Map<String, Item> itemMap = new HashMap<String, Item>();

    static {
        itemMap.put("APPLE", new Item("APPLE", BigDecimal.valueOf(0.50)));
        itemMap.put("ORANGE", new Item("ORANGE", BigDecimal.valueOf(0.50)));
        itemMap.put("MILK", new Item("MILK", BigDecimal.valueOf(0.80)));
        itemMap.put("EGGS", new Item("EGGS", BigDecimal.valueOf(1.50)));
        itemMap.put("BREAD", new Item("BREAD", BigDecimal.valueOf(1.50)));
    }

    public Item getItemDetails(String itemName) throws ItemNotFoundException {
        if (itemMap.containsKey(itemName.toUpperCase())) {
            return itemMap.get(itemName.toUpperCase());
        } else {
            throw new ItemNotFoundException("Invalid Item: " + itemName);
        }
    }
}
