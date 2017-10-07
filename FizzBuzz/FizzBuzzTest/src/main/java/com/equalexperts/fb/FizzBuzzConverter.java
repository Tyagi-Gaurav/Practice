package com.equalexperts.fb;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

public class FizzBuzzConverter implements IntFunction<String> {
    private BiFunction<Integer, Integer, Boolean> isMultipleOf = (x, y) -> x % y == 0;

    @Override
    public String apply(int num) {
        String result = String.valueOf(num);

        if (numberContainsDigit(num ,3))
            result = "lucky";
        else if (isMultipleOf.apply(num, 15))
            result = "fizzbuzz";
        else if (isMultipleOf.apply(num, 5))
            result = "buzz";
        else if (isMultipleOf.apply(num, 3))
            result = "fizz";

        return result;
    }

    private boolean numberContainsDigit(int num, int digit) {
        return String.valueOf(num).contains(String.valueOf(digit));
    }
}
