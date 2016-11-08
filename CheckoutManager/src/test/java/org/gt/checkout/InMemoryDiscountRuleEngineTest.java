package org.gt.checkout;

import org.gt.checkout.domain.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
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
    }

    @Test
    public void applyDiscountOnItems() throws Exception {
        //Given
        BigDecimal expectedDiscount = BigDecimal.valueOf(0.50);
        Item apple = new Item("Apple", BigDecimal.valueOf(0.5));

        //When
        BigDecimal discount1 = inMemoryDiscountRuleEngine.apply(apple);
        BigDecimal discount2 = inMemoryDiscountRuleEngine.apply(apple);

        //Then
        assertThat(discount1).isEqualTo(BigDecimal.ZERO);
        assertThat(discount2.compareTo(expectedDiscount.negate())).isEqualTo(0);
    }

    @Test
    public void applyNoDiscountWhenNoneAvailable() throws Exception {
        //Given
        Item orange = new Item("hgdhghd", BigDecimal.valueOf(1.5));
        itemSet = new ArrayList<>();
        itemSet.add(orange);

        //When
        BigDecimal actualDiscount = inMemoryDiscountRuleEngine.apply(orange);

        //Then
        assertThat(actualDiscount).isEqualTo(BigDecimal.ZERO);
    }
}