package gt.practice.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BatchCalls {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        List<String> batchData = new ArrayList<>();
        for (int i = 1; i <= 120; i++) {
            batchData.add(String.valueOf(i));
        }
        List<List<String>> parametersList = new ArrayList<>();

        //Make batch
        int begin = 0;
        int end = 0;
        while (begin < batchData.size() && end < batchData.size()) {
            if (end + 30 < batchData.size()) {
                parametersList.add(batchData.subList(begin, end + 30));
                begin += 30;
                end += 30;
            } else {
                parametersList.add(batchData.subList(begin, batchData.size()));
                break;
            }
        }

        List<CompletableFuture<List<String>>> collect1 = parametersList.stream()
                .map(pl -> CompletableFuture.supplyAsync(() -> stubCall(pl)))
                .collect(Collectors.toList());

        System.out.println("After Step 1");

        CompletableFuture<List<String>> listCompletableFuture1 = CompletableFuture.allOf(collect1.toArray(CompletableFuture[]::new))
                .thenApply(v -> collect1.stream()
                        .map(BatchCalls::getStrings)
                        .collect(Collectors.toList()))
                .thenApply(l -> l.stream()
                        .flatMap(Collection::stream).collect(Collectors.toList()));

        System.out.println("After Step 2");
        List<String> output = listCompletableFuture1.get(500, TimeUnit.MILLISECONDS);

        System.out.println(String.join("\n", output));

        executorService.shutdown();
    }

    private static <T> T getStrings(CompletableFuture<T> listCompletableFuture) {
        try {
            return listCompletableFuture.get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> stubCall(List<String> inputs) {
        String batchProcessId = UUID.randomUUID().toString();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inputs.stream().map(x -> x + "-" + batchProcessId).collect(Collectors.toList());
    }
}
