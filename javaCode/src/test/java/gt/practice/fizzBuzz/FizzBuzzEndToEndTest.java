package gt.practice.fizzBuzz;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class FizzBuzzEndToEndTest {
    private FizzBuzz fizzBuzzUt = new FizzBuzz(new FizzBuzzConverter());

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
    @DisplayName("should cover negative integers")
    void coverNegativeIntegers() {
        String expected = "buzz -4 fizz -2 -1 fizzbuzz 1";
        assertThat(fizzBuzzUt.getFizzBuzzForRange(-5,1)).isEqualTo(expected);
    }

    @Ignore
    @DisplayName("should return lucky for a number that contains 3")
    void luckyForNumberContainingThree() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(23, 23)).isEqualTo("lucky");
    }
}
