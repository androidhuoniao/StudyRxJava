package com.grass.studyrxjava;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by grassswwang on 2017/4/25.
 */

public class TestEmptyObservable {

    public static void testEmpty() {
        Observable.empty().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        Log.i("empty", "onCompleted is working");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("empty", "onError is working");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.i("empty", "onNext is working " + (o == null));
                    }
                });
    }
}
