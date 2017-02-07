package com.mjiayou.trecorelib.helper;

import android.content.Context;

import com.mjiayou.trecorelib.util.LogUtil;

/**
 * Created by treason on 2017/1/18.
 */

public class TCHelper {

    private static final String TAG = TCHelper.class.getSimpleName();

    public static Context mContext = null;
    public static String mAppName = "trecorelib";

    /**
     * 初始化
     */
    public static void init(Context context, String appName) {
        LogUtil.printInit(TAG);

        mContext = context;
        mAppName = appName;

        GsonHelper.init();
    }
}
