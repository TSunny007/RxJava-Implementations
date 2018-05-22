package com.rxjava;

import java.util.Optional;
import java.util.function.Function;

public class FirstClassCitizenParameter {

    public static void main(String[] args) {

        // The concatAndTransform call illustrates how functions can be passed
        // as a parameter. Function (lambdas) can be passed directly or...
        System.out.println(concatAndTransform("Hello", "World",  s -> s.toUpperCase()));
        // ...from a variable
        Function<String, String> transformToLower = s -> s.toLowerCase();

        System.out.println(concatAndTransform("Hello", "World",  transformToLower));
    }

    // The method applies the function on the strings provided.
    public static String concatAndTransform(String t, String u, Function<String, String> stringTransform)  {

        t = Optional.ofNullable(stringTransform).orElse(q -> q).apply(t);
        u = Optional.ofNullable(stringTransform).orElse(q -> q).apply(u);
        return t + " " + u;
    }
}
