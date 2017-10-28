package gt.practice.java8.algos;

import java.util.IntSummaryStatistics;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.summarizingInt;

public class AveragingList {
    public static void main(String[] args) {
        Double collect = Stream.of(1, 2, 3, 4, 5)
                .collect(averagingInt(x -> x));
        System.out.println(collect);

        IntSummaryStatistics collect1 = Stream.of(1, 2, 3, 4, 5)
                .collect(summarizingInt(Integer.class::cast));
        System.out.println(collect1);
    }
}
