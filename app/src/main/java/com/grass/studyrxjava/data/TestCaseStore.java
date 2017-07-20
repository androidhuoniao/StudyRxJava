package com.grass.studyrxjava.data;

import com.grass.studyrxjava.TestColdObservable;
import com.grass.studyrxjava.TestHotObservable;
import com.grass.studyrxjava.base.ITestCace;

import java.util.ArrayList;

/**
 * Created by grassswwang on 2017/7/20.
 */

public class TestCaseStore {
    public static final ArrayList<ITestCace> STORE = new ArrayList<>();

    static {
        STORE.add(new TestHotObservable());
        STORE.add(new TestColdObservable());
    }
}
