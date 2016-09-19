package gt.practice.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.Arrays;
import java.util.List;

public class ObservableWithCreate {
    public static void main(String[] args) {
        Observable observable =
            Observable.create(new Observable.OnSubscribe<List<Integer>>() {
                List<Integer> source = Arrays.asList(10,20,30);
                @Override
                public void call(Subscriber<? super List<Integer>> subscriber) {
                    System.out.println("Subscriber:onCall");
                    subscriber.onNext(source);
                }
            });

        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("Subscribe:Called: " + o);
            }
        });

        observable.publish();
    }
}
