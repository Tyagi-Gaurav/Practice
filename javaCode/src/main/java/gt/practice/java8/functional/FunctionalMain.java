package gt.practice.java8.functional;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalMain {
    public static void main(String[] args) {
        BinaryOperator<Long> addLong = (x,y) -> x + y;
        System.out.println(addLong.apply(3L, 4L));

        Predicate<Integer>  p = x -> x > 5;
        IntPredicate ip = x -> x > 5;

        System.out.println(check(p));
        System.out.println(check(ip));
    }

    static boolean check(Predicate<Integer> p) {
        return p.test(6);
    }

    static boolean check(IntPredicate p) {
        return p.test(6);
    }
}
