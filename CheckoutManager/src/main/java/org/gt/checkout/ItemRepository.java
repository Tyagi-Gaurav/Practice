package org.gt.checkout;

import org.gt.checkout.domain.Item;

@FunctionalInterface
public interface ItemRepository {
    Item getItemDetails(String itemName) throws ItemNotFoundException;
}
