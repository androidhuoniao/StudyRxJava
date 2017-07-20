package com.grass.studyrxjava;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by grassswwang on 2017/5/19.
 */

public class TestRegix {

    public static void test() {
        String str = "1111[adsf|111|dsf]xxx";
        String str2 = "222222";

        Pattern pattern = Pattern.compile(".*?\\[(.*?)\\].*?");
        Matcher matcher = pattern.matcher(str);
        Matcher matcher2 = pattern.matcher(str2);

        Log.i("matcher", "str: " + matcher.find() + " str2: " + matcher2.find());

    }
}
