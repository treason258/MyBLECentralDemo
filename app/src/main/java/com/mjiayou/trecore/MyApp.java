package com.mjiayou.trecore;

import android.app.Application;

import com.mjiayou.trecorelib.helper.TCHelper;

/**
 * Created by treason on 2017/1/18.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TCHelper.init(this, "trecore");
    }
}
