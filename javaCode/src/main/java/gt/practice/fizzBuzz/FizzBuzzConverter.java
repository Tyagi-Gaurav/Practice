package gt.practice.fizzBuzz;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FizzBuzzConverter implements BiFunction<Integer, Integer, List<String>> {
    private BiFunction<Integer, Integer, Boolean> isMultipleOf = (x, y) -> x % y == 0;
    private String convert(Integer num) {
        String result = String.valueOf(num);

//        if (numberContainsDigit(num ,3))
//            result = "lucky";
//        else
        if (isMultipleOf.apply(num, 15))
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

    @Override
    public List<String> apply(Integer lower, Integer higher) {
        return IntStream.rangeClosed(lower, higher)
                .mapToObj(x -> this.convert(x))
                .collect(toList());
    }
}
