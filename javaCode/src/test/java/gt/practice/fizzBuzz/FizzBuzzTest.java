package gt.practice.fizzBuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FizzBuzzTest {
    private Converter<Integer, String> converter = new FizzBuzzConverter();
    private FizzBuzz fizzBuzzUt = new FizzBuzz(converter);

    @Test
    @DisplayName("Test me")
    void shouldReturnFizzBuzz() {
        when(converter.convert())
        assertThat(fizzBuzzUt.getFizzBuzzForRange(1, 5))
                .isEqualTo("1 2 fizz 4 buzz");
    }



}
