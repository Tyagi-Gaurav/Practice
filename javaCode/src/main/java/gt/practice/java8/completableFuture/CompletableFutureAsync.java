package gt.practice.java8.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAsync {
    private static final ExpensiveTask expensiveTask = new ExpensiveTask();

    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture1 =
                executeWebService(100, 0)
                .thenCompose(s -> executeWebService(200, s));

        CompletableFuture<String> completableFuture2 =
                executeWebService(100, 0)
                .thenCompose(s -> executeWebService(200, s))
                .thenApply(x -> x + "ToString");

        System.out.println(completableFuture1.join());
        System.out.println(completableFuture2.join());
    }

    private static CompletableFuture<Integer> executeWebService(int time, int previousParameter) {
        return CompletableFuture.supplyAsync(() -> expensiveTask.slowRunningTask(time + previousParameter));
    }
}
