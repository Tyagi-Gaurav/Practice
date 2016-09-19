package gt.practice.java8.general;

import java.util.stream.Stream;

public class Reduce {
    public static void main(String[] args) {
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(0, (acc, element) -> acc + element);
        System.out.println(reduce);
    }
}
