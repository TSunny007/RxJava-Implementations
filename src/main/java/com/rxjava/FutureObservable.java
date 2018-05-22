package com.rxjava;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.FutureTask;

public class FutureObservable {

    public static void main(String[] args) {
        Observable<List<Long>>  observableFutureList;

        // Create a FutureTask that returns a List<Long>
        FutureTask<List<Long>> future = new FutureTask<>(() -> {
            return SimpleObservable.generateFibonacci(10);
        });

        // construct an observable... note that this only creates the
        // observable wrapper around the future. The future still needs
        // to be executed using it's "run" method, or by scheduling it to
        // execute.
        observableFutureList = Observable.from(future);

        // Schedule this future to run on the computation scheduler
        Schedulers.computation().createWorker().schedule(future::run); // Call the FutureTask's run method

        // Subscribe to the list... when the list is ready through the future,
        // iterate and print each element.
        observableFutureList.subscribe(list -> list.forEach(System.out::println));
    }
}
