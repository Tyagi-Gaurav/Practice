package gt.practice.methodReferences;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    Map<Integer, Long> cache = new HashMap<>();

    public Fibonacci() {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.calculate(4));
    }

    private Long calculate(int i) {
        return cache.computeIfAbsent(i, n -> calculate(n-1) + calculate(n - 2));
    }
}
