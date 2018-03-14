package gt.practice.java8.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinPoolDemo {
    public static void main(String[] args) {
        usingCommonForkJoinPool();
        usingCustomForkJoinPool();
    }

    private static void usingCustomForkJoinPool() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        ForkJoinTask<Long> submit = forkJoinPool.submit(ForkJoinPoolDemo::getParallelSum);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    private static void usingCommonForkJoinPool() {
        System.out.println("Available Processors : " + Runtime.getRuntime().availableProcessors());
        long longSum = getParallelSum();
        System.out.println(longSum);

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        longSum = getParallelSum();
        System.out.println(longSum);
    }

    private static long getParallelSum() {
        return LongStream.rangeClosed(1, 3_000_000)
                .parallel().sum();
    }
}
