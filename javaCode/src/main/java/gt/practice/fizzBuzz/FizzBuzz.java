package gt.practice.fizzBuzz;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FizzBuzz {

    public String getFizzBuzzForRange(int lower, int higher) {
        List<String> fbTokens = createListOfFizzBuzzTokensUsing(lower, higher);

        return String.join(" ", fbTokens);
    }

    private List<String> createListOfFizzBuzzTokensUsing(int lower, int higher) {
        return IntStream.rangeClosed(lower, higher)
            .mapToObj(x -> checkFizzBuzz(x))
            .collect(toList());
    }

    private String checkFizzBuzz(int num) {
        boolean isMultipleOfFifteen = num % 15 == 0;
        boolean isMultipleOfThree = num % 3 == 0;
        boolean isMultipleOfFive = num % 5 == 0;

        String result = String.valueOf(num);

        if (isMultipleOfFifteen)
            result = "fizzbuzz";
        else if (isMultipleOfFive)
            result = "buzz";
        else if (isMultipleOfThree)
            result = "fizz";

        return result;
    }
}
