package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.*;

public class InMemoryDiscountRuleEngine implements DiscountRuleEngine {

    private Map<String, DiscountItem> discountedSet = new HashMap<>();
    private Map<Item, Integer> eligibleItemsForDiscount = new HashMap<>();

    public InMemoryDiscountRuleEngine() {
        discountedSet.put("APPLE", new DiscountItem("APPLE", 2, new BigDecimal(50)));
        discountedSet.put("ORANGE", new DiscountItem("ORANGE", 1, new BigDecimal(10)));
        discountedSet.put("BANANA", new DiscountItem("BANANA", 5, new BigDecimal(10)));
    }

    public BigDecimal apply(Item item) {
        DiscountItem discountedItem = discountedSet.get(item.name().toUpperCase());
        if (discountedItem != null) {
            updateEligibleItemListWith(item);
            BigDecimal reduce = eligibleItemsForDiscount
                    .keySet()
                    .stream()
                    .filter(this::isDiscountApplicable)
                    .map(this::calculateDiscount)
                    .reduce(BigDecimal.ZERO, (x, acc) -> x.add(acc));
            return reduce.negate();
        }

        return BigDecimal.ZERO;
    }

    private boolean isDiscountApplicable(Item item) {
        return item != null && (eligibleItemsForDiscount.get(item) ==
                discountedSet.get(item.name().toUpperCase()).getQty());
    }

    private BigDecimal calculateDiscount(Item item) {
        DiscountItem discountItem = discountedSet.get(item.name().toUpperCase());
        BigDecimal discount = discountItem.getPercentage();
        int qty = discountItem.getQty();
        BigDecimal newDiscount = discount.divide(BigDecimal.valueOf(100));
        return item.price().multiply(newDiscount).multiply(BigDecimal.valueOf(qty));
    }

    private void updateEligibleItemListWith(Item discountedItem) {
        eligibleItemsForDiscount
                .computeIfPresent(discountedItem, (x, y) -> new Integer(y+1));
        eligibleItemsForDiscount
                .computeIfAbsent(discountedItem, (x) -> new Integer(1));
    }

    class DiscountItem {
        private String itemCode;
        private int qty;
        private BigDecimal percentage;

        public DiscountItem(String itemCode, int qty, BigDecimal percentage) {
            this.itemCode = itemCode;
            this.qty = qty;
            this.percentage = percentage;
        }

        public int getQty() {
            return qty;
        }

        public String getItemCode() {
            return itemCode;
        }

        public BigDecimal getPercentage() {
            return percentage;
        }
    }
}
