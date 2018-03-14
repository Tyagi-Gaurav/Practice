package gt.practice.java8.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;

public class SuccessiveSorting {
    public static void main(String[] args) {
        List<Golfer> golfers = asList(
                new Golfer("J", "N", 68),
                new Golfer("Ti", "Wo", 70),
                new Golfer("To", "Wa", 70),
                new Golfer("Ty", "We", 68),
                new Golfer("B", "Wa", 70)
        );

        sortbyScoreThenLastNameAndThenFirst(golfers)
                .forEach(System.out::println);
        System.out.println(nameScoreMap(golfers));
    }

    private static Map<String, Integer> nameScoreMap(List<Golfer> golfers) {
        return golfers.stream()
                .collect(Collectors.toMap(Golfer::getFirst, Golfer::getScore));
    }

    private static List<Golfer> sortbyScoreThenLastNameAndThenFirst(List<Golfer> golfers) {
        return golfers.stream()
                .sorted(comparingInt(Golfer::getScore)
                .thenComparing(Golfer::getLast)
                .thenComparing(Golfer::getFirst))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Getter
    @ToString
    static class Golfer {
        private String first;
        private String last;
        private int score;
    }
}
