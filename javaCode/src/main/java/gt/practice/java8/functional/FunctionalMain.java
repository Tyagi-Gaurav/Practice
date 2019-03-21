package gt.practice.java8.functional;

import java.util.function.*;

public class FunctionalMain {
    public static void main(String[] args) {
        BinaryOperator<Long> addLong = (x,y) -> x + y;
        System.out.println(addLong.apply(3L, 4L));

        Predicate<Integer>  p = x -> x > 5;
        IntPredicate ip = x -> x > 5;

        System.out.println(check(p));
        System.out.println(check(ip));

        int x = 4;

        Supplier<Integer> function = () -> x + 1;

        System.out.println(x);
        System.out.println(function.get());
        System.out.println(function.get());
    }

    static boolean check(Predicate<Integer> p) {
        return p.test(6);
    }

    static boolean check(IntPredicate p) {
        return p.test(6);
    }
}
