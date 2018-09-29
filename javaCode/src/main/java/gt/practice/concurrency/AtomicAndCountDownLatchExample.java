package gt.practice.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicAndCountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        AtomicInteger sharedState = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable producer = () -> {
            int randomInteger = ThreadLocalRandom.current().nextInt();
            sharedState.set(randomInteger);
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                int randomInteger = sharedState.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executorService.execute(producer);
        executorService.execute(consumer);

        executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        System.out.println(countDownLatch.getCount() == 0);
    }
}
