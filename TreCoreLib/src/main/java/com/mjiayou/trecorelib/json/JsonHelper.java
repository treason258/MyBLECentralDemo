package com.mjiayou.trecorelib.json;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public abstract class JsonHelper {

    private static JsonHelper mJsonHelper;

    /**
     * set new mJsonHelper instance
     *
     * @param json new instance
     * @return new instance
     */
    public static void set(JsonHelper json) {
        mJsonHelper = json;
    }

    /**
     * get default mJsonHelper handler
     *
     * @return Json
     */
    public static JsonHelper get() {
        if (mJsonHelper == null) {
            mJsonHelper = new GsonImpl();
        }
        return mJsonHelper;
    }

    public abstract String toJson(Object src);

    public abstract <T> T fromJson(String json, Class<T> clazz);

    public abstract <T> T fromJson(String json, Type type);

    public abstract <T> T fromJson(byte[] bytes, Class<T> clazz);
}
