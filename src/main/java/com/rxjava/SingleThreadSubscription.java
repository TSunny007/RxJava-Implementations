package com.rxjava;

import rx.Observable;

import java.util.List;

public class SingleThreadSubscription {

    public static void main(String[] args) {
        System.out.println("-----------------");
        System.out.println("Creating an Observable that does not specify a subscribeOn or an observeOn Schedule");
        System.out.println("driving thread: " + Thread.currentThread().getName());
        System.out.println("-----------------");

        // get the list of longs
        List<Long> fibList =  SimpleObservable.generateFibonacci(10);

        // Wrap the list in a n Observable
        Observable<Long> observable = Observable.from(fibList);

        // Subscribe...
        observable.subscribe(
                // onNext function
                i ->  {
                    System.out.println("onNext thread entr: " + Thread.currentThread().getName() );
                    System.out.println(i);
                    System.out.println("onNext thread exit: " + Thread.currentThread().getName());
                },
                // onError function
                t -> t.printStackTrace(),
                // onCompleted function
                () -> System.out.println("onCompleted()")
        );

        System.exit(0);
    }
}
