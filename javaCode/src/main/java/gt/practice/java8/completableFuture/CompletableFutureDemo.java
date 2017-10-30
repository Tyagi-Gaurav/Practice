package gt.practice.java8.completableFuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    private static final ExpensiveTask expensiveTask = new ExpensiveTask();

    public static void main(String[] args) {
        demoCompetableFutureWithComposeNoAsync();
    }

    private static void demoCompetableFutureWithComposeNoAsync() {
        CompletableFuture<Integer> webService1 =
                executeWebService(500, 0)
                .thenCompose(x -> executeWebService(5000 , x)); //x is the result of 1st webservice.

        //Call 3rd webService and combine it with result of 1 & 2 above i.e. webservice1
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
