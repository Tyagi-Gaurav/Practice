package gt.practice.java8.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CombiningCompletableFutures {
    public static void main(String[] args) {
        CombiningCompletableFutures combiningCompletableFutures = new CombiningCompletableFutures();
        combiningCompletableFutures.doIt();
    }

    private void doIt() {
        CompletableFuture.supplyAsync(this::sleepThenReturnString)
                .thenApply(Integer::parseInt)
                .thenApply(x -> x * 2)
                .thenAccept(System.out::println)
                .join();

    }

    private String sleepThenReturnString() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "14";
    }
}
