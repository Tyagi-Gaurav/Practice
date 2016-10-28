package ee.test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class FizzBuzzReporter {
    private FizzBuzz fizzBuzz;

    public FizzBuzzReporter(FizzBuzz fizzBuzz) {
        this.fizzBuzz = fizzBuzz;
    }

    public void reportFizzBuzzBetween(int minRange, int maxRange) {
        String output = fizzBuzz.printFizzBuzzForNumbersBetween(minRange, maxRange);
        List<String> strings = asList(output.split(" "));
        Map<String, Integer> collect = strings.stream().flatMap(str -> Stream.of(new KeyValue(str, 1)))
                .collect(
                        Collectors.groupingBy(
                                KeyValue::getKey,
                                Collectors.reducing(
                                        0,
                                        KeyValue::getValue,
                                        Integer::sum)));
        System.out.println(output);
        System.out.println("fizz: " + collect.get("fizz"));
        System.out.println("buzz: " + collect.get("buzz"));
        System.out.println("fizzbuzz: " + collect.get("fizzbuzz"));
        System.out.println("lucky: " + collect.get("lucky"));
        System.out.println("integer: " + collect.get("integer"));
    }

    public static void main(String[] args) {
        FizzBuzzReporter fizzBuzzReporter = new FizzBuzzReporter(new FizzBuzz());
        fizzBuzzReporter.reportFizzBuzzBetween(1, 20);
    }
}
