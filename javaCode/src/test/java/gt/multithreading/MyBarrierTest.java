package gt.multithreading;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MyBarrierTest {
    @Test
    void shouldBlockAllThreadsOnExecution() throws InterruptedException {
        MyBarrier cyclicBarrier = new MyBarrier(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable runnable = () -> {
            try {
                System.out.println("Thread Number: " + Thread.currentThread().getId());
                Thread.sleep(300 * Thread.currentThread().getId());
                System.out.println("Hello from Thread Number: " + Thread.currentThread().getId());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Bye from Thread Number: " + Thread.currentThread().getId());
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.submit(runnable);

        Thread.sleep(5000);
    }
}