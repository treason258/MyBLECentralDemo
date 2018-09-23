package com.mjiayou.trecorelib.base;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG
*/

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.mjiayou.trecorelib.common.Caches;
import com.mjiayou.trecorelib.helper.GsonHelper;
import com.mjiayou.trecorelib.helper.VolleyHelper;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.ProcessUtil;
import com.mjiayou.trecorelib.util.VersionUtil;

/**
 * TCApp
 */
public class TCApp extends Application {

    // TAG
    protected static final String TAG = TCApp.class.getSimpleName();

    // var
    private static Application mInstance;
    private Context mContext;

    /**
     * 获取Application对象
     */
    public static Application get() {
        return mInstance;
    }

    /**
     * onCreateManual
     */
    public static void onCreateManual(Application application) {
        mInstance = application;
        TCApp tcApp = new TCApp();
        tcApp.mContext = application.getApplicationContext();
        tcApp.initApp();
    }

    @Override
    public void onCreate() {
        LogUtils.printLifeRecycle(TAG, "onCreate");
        super.onCreate();

        // var
        mInstance = this;
        mContext = getApplicationContext();

        LogUtils.printLifeRecycle(TAG, "process id -> " + ProcessUtil.getProcessId());
        LogUtils.printLifeRecycle(TAG, "process name -> " + ProcessUtil.getProcessName(mContext));

        initApp();
    }

    @Override
    public void onTerminate() {
        LogUtils.printLifeRecycle(TAG, "onTerminate");
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.printLifeRecycle(TAG, "onConfigurationChanged | newConfig -> " + newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        LogUtils.printLifeRecycle(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtils.printLifeRecycle(TAG, "onTrimMemory | level -> " + level);
        super.onTrimMemory(level);
    }

    /**
     * 初始化APP
     */
    public void initApp() {
        LogUtils.printLifeRecycle(TAG, "initApp");
        LogUtils.printInit(TAG);

        /**
         * 初始化 配置信息
         */
        Caches.get().init();
        VersionUtil.init();

        /**
         * 初始化 第三方库
         */
        VolleyHelper.init();
        GsonHelper.init();
        // SwissArmyKnifeUtil.init();
    }

    // ******************************** project ********************************
}
