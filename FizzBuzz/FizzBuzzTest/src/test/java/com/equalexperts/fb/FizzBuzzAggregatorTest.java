package com.equalexperts.fb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FizzBuzzAggregatorTest {
    private Function<List<String>, String> aggregator =
            new FizzBuzzAggregator();

    @Test
    @DisplayName("should group all terms together and return stats")
    void shouldAggregate() {
        List<String> inputTokens = Arrays.asList("lucky","fizz","1","2","5","buzz","fizzbuzz","fizz");
        String expectedOutput = " fizz: 2 buzz: 1 fizzbuzz: 1 lucky: 1 integer: 3";

        assertThat(aggregator.apply(inputTokens)).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("should return all stats when no tokens are provided")
    void emptyMapWhenNoTokensProvided() {
        assertThat(aggregator.apply(Collections.emptyList()))
                .isEqualTo(" fizz: 0 buzz: 0 fizzbuzz: 0 lucky: 0 integer: 0");
    }
}