package com.cegeka.rxjavaplay;

import io.reactivex.Observable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RxJavaTest {

    @Test
    public void returnAValue() {
        final String result = "";
        Observable<String> stream = Observable.just("Hello");

        stream.subscribe(s -> System.out.println(s));
    }
}
