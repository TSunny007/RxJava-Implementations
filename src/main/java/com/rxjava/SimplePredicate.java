package com.rxjava;

import rx.Observable;

import java.util.LinkedList;
import java.util.List;

public class SimplePredicate {

    public static void main(String[] args) {
        // create an observable from a big list of integers (0...199)
        // Use a predicate to return only specific numbers

        Observable.from(generateBigIntegerList(200))
                .filter(i -> i%15 ==0 || i%17 == 1)
                .subscribe(System.out::println);
    }


    public static List<Integer> generateBigIntegerList(int number)
    {
        List<Integer> list = new LinkedList<>();
        for (int i =0; i < number; i++) {
            list.add(i);
        }
        return list;
    }
}
