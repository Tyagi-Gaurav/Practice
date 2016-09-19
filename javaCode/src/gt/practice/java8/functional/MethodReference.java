package gt.practice.java8.functional;


import java.util.function.Function;

public class MethodReference {
    private static double nextDown(double aDouble) {
        return Math.nextDown(aDouble);
    }

    public static double square(double num) {
        return Math.pow(num , 2);
    }

    public static void main(String[] args) {
        Function<Double, Double> square = MethodReference::square;
        Function<Double, Double> twoFunctions = square.andThen(MethodReference::nextDown);
        double ans = twoFunctions.apply(2.0);
        System.out.println(ans);
    }
}