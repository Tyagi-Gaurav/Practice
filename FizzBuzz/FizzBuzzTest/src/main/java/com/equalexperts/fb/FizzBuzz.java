package com.equalexperts.fb;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuzz {
    private IntFunction<String> fizzBuzzConverter;
    private Function<List<String>, String> aggregator;

    public FizzBuzz(IntFunction<String> fizzBuzzConverter) {
        this(fizzBuzzConverter, x -> "");
    }

    public FizzBuzz(IntFunction<String> fizzBuzzConverter, Function<List<String>, String> aggregator) {
        this.fizzBuzzConverter = fizzBuzzConverter;
        this.aggregator = aggregator;
    }

    public String getFizzBuzzForRange(int low, int high) {
        List<String> fbTokens = IntStream.rangeClosed(low, high)
                .mapToObj(fizzBuzzConverter)
                .collect(Collectors.toList());

        return formatResponse(fbTokens);
    }

    private String formatResponse(List<String> fbTokens) {
        return String.join(" ", fbTokens)
                + aggregator.apply(fbTokens);
    }
}
