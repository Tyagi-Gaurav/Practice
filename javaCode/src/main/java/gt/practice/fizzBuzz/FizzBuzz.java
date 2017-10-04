package gt.practice.fizzBuzz;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FizzBuzz {

    private BiFunction<Integer, Integer, Boolean> isMultipleOf = (x,y) -> x % y == 0;

    public String getFizzBuzzForRange(int lower, int higher) {
        List<String> fbTokens = createListOfFizzBuzzTokensUsing(lower, higher);
        return formattedOutputUsing(fbTokens);
    }

    private String formattedOutputUsing(List<String> fbTokens) {
        return String.join(" ", fbTokens);
    }

    private List<String> createListOfFizzBuzzTokensUsing(int lower, int higher) {
        return IntStream.rangeClosed(lower, higher)
            .mapToObj(x -> checkFizzBuzz(x))
            .collect(toList());
    }

    private String checkFizzBuzz(int num) {
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
