package gt.practice.kattis;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SynchronizingLists {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            int tc = scanner.nextInt();

            List<String> output = new ArrayList<>();

            while (tc != 0) {
                List<Integer> input = new ArrayList<>();

                for (int i = 0; i < 2 * tc; i++) {
                    input.add(scanner.nextInt());
                }

                List<Integer> collect = input.stream().limit(tc)
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList());

                Map<Integer, Integer> collect1 = IntStream.range(0, collect.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> collect.get(i), i -> i));

                List<Integer> collect2 = input.stream().skip(tc)
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList());

                IntStream.range(0, collect2.size())
                        .forEach(i -> output.add(String.valueOf(collect2.get(collect1.get(input.get(i))))));

                output.add(" ");
                tc = scanner.nextInt();
            }

            output.forEach(System.out::println);
        }
    }
}
