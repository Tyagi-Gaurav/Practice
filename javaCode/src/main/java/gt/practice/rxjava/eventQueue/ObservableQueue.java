package gt.practice.rxjava.eventQueue;

import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ObservableQueue<T> extends AbstractQueue<T> {
    private Queue<T> internalQueue;
    private PublishSubject<T> internalObservableSubject;

    public ObservableQueue() {
        internalQueue = new ArrayBlockingQueue<T>(100);
        internalObservableSubject = PublishSubject.create();
    }

    @Override
    public Iterator<T> iterator() {
        return internalQueue.iterator();
    }

    @Override
    public int size() {
        return internalQueue.size();
    }

    public Observable<T> observable() {
        return internalObservableSubject;
    }


    @Override
    public boolean offer(T t) {
        boolean offer = internalQueue.offer(t);
        if (offer) {
            internalObservableSubject.onNext(t);
        }

        return offer;
    }

    @Override
    public T poll() {
        return internalQueue.poll();
    }

    @Override
    public T peek() {
        return internalQueue.peek();
    }
}
