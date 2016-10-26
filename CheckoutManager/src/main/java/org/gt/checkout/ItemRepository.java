package org.gt.checkout;

import org.gt.checkout.domain.Item;

public interface ItemRepository {
    Item getItemDetails(String itemName) throws ItemNotFoundException;
}
