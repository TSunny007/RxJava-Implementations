package com.rxjava;

import rx.Observable;

import java.util.*;

public class SimpleObservable {

    public static void main(String[] args)
    {
        Observable<Long> observable = null;

        System.out.println("-----------------");
        System.out.println("Observable creation from an array of size 1");
        System.out.println("-----------------");
        observable = Observable.from(new Long[]{42L});
        observable.subscribe((i) -> {
           System.out.println(i);
        });

        System.out.println("-----------------");
        System.out.println("Observable creation from an Iterable");
        System.out.println("-----------------");
        observable = Observable.from(generateFibonacci(10));
        // This is the same thing as shown in the above lambda, just concise.
        observable.subscribe(System.out::println);
    }

    public static List<Long> generateFibonacci(int numbers) {
        Map<Integer, Long> directory = new HashMap<>();
        List<Long> list = new ArrayList<>();
        for (int i=1; i <= numbers; i++) {
            list.add(fib(i, directory));
        }
        return list;
    }

    private static Long fib(int number, Map<Integer, Long> fibMap) {
        if (fibMap.containsKey(number)) return fibMap.get(number);
        if (number <= 2) {
            fibMap.put(number, 1L); return 1L;}
        long value = fib(number -1, fibMap) + fib(number-2, fibMap);
        fibMap.put(number, value); return value;
    }
}
