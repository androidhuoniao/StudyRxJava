package com.grass.studyrxjava;

import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/4/25.
 */

public class TestFromCallableObservable {

    public static void testFromCallable() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "testFromCallable: " + Thread.currentThread().toString();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("callable", "onNext : " + s);
                    }
                });
    }

    public static void testFromCallable2() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "testFromCallable2: " + Thread.currentThread().toString();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("callable", "onNext : " + s);
                    }
                });
    }
}
