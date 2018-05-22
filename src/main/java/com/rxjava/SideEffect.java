package com.rxjava;

public class SideEffect {

    private int state = 0;

    /*Considered pure because doesn't depend on the state of the object, class, time or io*/
    public int f1(int x) { /*Same parameters -> same return*/
        return x*2;
    }


    /* Considered impure A bunch of threads calling on f2 will cause unpredictable results*/
    public int f2(int x) { /*Same parameters -> not the same result*/
        state = (++state%3);
        return x*2 +state;
    }

    public static void main(String[] args) {

        SideEffect v = new SideEffect();

        // I can call the f1 method any number of times, with any number of threads
        // and I will always get the same answer so long as I provide the same
        // parameters because f1's result is totally determined by the incoming
        // parameters and nothing else.
        for ( int i =0; i < 50; i++) {
            if (v.f1(5) != 10) throw new IllegalStateException();
        }

        // If I call the f2 method, I can never be sure of what I'm. going to
        // get because there is a side effect that effects the value returned
        // If another thread or call somewhere else in the code gets to it
        // before I do, then the value will not be what I expect.
        for (int i =0; i < 100; i++) {
            System.out.println("f2(5) = " + v.f2(5));
        }
    }
}
