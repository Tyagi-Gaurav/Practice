package gt.practice.concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronousQueueExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue();
        Runnable producer = () -> {
            Integer randomInteger = ThreadLocalRandom.current().nextInt();
            try {
                synchronousQueue.put(randomInteger);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                Integer randomInteger = synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executorService.execute(producer);
        executorService.execute(consumer);

        executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executorService.shutdown();
        System.out.println(synchronousQueue.size()== 0);
    }
}
