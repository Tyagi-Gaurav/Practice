package gt.practice.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferExample {
    final Lock lock = new ReentrantLock();
    private Condition untilSpaceAvailable = lock.newCondition();
    private Condition untilItemAdded = lock.newCondition();

    final Object[] items = new Object[100];
    private int itemCount = 0, putIndex = 0, getIndex = 0;

    public void put(Object input) throws InterruptedException {
        try {
            lock.lock();
            if (itemCount == items.length)
                untilSpaceAvailable.wait();

            items[putIndex++] = input;
            if (putIndex == items.length) putIndex = 0;
            itemCount++;
            untilItemAdded.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        try {
            lock.lock();

            if (itemCount == 0)
                untilItemAdded.wait();

            Object x = items[getIndex++];
            if (getIndex == items.length) getIndex = 0;
            --itemCount;
            untilSpaceAvailable.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
