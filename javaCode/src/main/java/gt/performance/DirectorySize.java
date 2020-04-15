package gt.performance;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class DirectorySize {
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    public static void main(String[] args) {
        /*
        Consumer Producer Example:
        ==========================
        For each file, add size to total
        For each sub-directory, add directory on work queue.
        */
        DirectorySize directorySize = new DirectorySize();
        File input = new File("/Users/gauravt/workspace/sky");

//        long startTime = System.currentTimeMillis();
//        System.out.println("Directory Size for " + input.getAbsolutePath() + " is " + directorySize.sequentialCalculate(input));
//        System.out.println("Total time taken (In ms): " + (System.currentTimeMillis() - startTime));
//
//        startTime = System.currentTimeMillis();
//        ForkJoinPool executorService = new ForkJoinPool(50);
//        System.out.println("Directory Size for " + input.getAbsolutePath() + " is " + directorySize.concurrentCalculate(executorService, input));
//        System.out.println("Total time taken (In ms): " + (System.currentTimeMillis() - startTime));
        directorySize.computeUsingForkJoinPool(input);
    }

    private long concurrentCalculate(ExecutorService executorService, File input) {
        Future<Long> result = executorService.submit(() ->
                concurrentCalculateList(executorService, Collections.singletonList(input)));
        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private long concurrentCalculateList(ExecutorService executorService, List<File> input) {
        long total = 0;

        for (File file : input) {
            if (!file.isDirectory()) return file.length();
            else {
                List<File> subDirectories = new ArrayList<>();
                File[] files = file.listFiles();
                assert files != null;
                for (File childFile : files) {
                    if (childFile.isDirectory())
                        subDirectories.add(childFile);
                    else
                        total += childFile.length();
                }

                Future<Long> result = executorService.submit(() ->
                        concurrentCalculateList(executorService, subDirectories));

                try {
                    total += result.get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return total;
    }

    private long sequentialCalculate(File seed) {
        long total = 0;

        if (!seed.isDirectory()) return seed.length();
        else {
            File[] files = seed.listFiles();
            assert files != null;
            for (File childFile : files) {
                if (childFile.isDirectory())
                    total += sequentialCalculate(childFile);
                else
                    total += childFile.length();
            }
        }

        return total;
    }

    public long computeUsingForkJoinPool(File input) {
        long startTime = System.currentTimeMillis();
        Long result = forkJoinPool.invoke(new FileSizeFinder(input));
        System.out.println("Directory Size for " + input.getAbsolutePath() + " is " + result);
        System.out.println("Total time taken (In ms): " + (System.currentTimeMillis() - startTime));
        return result;
    }

    private static final class FileSizeFinder extends RecursiveTask<Long> {
        private File input;

        public FileSizeFinder(File childFile) {
            this.input = childFile;
        }

        @Override
        public Long compute() {
            long total = 0;

            if (!input.isDirectory()) return input.length();
            else {
                File[] files = input.listFiles();
                assert files != null;
                for (File childFile : files) {
                    if (childFile.isDirectory())
                        total += forkJoinPool.invoke(new FileSizeFinder(childFile));
                    else
                        total += childFile.length();
                }
            }

            return total;
        }
    }
}
