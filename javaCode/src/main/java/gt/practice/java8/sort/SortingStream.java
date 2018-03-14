package gt.practice.java8.sort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SortingStream {
    public static void main(String[] args) {
        List<String> strings = asList("This is a list of Strings".split(" "));

        sortUsingStreams(strings);
        sortUsingLength(strings);
    }

    private static void sortUsingLength(List<String> strings) {
        List<String> collect = strings.stream().sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void sortUsingStreams(List<String> strings) {
        List<String> collect = strings.stream().sorted().collect(Collectors.toList());
        System.out.println(collect);
    }
}
