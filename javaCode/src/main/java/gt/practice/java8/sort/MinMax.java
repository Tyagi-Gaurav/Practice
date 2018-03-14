package gt.practice.java8.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class MinMax {
    public static void main(String[] args) {
        List<Integer> integers = asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> min = integers.stream().min(Comparator.comparingInt(x -> x));
        System.out.println(min.orElseThrow(RuntimeException::new));
    }
}
