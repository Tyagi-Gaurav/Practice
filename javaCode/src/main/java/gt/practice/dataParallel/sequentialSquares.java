package gt.practice.dataParallel;

import java.util.stream.IntStream;

public class sequentialSquares {
    public static void main(String[] args) {
        int sum = IntStream.range(1, 10)
                .parallel()
                .map(x -> x * x)
                .sum();
        System.out.println(sum);
    }
}
