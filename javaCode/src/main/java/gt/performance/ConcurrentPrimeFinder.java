package gt.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentPrimeFinder {
    public static void main(String[] args) {
        ConcurrentPrimeFinder concurrentPrimeFinder = new ConcurrentPrimeFinder();
        long startTime = System.currentTimeMillis();
        System.out.println("Number of primes: " + concurrentPrimeFinder.sequentialListNumberOfPrimesUpto(10000000));
        System.out.println("Total time taken (In ms): " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        System.out.println("Number of primes: " + concurrentPrimeFinder.concurrentListNumberOfPrimesUpto(10000000));
        System.out.println("Total time taken (In ms): " + (System.currentTimeMillis() - startTime));
    }

    /**
        It takes more time to conduct primality test on large numbers.
        Hence, we divide the problem in chunks.
     */
    private int concurrentListNumberOfPrimesUpto(int limit) {
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        List<Callable<Integer>> partitions = new ArrayList<>();

        int numbersPerPartition = limit/poolSize;

        for (int i=0;i < poolSize; ++i) {
            int lower = (i * numbersPerPartition) + 1;
            int higher = (i == poolSize -1) ? limit : lower + numbersPerPartition - 1;
            partitions.add(() -> countPrimesInRange(lower, higher));
        }

        int total = 0;

        try {
            List<Future<Integer>> futures = executorService.invokeAll(partitions);
            for (Future<Integer> future : futures) {
                total += future.get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdownNow();
        }

        return total;
    }

    private int countPrimesInRange(int lower, int higher) {
        int total = 0;
        for(int i=lower; i <= higher; ++i) {
            if (isPrime(i)) {
                total++;
            }
        }

        return total;
    }

    private int sequentialListNumberOfPrimesUpto(int limit) {
        return countPrimesInRange(1, limit);
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;

        for(int i=2;i <= Math.sqrt(number);++i)
            if (number % i == 0) return false;

        return true;
    }
}
