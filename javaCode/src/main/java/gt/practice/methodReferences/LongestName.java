package gt.practice.methodReferences;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class LongestName {
    public static void main(String[] args) {
        longestUsingReduce();
        longestUsingCollector();

        Stream<String> names = Stream.of("John", "Paul", "George", "John",
                "Paul", "John");
        Map<String, Long> collect = names
                .collect(groupingBy(name -> name, counting()));
        System.out.println(collect);
    }

    private static void longestUsingCollector() {
        Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");

        Optional<String> result = names.collect(maxBy((x, y) -> {
            if (x.length() > y.length()) {
                return 1;
            } else if (x.length() < y.length()) {
                return -1;
            } else {
                return 0;
            }
        }));

        System.out.println(result.orElse("No String found"));
    }

    private static void longestUsingReduce() {
        Stream<String> names = Stream.of("John Lennon", "Paul McCartney",
                "George Harrison", "Ringo Starr", "Pete Best", "Stuart Sutcliffe");

        String result = names.reduce("",
                (String x, String acc) -> {
                    if (x.length() > acc.length()) {
                        return x;
                    } else {
                        return acc;
                    }
                });

        System.out.println(result);
    }
}
