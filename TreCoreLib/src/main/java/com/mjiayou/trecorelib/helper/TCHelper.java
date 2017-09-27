package com.mjiayou.trecorelib.helper;

/**
 * Created by treason on 2017/6/15.
 */

public class TCHelper {

    private static final String TAG = TCHelper.class.getSimpleName();

    public static final String CALL_INIT_FIRST = "you need call TCHelper.init(context, appName) in the Application";
    public static final String ERROR_CONTEXT_NULL = "context == null";

    private static String mAppName = "trecorelib";

    /**
     * 设置 mAppName
     */
    public static void setAppname(String appName) {
        mAppName = appName;
    }

    /**
     * 返回 mAppName
     */
    public static String getAppName() {
        return mAppName;
    }
}
