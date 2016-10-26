package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class CheckoutManager {

    private ItemRepository defaultItemRepository;
    private InMemoryDiscountRuleEngine inMemoryDiscountRuleEngine;

    public CheckoutManager(ItemRepository defaultItemRepository,
                           InMemoryDiscountRuleEngine inMemoryDiscountRuleEngine) {
        this.defaultItemRepository = defaultItemRepository;
        this.inMemoryDiscountRuleEngine = inMemoryDiscountRuleEngine;
    }

    public Invoice checkoutItemsIn(List<String> cart) {
        final BigDecimal sum = BigDecimal.ZERO;
        List<Item> shoppingCart = new ArrayList<Item>();

        cart.stream().forEach(itemName -> {
            try {
                Item item = defaultItemRepository.getItemDetails(itemName);
                shoppingCart.add(item);
                Optional<Item> discountItem = applyDiscountFor(item, shoppingCart);
                if (discountItem.isPresent()) {
                    shoppingCart.add(discountItem.get());
                }
            } catch (ItemNotFoundException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        });

        List<Item> currentList = new ArrayList<Item>();
        currentList.addAll(shoppingCart);
        int itemCounts = currentList.size();
        BigDecimal totalPrice = currentList.stream().reduce(BigDecimal.ZERO,
                (BigDecimal acc, Item item) -> {
                    BigDecimal add = acc.add(item.price());
                    System.out.println("Adding " + item.price() + "with " + acc);
                    System.out.println("Result : " + add);
                    return add;
                },
                BigDecimal::add);

        return new Invoice(totalPrice,
                           itemCounts,
                           currentList);
    }

    private Optional<Item> applyDiscountFor(Item itemDetails, List<Item> itemSet) {
        BigDecimal discount = inMemoryDiscountRuleEngine.apply(itemDetails, itemSet);
        Optional<Item> discountItem = Optional.empty();
        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            discountItem = Optional.of(new Item("Discount: " + itemDetails.name(), discount));
        }

        return discountItem;
    }

    private void addItemsTo(List<Item> itemSet, Item itemDetails) {
        itemSet.add(itemDetails);
    }
}
