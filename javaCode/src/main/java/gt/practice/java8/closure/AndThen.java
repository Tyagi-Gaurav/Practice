package gt.practice.java8.closure;

import java.util.function.Function;

public class AndThen {
    public static void main(String[] args) {
        Function<Integer, Integer> add2 = x -> x + 2;
        Function<Integer, Integer> mult3 = x -> x * 3;

        Function<Integer, Integer> add2Mult3_1 = add2.compose(mult3);
        Function<Integer, Integer> add2Mult3_2 = add2.andThen(mult3);

        System.out.println(add2Mult3_1.apply(4));
        System.out.println(add2Mult3_2.apply(4));

    }
}
