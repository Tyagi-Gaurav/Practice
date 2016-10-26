package org.gt.checkout;

import org.gt.checkout.domain.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CheckoutManagerTest {
    private static final String APPLE = "apple";
    private static final String INVALID_ITEM_NAME = "InvalidItem";

    private static final MathContext PRECISION = new MathContext(2);
    public static final String ORANGE = "orange";

    private CheckoutManager checkoutManager;
    private ItemRepository mockItemRepository;
    private InMemoryDiscountRuleEngine mockInMemoryDiscountRuleEngine;

    @Before
    public void setUp() throws Exception {
        mockItemRepository = mock(ItemRepository.class);
        mockInMemoryDiscountRuleEngine = mock(InMemoryDiscountRuleEngine.class);
        checkoutManager = new CheckoutManager(mockItemRepository, mockInMemoryDiscountRuleEngine);

        when(mockInMemoryDiscountRuleEngine.apply(any(Item.class), any(List.class))).thenReturn(BigDecimal.ZERO);
    }

    @Test
    public void useItemRepositoryToGetItems() throws Exception {
        //Given
        Item appleItem = createItem(APPLE);
        when(mockItemRepository.getItemDetails(APPLE)).thenReturn(appleItem);
        List<String> cart = new ArrayList<String>();
        cart.add(APPLE);

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(cart);

        //Then
        assertThat(invoice).isNotNull();
        assertThat(invoice.totalPrice()).isEqualTo(appleItem.price());
        assertThat(invoice.totalItems()).isEqualTo(1);
        verify(mockItemRepository).getItemDetails(APPLE);
    }

    @Test
    public void throwExceptionWhenItemNotFound() throws Exception {
        //Given
        when(mockItemRepository.getItemDetails(INVALID_ITEM_NAME)).thenThrow(ItemNotFoundException.class);
        List<String> cart = new ArrayList<String>();
        cart.add(INVALID_ITEM_NAME);

        //When
        try {
            checkoutManager.checkoutItemsIn(cart);
            fail("Expected Exception");
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(IllegalArgumentException.class);
        }
    }

    @Test
    public void applyDiscountOnOneItem() throws Exception {
        //Given
        Item appleItem = createItem(APPLE);
        Item orangeItem = createItem(ORANGE);
        List<String> cart = new ArrayList<String>();
        cart.add(APPLE);
        cart.add(ORANGE);
        cart.add(APPLE);

        when(mockItemRepository.getItemDetails(APPLE)).thenReturn(appleItem);
        when(mockItemRepository.getItemDetails(ORANGE)).thenReturn(orangeItem);
        when(mockInMemoryDiscountRuleEngine.apply(eq(appleItem), any(List.class)))
                .thenReturn(appleItem.price())
                .thenReturn(appleItem.price().negate());

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(cart);

        //Then
        assertThat(invoice).isNotNull();
        BigDecimal result = orangeItem.price().add(appleItem.price());
        assertThat(invoice.totalPrice()).isEqualTo(result);
        assertThat(invoice.totalItems()).isEqualTo(4);
        verify(mockInMemoryDiscountRuleEngine, times(2)).apply(eq(appleItem), any(List.class));
        verify(mockInMemoryDiscountRuleEngine).apply(eq(orangeItem), any(List.class));
    }

    @Test
    public void invoiceToHaveEachItemInformation() throws Exception {
        //Given
        Item appleItem = createItem(APPLE);
        Item orangeItem = createItem(ORANGE);
        List<String> cart = new ArrayList<String>();
        cart.add(APPLE);
        cart.add(ORANGE);
        cart.add(APPLE);

        when(mockItemRepository.getItemDetails(APPLE)).thenReturn(appleItem);
        when(mockItemRepository.getItemDetails(ORANGE)).thenReturn(orangeItem);
        when(mockInMemoryDiscountRuleEngine.apply(eq(appleItem), any(List.class)))
                .thenReturn(appleItem.price())
                .thenReturn(appleItem.price().negate());

        //When
        Invoice invoice = checkoutManager.checkoutItemsIn(cart);

        //Then
        assertThat(invoice).isNotNull();
        verifyTransactionsIn(invoice);

    }

    private void verifyTransactionsIn(Invoice invoice) {
        assertThat(invoice.getTransactions()).isNotNull();
        assertThat(invoice.getTransactions().size()).isEqualTo(4);
        List<Item> transactions = invoice.getTransactions();

        assertThat(transactions.get(0).name()).isEqualTo(APPLE);
        assertThat(transactions.get(1).name()).isEqualTo(ORANGE);
        assertThat(transactions.get(2).name()).isEqualTo(APPLE);
        assertThat(transactions.get(3).name()).isEqualTo("Discount: " + APPLE);
    }

    private Item createItem(String itemName) {
        BigDecimal price = new BigDecimal(new Random().nextDouble(), PRECISION);
        Item item = new Item(itemName, price);
        System.out.println(item);
        return item;
    }
}
