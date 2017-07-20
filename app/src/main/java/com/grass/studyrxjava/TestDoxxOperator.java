package com.grass.studyrxjava;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/5/19.
 */

public class TestDoxxOperator {

    private static final String TAG = "doxx";

    public static void testDoOnTerminate() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("111");
//                subscriber.onCompleted();
                subscriber.onError(new NullPointerException("1111"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "doOnTerminate ");
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(TAG, "doOnError is working");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "doOnCompleted");

                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "doAfterTerminate");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: " + s);
                    }
                });
    }
}
