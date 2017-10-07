package com.equalexperts.fb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzEndToEndTest {
    private FizzBuzz fizzBuzzUt = new FizzBuzz(new FizzBuzzConverter(), new FizzBuzzAggregator());

    @Test
    @DisplayName("Fizz Buzz should produce a string output with fizz, buzz and FizzBuzz")
    void fizzBuzzOutput() {
        String expectedOutput = "1 2 lucky 4 buzz fizz 7 8 fizz buzz 11 fizz lucky 14 fizzbuzz 16 17 fizz 19 buzz";
        String expectedReport = " fizz: 4 buzz: 3 fizzbuzz: 1 lucky: 2 integer: 10";
        assertThat(fizzBuzzUt.getFizzBuzzForRange(1,20))
                .isEqualTo(expectedOutput + expectedReport);
    }

    @Test
    @DisplayName("should returns fizzbuzz when range has only 0's")
    void fizzbuzzWhenRangeHasOnlyZeros() {
        assertThat(fizzBuzzUt.getFizzBuzzForRange(0,0))
                .isEqualTo("fizzbuzz fizz: 0 buzz: 0 fizzbuzz: 1 lucky: 0 integer: 0");
    }
}
