package gt.performance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadExecutorDemo {
    private static ScheduledExecutorService timer = Executors.newScheduledThreadPool(10,
            r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
    private ScheduledFuture<?> scheduledFuture;

    public ScheduledThreadExecutorDemo() {
        scheduledFuture = timer.scheduleAtFixedRate(this::doSomething, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduledFuture.cancel(false);
        //timer.shutdown(); -- Since all threads are daemon threads, they get terminated when JVM exits. JVM exits
        //after all non-daemon threads terminate.
    }

    private void doSomething() {
        System.out.println("Do Something");
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadExecutorDemo scheduledThreadExecutorDemo = new ScheduledThreadExecutorDemo();
        Thread.sleep(5000);
        scheduledThreadExecutorDemo.stop();

    }
}
