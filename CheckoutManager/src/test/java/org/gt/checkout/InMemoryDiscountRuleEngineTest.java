package org.gt.checkout;

import org.gt.checkout.domain.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InMemoryDiscountRuleEngineTest {
    private InMemoryDiscountRuleEngine inMemoryDiscountRuleEngine;
    private List<Item> itemSet;

    @Before
    public void setUp() throws Exception {
        inMemoryDiscountRuleEngine = new InMemoryDiscountRuleEngine();
        itemSet = mock(List.class);
    }

    @Test
    public void applyDiscountOnItems() throws Exception {
        //Given
        BigDecimal expectedDiscount = BigDecimal.valueOf(0.5);
        Item apple = new Item("apple", BigDecimal.valueOf(0.5));
        when(itemSet.contains(apple)).thenReturn(true);

        //When
        BigDecimal actualDiscount = inMemoryDiscountRuleEngine.apply(apple, itemSet);

        //Then
        assertThat(actualDiscount).isEqualTo(expectedDiscount.negate());
    }

    @Test
    public void applyNoDiscountWhenNoneAvailable() throws Exception {
        //Given
        Item orange = new Item("orange", BigDecimal.valueOf(1.5));
        when(itemSet.contains(orange)).thenReturn(false);

        //When
        BigDecimal actualDiscount = inMemoryDiscountRuleEngine.apply(orange, itemSet);

        //Then
        assertThat(actualDiscount).isEqualTo(BigDecimal.ZERO);
    }
}