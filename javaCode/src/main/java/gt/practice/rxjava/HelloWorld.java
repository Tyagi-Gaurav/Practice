package gt.practice.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class HelloWorld {
    public static void main(String[] args) {
        String[] names= {"ben", "john", "edward", "raju"};
        HelloWorld.hello(names);
    }

    public static void hello(String ...names) {
        Observable.from(names)
                  //.filter(x -> x.length() < 20)
                  //.map(x -> x.substring(0, 2))
                  .subscribe(new Action1<String>() {
                      @Override
                      public void call(String name) {
                          System.out.println("Hello : " + name);
                      }
                  });
    }

    public static void helloWithCreate(String... names) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        });
    }
}
