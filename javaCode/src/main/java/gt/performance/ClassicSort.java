package gt.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 1. This code does not consider JVM warming.
 * 2. Add JVM options  `-Xms2048m -Xmx2048m -XX:+PrintCompilation`
 * 3. Add
 */
public class ClassicSort {
    private static final int N = 1_000;
    private static final int I = 150_000;
    private static final List<Integer> testData = new ArrayList<>();

    public static void main(String[] args) {
        Random randomGenerator = new Random();
        for (int i = 0; i < N; i++) {
            testData.add(randomGenerator.nextInt(Integer.MAX_VALUE));
        }

        System.out.println("Testing Sort Algorithm");

        double startTime = System.nanoTime();

        for (int i = 0; i < I; i++) {
            List<Integer> copy = new ArrayList<>(testData);
            Collections.sort(copy);
        }

        double endTime = System.nanoTime();
        double timePerOperation = ((endTime - startTime) / (1_000_000_000L * I));
        System.out.println("Result: " + (1 / timePerOperation) + " op/s");
    }
}
