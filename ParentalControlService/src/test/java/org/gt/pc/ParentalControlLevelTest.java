package org.gt.pc;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gt.pc.ParentalControlLevel.*;

public class ParentalControlLevelTest {
    @Test
    void shouldMaintainOrderOfValues() {
        assertThat(values())
                .containsExactly(
                        U,
                        PG,
                        TWELVE,
                        FIFTEEN,
                        EIGHTEEN
                );
    }

    @Test
    void returnTrueWhenSourceIsHigherThanInput() {
        assertThat(EIGHTEEN.isHigherThan(FIFTEEN)).isTrue();
    }

    @Test
    void returnTrueWhenSourceIsSameAsInput() {
        assertThat(EIGHTEEN.isHigherThan(EIGHTEEN)).isTrue();
    }

    @Test
    void returnFalseWhenSourceIsLowerThanInput() {
        assertThat(TWELVE.isHigherThan(EIGHTEEN)).isFalse();
    }

    @Test
    void throwErrorWhenInvalidParentalControlLevelIsUsed() {
        assertThatThrownBy(() -> ParentalControlLevel.getFrom(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();
    }

    @Test
    void shouldConvertParentalControlLevelFor15() {
        assertThat(ParentalControlLevel.getFrom("15"))
                .isEqualTo(FIFTEEN);

    }
}
