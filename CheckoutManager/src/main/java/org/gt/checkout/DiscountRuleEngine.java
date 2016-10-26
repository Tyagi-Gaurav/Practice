package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountRuleEngine {
    BigDecimal apply(Item item, List<Item> itemSet);
}
