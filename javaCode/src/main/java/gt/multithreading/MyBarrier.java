package gt.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyBarrier {
    private final Lock barrierLock = new ReentrantLock();
    private final Condition condition = barrierLock.newCondition();
    private final int numberOfThreads;
    private int currentCount;

    public MyBarrier(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.currentCount = numberOfThreads;
    }

    public void await() {
        try {
            barrierLock.lock();

            -- currentCount;
            if (currentCount == 0) {
                //Trip Signal
                startNextGeneration();
            } else {
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            barrierLock.unlock();
        }
    }

    private void startNextGeneration() {
        this.currentCount = numberOfThreads;
        condition.signalAll();
    }
}
