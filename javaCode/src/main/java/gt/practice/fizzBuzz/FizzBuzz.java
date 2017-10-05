package gt.practice.fizzBuzz;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class FizzBuzz {

    private Converter<Integer, String> converter;

    public FizzBuzz(Converter<Integer, String> converter) {
        this.converter = converter;
    }

    public FizzBuzz() {

    }

    public String getFizzBuzzForRange(int lower, int higher) {
        List<String> fbTokens =

        (lower, higher);
        return formattedOutputUsing(fbTokens);
    }

    private String formattedOutputUsing(List<String> fbTokens) {
        return String.join(" ", fbTokens);
    }


}
