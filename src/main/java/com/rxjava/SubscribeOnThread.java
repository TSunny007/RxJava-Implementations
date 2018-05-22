package com.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;

public class SubscribeOnThread {

    public static void main(String[] args) throws Exception {

        // Create and sync on an object that we will use to make sure we don't
        // hit the System.exit(0) call before out threads have had a chance to complete

        Object waitMonitor = new Object();
        synchronized(waitMonitor){
            System.out.println("-----------------");
            System.out.println("Creating an Observable that does specify a subscribeOn, but not an observeOn Schedule");
            System.out.println("driving thread: " + Thread.currentThread().getName());
            System.out.println("-----------------");

            // get the list of longs
            List<Long> fibList = SimpleObservable.generateFibonacci(10);

            // Wrap the list in an Observable
            Observable<Long> observable = Observable.from(fibList);

            // Subscribe...
            observable
                    // make sure that the subscriber driver code executes on a new thread...
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(
                            // onNext function
                            i -> {
                                System.out.println("onNext thread entr: " + Thread.currentThread().getName());
                                System.out.println(i);
                                System.out.println("onNext thread exit: " + Thread.currentThread().getName());
                            },
                            // onError function
                            t -> t.printStackTrace(),
                            // onCompleted function
                            // onCompleted function
                            () -> {
                                System.out.println("onCompleted()");
                                synchronized (waitMonitor) {
                                    waitMonitor.notify();
                                }
                            }
                    );
            waitMonitor.wait();
        }
        System.exit(0);
    }
}
