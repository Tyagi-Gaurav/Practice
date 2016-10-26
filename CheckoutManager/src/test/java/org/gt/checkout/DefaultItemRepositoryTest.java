package org.gt.checkout;

import org.gt.checkout.domain.Item;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.junit.Assert.fail;

public class DefaultItemRepositoryTest {
    private ItemRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        itemRepository = new DefaultItemRepository();
    }

    @Test
    public void getItemDetails() throws Exception {
        //When
        Item apple = itemRepository.getItemDetails("apple");

        //Then
        assertThat(apple).isNotNull();
        assertThat(apple.name()).isEqualTo("APPLE");
        assertThat(apple.price()).isEqualTo(BigDecimal.valueOf(0.5));
    }

    @Test
    public void throwItemNotFoundExceptionWhenItemDoesNotExist() throws Exception {
        //When
        try {
            itemRepository.getItemDetails("YYTTDD");
            fail("Expected Exception");
        } catch(Exception e) {
            assertThat(e.getClass()).isEqualTo(ItemNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("Invalid Item: YYTTDD");
        }
    }
}