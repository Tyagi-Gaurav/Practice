package org.gt.checkout.e2e;

import org.gt.checkout.CheckoutManager;
import org.gt.checkout.DefaultItemRepository;
import org.gt.checkout.InMemoryDiscountRuleEngine;
import org.gt.checkout.Invoice;
import org.gt.checkout.domain.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CheckoutManagerE2ETest {
    public static final MathContext PRECISION = new MathContext(2);
    private CheckoutManager checkoutManager;
    private List<String> mockCart;

    @Before
    public void setUp() throws Exception {
        checkoutManager = new CheckoutManager(new DefaultItemRepository(),
                  new InMemoryDiscountRuleEngine());
    }

    @Test
    public void checkoutSingleItemWithoutDiscount() throws Exception {
        //Given
        mockCart = createSingleMockItemList();
        BigDecimal expectedTotalPrice = new BigDecimal(0.5);

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(mockCart);

        //Then
        assertThat(invoice.totalPrice()).isEqualTo(expectedTotalPrice);
    }

    @Test
    public void throwExceptionWhenHasInvalidItem() throws Exception {
        //Given
        mockCart = createSingleMockItemList();
        mockCart.add("BoxOfAir");

        //When
        try {
            checkoutManager.checkoutItemsIn(mockCart);
            fail("Expected Exception");
        } catch(Exception e) {
            assertThat(e.getClass()).isEqualTo(IllegalArgumentException.class);
            assertThat(e.getMessage()).isEqualTo("Invalid Item: BoxOfAir");
        }
    }

    @Test
    public void checkoutMultipleItemsWithoutDiscount() throws Exception {
        //Given
        mockCart = createMultipleMockItemList();
        BigDecimal expectedTotalPrice = new BigDecimal(4.8, new MathContext(2));

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(mockCart);

        //Then
        assertThat(invoice.totalPrice()).isEqualTo(expectedTotalPrice);
    }

    @Test
    public void checkoutItemsWithDiscount() throws Exception {
        //Given
        mockCart = createMultipleMockItemList();
        mockCart.add("Apple");
        BigDecimal expectedTotal = new BigDecimal(4.3, PRECISION);

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(mockCart);

        //Then
        assertThat(invoice.totalPrice()).isEqualTo(expectedTotal);
    }

    @Test
    public void afterCompleteCheckoutReturnInvoiceWhichHasAllItemsInCart() throws Exception {
        //Given
        mockCart = createMultipleMockItemList();
        mockCart.add("Apple");
        BigDecimal expectedTotal = new BigDecimal(4.3, PRECISION);

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(mockCart);

        //Then
        assertThat(invoice).isNotNull();
        assertThat(invoice.totalPrice()).isEqualTo(expectedTotal);
    }

    @Test
    public void invoiceShouldIndicateTotalCountOfAllItemsInCart() throws Exception {
        //Given
        mockCart = createMultipleMockItemList();
        mockCart.add("Apple");
        mockCart.add("Orange");
        BigDecimal expectedTotal = new BigDecimal(4.8, PRECISION);

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(mockCart);

        //Then
        assertThat(invoice).isNotNull();
        assertThat(invoice.totalPrice()).isEqualTo(expectedTotal);
        assertThat(invoice.totalItems()).isEqualTo(7);
    }

    private List<String> createMultipleMockItemList() {
        List<String> itemList = new ArrayList<String>();
        itemList.add("Apple");
        itemList.add("Orange");
        itemList.add("Milk");
        itemList.add("Bread");
        itemList.add("Eggs");

        return itemList;
    }

    private List<String> createSingleMockItemList() {
        List<String> itemList = new ArrayList<String>();
        itemList.add("Apple");

        return itemList;
    }
}
