package gt.practice.java8.WaitUsingLatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WaitLatch {
    public void init(CountDownLatch countDownLatch) {
        try {
            while (countDownLatch.await(5, TimeUnit.SECONDS)) {
                System.out.println("Begin new initialization with " + countDownLatch.getCount());
                break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> {
                    WaitLatch waitLatch = new WaitLatch();
                    waitLatch.init(countDownLatch);
                    return 0;
                }),
                CompletableFuture.supplyAsync(() -> {
                    ServiceToWaitFor serviceToWaitFor = new ServiceToWaitFor(countDownLatch);
                    serviceToWaitFor.init();
                    return 0;
                }));

        voidCompletableFuture.join();
    }
}

class ServiceToWaitFor {
    private CountDownLatch countDownLatch;

    ServiceToWaitFor(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void init() {
        try {
            System.out.println("Initializing Service");
            Thread.sleep(7000);
            System.out.println("Service Initialized");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

