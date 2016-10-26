package org.gt.checkout;

import org.gt.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public class Invoice {
    private BigDecimal totalPrice;
    private int totalCount;
    private List<Item> itemList;

    public Invoice(BigDecimal totalPrice,
                   int totalCount,
                   List<Item> itemList) {
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.itemList = itemList;
    }

    public BigDecimal totalPrice() {
        return totalPrice;
    }

    public int totalItems() {
        return totalCount;
    }

    public List<Item> items() {
        return itemList;
    }

    public List<Item> getTransactions() {
        return itemList;
    }
}
