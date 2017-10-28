package gt.practice.java8.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureDemo {
    private static final ExpensiveTask expensiveTask = new ExpensiveTask();

    public static void main(String[] args) {
        CompletableFuture<Integer> webService1 =
                executeWebService(500, 0)
                .thenCompose(x -> executeWebService(5000 , x));

        //Call 3rd webService with result of 1 & 2 above
        System.out.println(
                executeWebService(500, 0)
                .thenCompose(x -> executeWebService(400, x))
                .thenCombine(webService1, (y, z) -> {
                    /*
                     y is the result from futures in this chain and
                     z is the result from webservice1 chain.
                    */

                    System.out.println("Final Result " + y + ", " + z);
                    return y + z;
                }).join());
    }

    private static CompletableFuture<Integer> executeWebService(int time, int previousParameter) {
        CompletableFuture<Integer> objectCompletableFuture = new CompletableFuture<>();
        objectCompletableFuture.complete(expensiveTask.slowRunningTask(time + previousParameter));
        return objectCompletableFuture;
    }
}
