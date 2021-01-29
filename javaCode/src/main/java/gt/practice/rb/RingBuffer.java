package gt.practice.rb;

import java.lang.reflect.Array;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RingBuffer<T> {
    private T[] data;
    private int start = 0, end = 0;
    private int writeCount = 0;
    private int readCount = 0;
    volatile boolean keepRunning = true;

    public RingBuffer(Class<T> clazz, int length) {
        data = (T[]) Array.newInstance(clazz, length);
    }

    public boolean isFull() {
        return (end + 1) % data.length == start;
    }

    public boolean isEmpty() {
        return start == end;
    }

    public void write(T item) {
        writeCount++;
        if ((end + 1) % data.length != start) {
            data[end] = item;
            end = (end + 1) % data.length;
        }
    }

    public T read() {
        readCount++;
        if (start != end) {
            T item = data[start];
            start = (start + 1) % data.length;
            return item;
        }

        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        RingBuffer<String> ringBuffer = new RingBuffer<>(String.class, 10);
        Lock ringBufferLock = new ReentrantLock();
        Condition spaceAvailable = ringBufferLock.newCondition();
        Condition contentAvailable = ringBufferLock.newCondition();

        Runnable producer = () -> {
            while (ringBuffer.keepRunning) {
                ringBufferLock.lock();
                try {
                    while (ringBuffer.isFull()) {
                        spaceAvailable.await();
                    }
                    String item = UUID.randomUUID().toString();
                    ringBuffer.write(item);
                    System.out.println("Producer Wrote: " + item);
                    contentAvailable.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ringBufferLock.unlock();
                }
            }
        };

        Runnable consumer = () -> {
            while (ringBuffer.keepRunning) {
                ringBufferLock.lock();
                try {
                    while (ringBuffer.isEmpty()) {
                        contentAvailable.await();
                    }
                    String read = ringBuffer.read();
                    System.out.println("Consumer Read: " + read);
                    spaceAvailable.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    ringBufferLock.unlock();
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(producer);
        executorService.submit(consumer);

        Thread.sleep(1000);

        ringBuffer.keepRunning = false;
        executorService.shutdownNow();

        System.out.println("Write Count: " + ringBuffer.writeCount);
        System.out.println("Read Count: " + ringBuffer.readCount);
    }
}
