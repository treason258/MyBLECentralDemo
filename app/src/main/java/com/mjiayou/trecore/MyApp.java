package com.mjiayou.trecore;

import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.helper.TCHelper;
import com.mjiayou.trecorelib.util.LogUtils;

/**
 * Created by treason on 2017/6/15.
 */

public class MyApp extends TCApp {

    @Override
    public void onCreate() {
        LogUtils.traceStart(TAG);
        super.onCreate();
        LogUtils.traceStop(TAG);
    }
}
