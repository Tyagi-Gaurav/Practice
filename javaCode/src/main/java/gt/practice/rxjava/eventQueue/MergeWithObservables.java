package gt.practice.rxjava.eventQueue;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

public class MergeWithObservables {
    public static void main(String[] args) {
        ObservableQueue<Integer> observableQueue1 = new ObservableQueue<>();
        ObservableQueue<Integer> observableQueue2 = new ObservableQueue<>();

        Observable<Integer> zippedObservable = Observable.zip(observableQueue1.observable(),
                observableQueue2.observable(),
                new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                });

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println(throwable);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Next: " + integer);
            }
        };

        zippedObservable.subscribe(subscriber);

        for (int i = 0; i < 5; i++) {
            observableQueue1.offer(i);
        }

        for (int i = 5; i < 10; i++) {
            observableQueue2.offer(i);
        }
    }
}
