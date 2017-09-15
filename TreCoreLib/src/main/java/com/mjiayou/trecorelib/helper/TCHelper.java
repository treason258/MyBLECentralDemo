package com.mjiayou.trecorelib.helper;

import android.content.Context;
import android.text.TextUtils;

import com.mjiayou.trecorelib.util.LogUtils;

/**
 * Created by treason on 2017/6/15.
 */

public class TCHelper {

    private static final String TAG = TCHelper.class.getSimpleName();

    public static final String CALL_INIT_FIRST = "you need call TCHelper.init(context, appName) in the Application";
    public static final String ERROR_CONTEXT_NULL = "context == null";

    private static Context mContext = null;
    private static String mAppName = "trecorelib";

    /**
     * 初始化
     */
    public static void init(Context context, String appName) {
        LogUtils.printInit(TAG);

        if (context != null) {
            mContext = context;
        }
        if (!TextUtils.isEmpty(appName)) {
            mAppName = appName;
        }
    }

    public static void init(Context context) {
        init(context, null);
    }

    /**
     * 返回 mContext
     */
    public static Context getContext() {
        if (mContext == null) {
            LogUtils.e(TAG, ERROR_CONTEXT_NULL + " | " + CALL_INIT_FIRST);
            return null;
        }
        return mContext;
    }

    /**
     * 返回 mAppName
     */
    public static String getAppName() {
        return mAppName;
    }
}
