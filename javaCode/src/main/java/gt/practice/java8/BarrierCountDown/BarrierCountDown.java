package gt.practice.java8.BarrierCountDown;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;

public class BarrierCountDown {
    public void init(CyclicBarrier cyclicBarrier) {
        while (cyclicBarrier.getNumberWaiting() < 1) {
            if (cyclicBarrier.getNumberWaiting() == 1) {
                System.out.println("Begin new initialization with " + cyclicBarrier.getNumberWaiting());
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> {
                    BarrierCountDown barrierCountDown = new BarrierCountDown();
                    barrierCountDown.init(cyclicBarrier);
                    return 0;
                }),
                CompletableFuture.supplyAsync(() -> {
                    ServiceToWaitFor serviceToWaitFor = new ServiceToWaitFor(cyclicBarrier);
                    serviceToWaitFor.init();
                    return 0;
                }));

        voidCompletableFuture.join();
    }
}

class ServiceToWaitFor {
    private CyclicBarrier cyclicBarrier;

    ServiceToWaitFor(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void init() {
        try {
            System.out.println("Initializing Service with " + cyclicBarrier.getNumberWaiting());
            Thread.sleep(7000);
            System.out.println("Service Initialized");
            int await = cyclicBarrier.await();
            System.out.println("Await: " + await);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
