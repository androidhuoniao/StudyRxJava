package com.grass.studyrxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/4/20.
 */

public class TestConcatOperator {

    private static final String TAG = "concat";
    private Random mSleepRandom = new Random();

    public void testConcatWithFirst() {
        Observable.concat(checkCaptivePortalInner(), checkCaptivePortalOuter())
                .first().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted is working");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError is working");

                    }

                    @Override
                    public void onNext(Boolean result) {
                        Log.i(TAG, "onNext: " + result);
                    }
                });

    }

    public Observable<Boolean> checkCaptivePortalInner() {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Log.i(TAG, "call: checkCaptivePortalInner is working");
                boolean result = true;
                if (result) {
                    subscriber.onNext(result);
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Boolean> checkCaptivePortalOuter() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Log.i(TAG, "call: checkCaptivePortalOuter is working");
                boolean result = false;
                if (result) {
                    subscriber.onNext(result);
                } else {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public void testConcat() {
        Log.i("concat", "testConcat is working ---------------------------------");
        List<Observable<String>> listObservables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listObservables.add(createObservable(i));
        }
        Observable.concat(listObservables).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("concat", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("concat", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("concat", "onNext: " + s);
                    }
                });

    }

    public void testConcatDelayError() {
        Log.i("concat", "testConcatDelayError is working ---------------------------------");
        List<Observable<String>> listObservables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listObservables.add(createObservable(i));
        }
        Observable.concatDelayError(listObservables).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("concat", "testConcatDelayError: onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("concat", "testConcatDelayError: onError: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("concat", "testConcatDelayError onNext: " + s);
                    }
                });

    }

    private Observable<String> createObservable(final int pos) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (pos == 1) {
                    subscriber.onError(new Exception("--------------------------posistion is " + pos + " valid"));
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    Log.i("concat", "call " + pos + " i: " + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                subscriber.onNext("pos: " + pos);
                subscriber.onCompleted();
            }
        });
    }
}
