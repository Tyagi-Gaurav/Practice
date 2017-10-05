package gt.practice.fizzBuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzConverterTest {
    private Converter converterUt = new FizzBuzzConverter();

    @Test
    @DisplayName("should return fizz for just a single number 3")
    void shouldReturnFizz() {
        assertThat(converterUt.convert(3)).isEqualTo("fizz");
    }

    @Test
    @DisplayName("should return buzz for just a single number divisible by 5")
    void shouldReturnBuzz() {
        assertThat(converterUt.convert(5)).isEqualTo("buzz");
    }

    @Test
    @DisplayName("should return fizzbuzz for just a single number divisible by 15")
    void shouldReturnFizzBuzz() {
        assertThat(converterUt.convert(15)).isEqualTo("fizzbuzz");
    }
}
