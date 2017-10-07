package com.equalexperts.fb;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class FizzBuzzAggregator implements Function<List<String>, String> {
    @Override
    public String apply(List<String> tokens) {
        return createAggregatedResponse(tokens
                .stream()
                .map(this::determineBucket)
                .collect(
                groupingBy(Function.identity(), summingInt(e -> 1))));
    }

    private String determineBucket(String x) {
        if (isNumber(x))
            return "integer";
        else
            return x;
    }

    private boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private String createAggregatedResponse(Map<String, Integer> aggregatorMap) {
        StringBuilder builder = new StringBuilder("");
        builder.append(" fizz: " + aggregatorMap.getOrDefault("fizz", 0));
        builder.append(" buzz: " + aggregatorMap.getOrDefault("buzz", 0));
        builder.append(" fizzbuzz: " + aggregatorMap.getOrDefault("fizzbuzz", 0));
        builder.append(" lucky: " + aggregatorMap.getOrDefault("lucky", 0));
        builder.append(" integer: " + aggregatorMap.getOrDefault("integer", 0));
        return builder.toString();
    }
}
