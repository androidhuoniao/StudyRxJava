package com.grass.studyrxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/7/19.
 */

public class TestRetryWhenOperator {

    public static final String TAG = "retry";

    private Observable<String> loadUserInfo() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "-------------call: is working");
                subscriber.onNext("grass");
                subscriber.onCompleted();
//                subscriber.onError(new Exception("onError is working"));
            }
        });
    }

    public void testLoadUserInfo() {
        loadUserInfo().retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                Log.i(TAG, "call: retryWhen is working");
//                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Throwable throwable) {
//                        Log.i(TAG, "call: flatMap is working");
//                        return Observable.error(new Exception("1111"));
//                    }
//                });
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer i) {
                        Log.i(TAG, "call: zip " + throwable.getMessage() + " i: " + i);
                        return i;
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: is working");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: is working " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: is working: " + s);
                    }
                });
    }

    public void testLoadUserInfo2() {
        loadUserInfo().retryWhen(new RetryWithDelay(3, 1000)).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: is working");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: is working " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: is working: " + s);
                    }
                });
    }


    public class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> attempts) {
            Log.i(TAG, "call: RetryWithDelay is working");
            return attempts
                    .flatMap(new Func1<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> call(Throwable throwable) {
                            if (++retryCount <= maxRetries) {
                                // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                                Log.i("retry", "get error, it will try after " + retryDelayMillis
                                        + " millisecond, retry count " + retryCount);
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }

}
