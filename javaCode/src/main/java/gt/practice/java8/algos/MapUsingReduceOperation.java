package gt.practice.java8.algos;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class MapUsingReduceOperation {
    public static void main(String[] args) {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 6);
        Function<Integer, Integer> mapFunction = x -> x + 1;

        ArrayList<Integer> reduce = integerStream
                .reduce(new ArrayList<>(),
                        (ArrayList<Integer> x, Integer y) -> {
                            x.add(mapFunction.apply(y));
                            return x;
                        },
                        (x, y) -> {
                            x.addAll(y);
                            return x;
                        });

        System.out.println(reduce);
    }
}
