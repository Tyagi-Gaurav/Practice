package gt.practice.java8.algos;

import java.util.HashMap;
import java.util.Map;

public class FibonacciWithMap {
    private Map<Integer, Integer> fibSequence = new HashMap<>();

    public FibonacciWithMap() {
        fibSequence.put(1, 0);
        fibSequence.put(2, 1);
    }

    public static void main(String[] args) {
        FibonacciWithMap fibonacciWithMap = new FibonacciWithMap();
        System.out.println(fibonacciWithMap.compute(4));
        System.out.println(fibonacciWithMap.compute(7));
        System.out.println(fibonacciWithMap.compute(8));
        System.out.println(fibonacciWithMap.compute(20));
    }

    private Integer compute(int fibTerm) {
        return fibSequence.computeIfAbsent(fibTerm, this::fibonacci);
    }

    private Integer fibonacci(int number) {
        return compute(number - 1) + compute(number - 2);
    }
}
