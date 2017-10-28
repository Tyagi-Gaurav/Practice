package gt.practice.java8.completableFuture;

import java.util.Random;

public class ExpensiveTask {
    private Random random = new Random();

    public Integer slowRunningTask(long num) {
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int result = random.nextInt(5000);
        System.out.println("Slept for " + num + " and returning result " + result);
        return result;
    }
}
