package com.grass.studyrxjava;

import android.util.Log;

import com.grass.studyrxjava.base.ITestCace;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by grassswwang on 2017/7/20.
 */

public class TestColdObservable implements ITestCace {

    public void test() {
        try {
            Observable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS);
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.i("cold", "First: " + aLong + " " + Thread.currentThread().toString());
                }
            });
            Thread.sleep(500);
            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.i("cold", "Second: " + aLong + " " + Thread.currentThread().toString());
                }
            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
            secondSubs.unsubscribe();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTestName() {
//        return getClass().getSimpleName();
        return "TestColdObservable";
    }

    @Override
    public void doOnClick() {
        test();
    }
}
