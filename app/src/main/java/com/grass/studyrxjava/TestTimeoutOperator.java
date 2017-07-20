package com.grass.studyrxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by grassswwang on 2017/4/26.
 */

public class TestTimeoutOperator {

    public static void testTimeout() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(4 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("original observable is working");
                subscriber.onCompleted();
            }
        }).timeout(3, TimeUnit.SECONDS, Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("timeout default observable is working");
            }
        }))
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        return "onErrorReturn is working";
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i("timeout", "onCompete : ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("timeout", "onError : " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("timeout", "onNext : " + s);
                    }
                });
    }
}
