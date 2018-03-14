package gt.practice.java8.partition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Partitioning {
    public static void main(String[] args) {
        List<String> strings = asList("This is a long list of strings to use as a demo".split(" "));

        partitionBasedOnAFunction(strings);
        partitionBasedOnAFunctionAndPostProcess(strings);
        groupingBasedOnFunction(strings);
    }

    private static void partitionBasedOnAFunctionAndPostProcess(List<String> strings) {
        Map<Boolean, Long> collect = strings.stream().collect(Collectors.partitioningBy(x -> x.length() % 2 == 0, Collectors.counting()));
        System.out.println(collect);
    }

    private static void groupingBasedOnFunction(List<String> strings) {
        Map<Integer, List<String>> collect = strings.stream().collect(Collectors.groupingBy(String::length));
        System.out.println(collect);
    }

    private static void partitionBasedOnAFunction(List<String> strings) {
        Map<Boolean, List<String>> collect = strings.stream().collect(Collectors.partitioningBy(x -> x.length() % 2 == 0));
        System.out.println(collect);
    }
}
