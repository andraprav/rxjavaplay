package com.cegeka.rxjavaplay;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class RxJavaPlay {

    public void test() {
        Observable<Todo> stream = Observable.create(emitter -> {
            try {
                List<Todo> todos = getTodos();
                for (Todo todo : todos) {
                    emitter.onNext(todo);
                }
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

        Disposable subscribe = stream.subscribe(t -> System.out.print(t), e -> e.printStackTrace());
    }

    private List<Todo> getTodos() {
        return new ArrayList<>();
    }
}
