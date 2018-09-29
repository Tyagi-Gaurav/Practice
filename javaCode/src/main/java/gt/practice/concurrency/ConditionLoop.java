package gt.practice.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConditionLoop {
    private boolean condition;

    public static void main(String[] args) throws InterruptedException {
        ConditionLoop loop = new ConditionLoop();

        Runnable r1 = () -> {
            try {
                loop.waitForCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable r2 = loop::satisfyCondition;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(r1);
        Thread.sleep(1000);
        executorService.submit(r2);
        executorService.shutdown();
        executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private synchronized void waitForCondition() throws InterruptedException {
        while (!condition) {
            System.out.println("Getting ready to wait for condition..");
            wait();
            System.out.println("Waiting finish with condition value: " + condition);
        }
    }

    private synchronized void satisfyCondition() {
        condition = true;
        notifyAll();
    }


}
