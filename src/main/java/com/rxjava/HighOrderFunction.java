package com.rxjava;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class HighOrderFunction {
    public static void main(String[] args) {
        Supplier<String> xformOperation = createCombineAndTransform("Hello", "World", a -> a.toUpperCase());
        // The return high-order function isn't actually called until get is called.
        System.out.println(xformOperation.get());
    }

    public static Supplier<String> createCombineAndTransform (final String a, final String b, final Function<String, String> transformer) {
        return () -> {
            String aa = Optional.ofNullable(transformer).orElse(q -> q).apply(a);
            String bb = Optional.ofNullable(transformer).orElse(q -> q).apply(b);
            return aa + " " + bb;
        };
    }
}
