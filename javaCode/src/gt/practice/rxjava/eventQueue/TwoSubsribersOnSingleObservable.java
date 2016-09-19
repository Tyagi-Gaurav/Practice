package gt.practice.rxjava.eventQueue;

import rx.Subscriber;

public class TwoSubsribersOnSingleObservable {
    public static void main(String[] args) {
        ObservableQueue observableQueue = new ObservableQueue();
        observableQueue.observable().subscribe(aSubscriber());
        observableQueue.observable().subscribe(aSubscriber());

        for (int i = 0; i < 5; i++) {
            observableQueue.add(i);
        }
    }

    private static Subscriber aSubscriber() {
        return new Subscriber() {
            @Override
            public void onCompleted() {
                System.out.println("On Complete");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Throwable " + throwable);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("Object " + o);
            }
        };
    }
}
