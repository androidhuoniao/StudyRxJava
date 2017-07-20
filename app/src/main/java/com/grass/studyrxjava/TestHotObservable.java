package com.grass.studyrxjava;

import android.util.Log;

import com.grass.studyrxjava.base.ITestCace;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

/**
 * Created by grassswwang on 2017/7/20.
 */

public class TestHotObservable implements ITestCace {

    public void test() {
        try {
            ConnectableObservable<Long> cold = Observable.interval(200, TimeUnit.MILLISECONDS)
                    .filter(new Func1<Long, Boolean>() {
                        @Override
                        public Boolean call(Long aLong) {
                            Log.i("hot", "call: filter is working: " + aLong);
                            return true;
                        }
                    }).publish();
            cold.connect();
            Subscription firstSubs = cold.subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.i("hot", "First: " + aLong);
                }
            });

//            Thread.sleep(500);
//            Subscription secondSubs = cold.subscribe(new Action1<Long>() {
//                @Override
//                public void call(Long aLong) {
//                    Log.i("hot", "Second: " + aLong);
//                }
//            });
            Thread.sleep(500);
            firstSubs.unsubscribe();
//            secondSubs.unsubscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTestName() {
        return "Test Hot Observable";
    }

    @Override
    public void doOnClick() {
        test();
    }
}
