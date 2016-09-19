package gt.practice.rxjava;

import rx.Observable;

public class RxJavaObservable {
    public static void main(String[] args) throws InterruptedException {
        ObservableList<Integer> observableList = new ObservableList<>();

        observableList.getObservable().subscribe(System.out::println);

        observableList.add(1);
        Thread.sleep(1000);
        observableList.add(2);
        Thread.sleep(1000);
        observableList.add(3);
    }
}
