package gt.practice.java8.collectors;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class AggregatingStrings {
    public static void main(String[] args) {
        Double collect = Stream.of(1, 2, 3, 4, 5, 6, 7)
                .collect(averagingInt(Integer::valueOf));
        System.out.println(collect);

        Stream<Integer> boxed = IntStream.range(1, 100).boxed();

        Map<Boolean, List<Integer>> collect1 = boxed.collect(partitioningBy(x -> x % 2 == 0));
        System.out.println(collect1);

        String test = "To be or not to be";
        System.out.println(asList(test.split(" "))
                .stream()
                .collect(joining(", ", "[", "]")));

    }
}
