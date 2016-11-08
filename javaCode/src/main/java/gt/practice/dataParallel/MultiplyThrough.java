package gt.practice.dataParallel;

import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class MultiplyThrough {
    public static void main(String[] args) {
        Integer reduce = sequentialMultiply();
        System.out.println(reduce);

        reduce = parallelMultiply();
        System.out.println(reduce);
    }

    private static Integer sequentialMultiply() {
        return Stream.of(1, 2, 3, 4, 5)
                .reduce(5, (x, acc) -> acc * x);
    }

    private static Integer parallelMultiply() {
        Integer reduce = Stream.of(1, 2, 3, 4, 5)
                .parallel()
                .reduce(1, (x, acc) -> acc * x);
        return reduce * 5;
    }
}
