package com.mjiayou.trecore;

import android.app.Application;

import com.mjiayou.trecorelib.helper.TCHelper;
import com.mjiayou.trecorelib.util.LogUtils;

/**
 * Created by treason on 2017/6/15.
 */

public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();

    @Override
    public void onCreate() {
        LogUtils.traceStart(TAG);
        super.onCreate();

        TCHelper.init(this);

        LogUtils.traceStop(TAG);
    }
}
