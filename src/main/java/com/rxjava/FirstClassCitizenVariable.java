package com.rxjava;

import java.util.function.BiFunction;

public class FirstClassCitizenVariable {

    public static void main(String[] args) {
        // takes string, string and returns a string. Inline Lamba
        BiFunction<String,String,String> concatFunction = (t,u) -> t+" "+u;
        System.out.println(concatFunction.apply("Hello", "From the inline Lambda"));

        // Same thing as above, but uses the private static method.
        concatFunction = FirstClassCitizenVariable::concatStrings;
        System.out.println(concatFunction.apply("Hello", "From the private static method!"));

        // Same thing still, but uses the instance (package level) method.
        FirstClassCitizenVariable instance = new FirstClassCitizenVariable();
        concatFunction = instance::concatStrings2;
        System.out.println(concatFunction.apply("Hello", "From the instance method!"));
    }

    private static String concatStrings(String t, String u) {
        return t+ " "+ u;
    }

    /*Package*/ String concatStrings2(String t, String u) {
        return t + " " + u;
    }
}
