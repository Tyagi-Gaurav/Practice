package gt.practice.rxjava;

import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.List;

public class ObservableList<T> {
    private List<T> data;
    private PublishSubject<T> subject;

    public ObservableList() {
        this.data = new ArrayList<>();
        this.subject = PublishSubject.create();
    }

    public void add(T elem) {
        data.add(elem);
        subject.onNext(elem);
    }

    public Observable<T> getObservable() {
        return subject;
    }
}
