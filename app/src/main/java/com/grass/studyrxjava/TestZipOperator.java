package com.grass.studyrxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/4/27.
 */

public class TestZipOperator {


    private static final String TAG = "zip";

    public static void testZipWith() {
        Observable.just("grass").zipWith(Observable.range(1, 3), new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer index) {
                return s + index;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "zip onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "zip onError: ");

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "zip onNext: " + s);
            }
        });
    }

    public static void testZip() {
        Log.i("zip", "testZip is working ---------------------------------");
        List<Observable<String>> listObservables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listObservables.add(createObservable(i));
        }
        Observable.zip(listObservables, new FuncN<String>() {
            @Override
            public String call(Object... args) {
                StringBuffer sb = new StringBuffer();
                for (Object arg : args) {
                    sb.append(arg).append("-");
                }
                return sb.toString();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("zip", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("zip", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("zip", "onNext: " + s);
                    }
                });
    }

    private static Observable<String> createObservable(final int pos) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                if (pos == 1) {
//                    subscriber.onError(new Exception("--------------------------posistion is " + pos + " valid"));
//                    subscriber.onCompleted();
//                    return;
//                }
//                for (int i = 0; i < 10; i++) {
//                    Log.i("zip", "call " + pos + " i: " + i);
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
                if (pos == 1) {
                    try {
                        Thread.sleep(1010);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("zip", "call " + pos);
                subscriber.onNext("pos: " + pos);
                subscriber.onCompleted();
            }
        });
    }
}
