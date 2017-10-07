package com.equalexperts.fb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;


public class FizzBuzzConverterTest {
    private IntFunction<String> converter = new FizzBuzzConverter();

    @Test
    @DisplayName("should return lucky for just a single number 3")
    void shouldReturnLucky() {
        assertThat(converter.apply(3)).isEqualTo("lucky");
    }

    @Test
    @DisplayName("should return fizz for single number divisible by 3")
    void shouldReturnFizz() {
        assertThat(converter.apply(18)).isEqualTo("fizz");
    }

    @Test
    @DisplayName("should return buzz for just a single number divisible by 5")
    void shouldReturnBuzz() {
        assertThat(converter.apply(25)).isEqualTo("buzz");
    }

    @Test
    @DisplayName("should return buzz for just a single number 5")
    void shouldReturnBuzz2() {
        assertThat(converter.apply(5)).isEqualTo("buzz");
    }

    @Test
    @DisplayName("should return fizzbuzz for a number divisible by 3, 5 and 15")
    void shouldReturnFizzBuzz2() {
        assertThat(converter.apply(45)).isEqualTo("fizzbuzz");
    }

    @Test
    @DisplayName("should return fizzbuzz for just a single number divisible by 15")
    void shouldReturnFizzBuzz() {
        assertThat(converter.apply(15)).isEqualTo("fizzbuzz");
    }

    @Test
    @DisplayName("If number contains 3 then it must output lucky")
    void outputLuckyIfNumbercontainsThree() {
        assertThat(converter.apply(23)).isEqualTo("lucky");
    }
}
