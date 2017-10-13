package gt.practice.java8.algos;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SplitListOperation {
    public static void main(String[] args) {
        int index = 4;
        List<Integer> collect = Stream.of(5, 6, 7, 1, 2, 3, 8, 3, 9, 10).collect(Collectors.toList());

        IntPredicate lessThan = i -> i < index;
        IntPredicate greaterThan = i -> i >= index;
        List<Integer> listFrst = getList(collect, lessThan);
        List<Integer> listSnd = getList(collect, greaterThan);

        System.out.println(listFrst);
        System.out.println(listSnd);
    }

    private static List<Integer> getList(List<Integer> collect, IntPredicate lessThan) {
        return IntStream.range(0, collect.size())
                .filter(lessThan)
                .mapToObj(x -> collect.get(x))
                .collect(Collectors.toList());
    }
}
