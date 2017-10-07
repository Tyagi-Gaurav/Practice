package com.equalexperts.fb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class FizzBuzzTest {
    private IntFunction<String> fizzBuzzConverter = mock(FizzBuzzConverter.class);
    private Function<List<String>, String> aggregator = mock(FizzBuzzAggregator.class);

    private FizzBuzz fizzBuzzUt = new FizzBuzz(fizzBuzzConverter);

    @Test
    @DisplayName("should return fizz for just a single number 3")
    void shouldReturnFizzForSingleNumberThree() {
        //When
        when(fizzBuzzConverter.apply(3)).thenReturn("fizz");

        //Then
        assertThat(fizzBuzzUt.getFizzBuzzForRange(3,3)).isEqualTo("fizz");
        verify(fizzBuzzConverter).apply(3);
    }

    @Test
    @DisplayName("should return fizz for just a single number 5")
    void shouldReturnBuzzForSingleNumberFive() {
        //When
        when(fizzBuzzConverter.apply(5)).thenReturn("buzz");

        //Then
        assertThat(fizzBuzzUt.getFizzBuzzForRange(5,5)).isEqualTo("buzz");
        verify(fizzBuzzConverter).apply(5);
    }

    @Test
    @DisplayName("should return fizzbuzz for just a single number 15")
    void shouldReturnBuzzForSingleNumberFifteen() {
        //When
        when(fizzBuzzConverter.apply(15)).thenReturn("fizzbuzz");

        //Then
        assertThat(fizzBuzzUt.getFizzBuzzForRange(15,15)).isEqualTo("fizzbuzz");
        verify(fizzBuzzConverter).apply(15);
    }

    @Test
    @DisplayName("Fizz Buzz returns empty string when range does not have any numbers")
    void emptyWhenStartRangeIsMoreThanEndRange() {
        //When
        //Then
        assertThat(fizzBuzzUt.getFizzBuzzForRange(20,0)).isEqualTo("");
        verify(fizzBuzzConverter, never()).apply(anyInt());
    }

    @Test
    @DisplayName("should cover negative integers")
    void coverNegativeIntegers() {
        //When
        when(fizzBuzzConverter.apply(-2)).thenReturn("-2");
        when(fizzBuzzConverter.apply(-1)).thenReturn("-1");
        when(fizzBuzzConverter.apply(0)).thenReturn("fizzbuzz");
        when(fizzBuzzConverter.apply(1)).thenReturn("1");

        //Then
        String expected = "-2 -1 fizzbuzz 1";
        assertThat(fizzBuzzUt.getFizzBuzzForRange(-2,1)).isEqualTo(expected);
        verify(fizzBuzzConverter, times(4)).apply(anyInt());
    }

    @Test
    @DisplayName("should generate report using aggregator when custom aggregator is passed in")
    void generateReport() {
        //Given
        fizzBuzzUt = new FizzBuzz(fizzBuzzConverter, aggregator);

        //When
        List<String> expectedTokens = Arrays.asList("4", "buzz", "fizz");
        String expectedOutput = " fizz: 1 buzz: 1 fizzbuzz: 0 lucky: 0 integer: 1";

        when(fizzBuzzConverter.apply(4)).thenReturn("4");
        when(fizzBuzzConverter.apply(5)).thenReturn("buzz");
        when(fizzBuzzConverter.apply(6)).thenReturn("fizz");
        when(aggregator.apply(expectedTokens)).thenReturn(expectedOutput);

        //Then
        assertThat(fizzBuzzUt.getFizzBuzzForRange(4,6))
                .isEqualTo("4 buzz fizz fizz: 1 buzz: 1 fizzbuzz: 0 lucky: 0 integer: 1");
        verify(aggregator).apply(expectedTokens);

    }
}
