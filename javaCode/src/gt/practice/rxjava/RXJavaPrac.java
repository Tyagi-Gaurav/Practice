package gt.practice.rxjava;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class RXJavaPrac {
    List<Integer> sourceList = new ArrayList<>();
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new RXJavaPrac().staticSubscriptionFromSharedFuture();
    }

    private void staticSubscriptionFromFuture() throws ExecutionException, InterruptedException {
        Observable.from(listFuture)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        for (Integer num : integers) {
                            System.out.println(num);
                        }
                    }
                });

    }

    private void staticSubscriptionFromSharedFuture() throws ExecutionException, InterruptedException {
        System.out.println("Observable Thread: " + Thread.currentThread().getId());
        Observable.from(sourceList)
            .subscribeOn(Schedulers.from(executor))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer num) {
                    System.out.println("Subscriber Thread: " + Thread.currentThread().getId());
                    System.out.println(num);
                }
            });
    }

    private void staticSubscriptionFromList() throws ExecutionException, InterruptedException {
        Observable.from(sourceList)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer nextNumber) {
                        System.out.println("Got event: " + nextNumber);
                    }
                });

        sharedListFuture.get();

    }

    Future<List<Integer>> listFuture = executor.submit(new Callable<List<Integer>>() {
        Random random = new Random();

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> sourceList = new ArrayList<Integer>();

            for (int i=0; i < 10;++i) {
                int randomNumber = random.nextInt();
                System.out.println("Publishing Number: " + randomNumber);
                sourceList.add(randomNumber);
                Thread.sleep(500);
            }

            return sourceList;
        }
    });

    Future<Integer> sharedListFuture = executor.submit(new Callable<Integer>() {
        Random random = new Random();

        @Override
        public Integer call() throws Exception {
            System.out.println("Shared List Future Thread: " + Thread.currentThread().getId());
            for (int i=0; i < 10;++i) {
                int randomNumber = random.nextInt();
                System.out.println("Publishing Number: " + randomNumber);
                sourceList.add(randomNumber);
                Thread.sleep(500);
            }

            return 0;
        }
    });
}
