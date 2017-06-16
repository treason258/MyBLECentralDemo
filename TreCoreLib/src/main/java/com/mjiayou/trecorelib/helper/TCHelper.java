package com.mjiayou.trecorelib.helper;

import android.content.Context;

import com.mjiayou.trecorelib.util.LogUtil;

/**
 * Created by treason on 2017/6/15.
 */

public class TCHelper {

    private static final String TAG = TCHelper.class.getSimpleName();

    private static Context mContext = null;
    private static String mAppName = "trecorelib";

    /**
     * 初始化
     */
    public static void init(Context context, String appName) {
        LogUtil.printInit(TAG);

        mContext = context;
        mAppName = appName;
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getAppName() {
        return mAppName;
    }
}
