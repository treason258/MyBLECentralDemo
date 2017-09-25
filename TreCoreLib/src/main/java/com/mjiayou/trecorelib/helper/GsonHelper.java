package com.mjiayou.trecorelib.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mjiayou.trecorelib.util.LogUtils;

/**
 * Created by treason on 16/5/14.
 */
public class GsonHelper {

    private static final String TAG = GsonHelper.class.getSimpleName();

    /**
     * 初始化
     */
    public static void init() {
        LogUtils.printInit(TAG);
    }

    /**
     * 获取gson对象
     */
    public static Gson get() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
