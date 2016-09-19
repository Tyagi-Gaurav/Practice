package gt.practice.timer;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class TimerTaskPractice {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //timerDemo();
        //taskEndBeforeStipulatedTime_Approach1();
        taskEndBeforeStipulatedTime_Approach2();
    }

    private static void taskEndBeforeStipulatedTime_Approach2() {
        Callable<String> callableToCompleteUnderOneSecond = () -> {
            Thread.sleep(5000);
            return "Immediate Callable";
        };

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);
        long timeout = 1000;
        Future<String> delayedFuture = executorService.submit(callableToCompleteUnderOneSecond);
        Instant start = Instant.now();
        try {
            delayedFuture.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Interrupted or Execution Exception");
        } catch (TimeoutException e) {
            System.err.println("Timeout Exception: Could not complete task in the stipulated time.");
            System.err.println("Start Time: " + start.getEpochSecond());
            System.err.println("End Time: " + Instant.now().getEpochSecond());
        }
    }

    private static void taskEndBeforeStipulatedTime_Approach1() throws ExecutionException, InterruptedException {
        Callable<String> immediateCallable = () -> {return "Immediate Callable";};
        Callable<String> delayedCallable = () -> {
            Thread.sleep(5000);
            return "Immediate Callable";
        };
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);
        Future<String> immediateFuture = executorService.submit(immediateCallable);
        Future<String> delayedFuture = executorService.submit(delayedCallable);
        executorService.schedule(() -> {delayedFuture.cancel(true);}, 1000, TimeUnit.MILLISECONDS);

        System.out.println(immediateFuture.get());
        try {
            System.out.println(delayedFuture.get());
        } catch(CancellationException e) {
            System.err.println("Delayed Future: Task did not complete in Stipulated time");
        }
    }

    private static void timerDemo() {
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("Hello World");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 50, 1000);
    }

}
