package gt.practice.lambda;

import java.util.stream.Stream;

public class Reduce {
    public static void main(String[] args) {
        Integer sum = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, (num, acc) -> num + acc);
        System.out.println(sum);
    }
}
