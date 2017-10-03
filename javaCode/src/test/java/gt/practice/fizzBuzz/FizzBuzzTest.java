package gt.practice.fizzBuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzTest {
    private FizzBuzz fizzBuzzUt = new FizzBuzz();

    @Test
    @DisplayName("should returns fizzbuzz when range has only 0")
    void emptyStringWhen() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(0,0)).isEqualTo("fizzbuzz");
    }

    @Test
    @DisplayName("Fizz Buzz should produce a string output with fizz, buzz and FizzBuzz")
    void fizzzBuzOutput() {
        String expectedOutput = "1 2 fizz 4 buzz fizz 7 8 fizz buzz 11 fizz 13 14 fizzbuzz 16 17 fizz 19 buzz";
        assertThat(fizzBuzzUt.getFizzBuzzForRange(1,20)).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Fizz Buzz returns empty string when range does not have any numbers")
    void emptyWhenStartRangeIsMoreThanEndRange() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(20,0)).isEqualTo("");
    }

    @Test
    @DisplayName("should return fizz for just a single number divisible by 3")
    void shouldReturnFizz() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(3,3)).isEqualTo("fizz");
    }

    @Test
    @DisplayName("should return buzz for just a single number divisible by 5")
    void shouldReturnBuzz() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(5,5)).isEqualTo("buzz");
    }

    @Test
    @DisplayName("should return fizzbuzz for just a single number divisible by 15")
    void shouldReturnFizzBuzz() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(15,15)).isEqualTo("fizzbuzz");
    }

    @Test
    @DisplayName("should cover negative integers")
    void coverNegativeIntegers() {
        String expected = "buzz -4 fizz -2 -1 fizzbuzz 1";
        assertThat(fizzBuzzUt.getFizzBuzzForRange(-5,1)).isEqualTo(expected);
    }
}
