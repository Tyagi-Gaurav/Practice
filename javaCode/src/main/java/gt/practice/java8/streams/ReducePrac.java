package gt.practice.java8.streams;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;

public class ReducePrac {
    public static void main(String[] args) {
        summaryCalculationOnStream();
    }

    private static void summaryCalculationOnStream() {
        String[] wordArray = "This is a string".split(" ");
        int length = Arrays.stream(wordArray)
                .mapToInt(String::length)
                .sum();
        OptionalDouble average = Arrays.stream(wordArray)
                .mapToInt(String::length)
                .average();
        IntSummaryStatistics intSummaryStatistics = Arrays.stream(wordArray)
                .mapToInt(String::length)
                .summaryStatistics();
        System.out.println("Length of String is " + length + " characters");
        System.out.println("Average length of word is " + average.orElse(0.0) + " characters");
        System.out.println("Count: " + intSummaryStatistics.getCount());
        System.out.println("Average word: " + intSummaryStatistics.getAverage());
        System.out.println("Max word: " + intSummaryStatistics.getMax());
        System.out.println("Min word: " + intSummaryStatistics.getMin());
        System.out.println("Sum of lengths: " + intSummaryStatistics.getSum());
    }
}
