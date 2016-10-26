package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public class InMemoryDiscountRuleEngine implements DiscountRuleEngine {

    public BigDecimal apply(Item item, List<Item> itemSet) {
        long count = itemSet.stream().filter(x -> "APPLE".equals(x.name())).count();
        if (count == 2) {
            return item.price().negate();
        }

        return BigDecimal.ZERO;
    }
}
