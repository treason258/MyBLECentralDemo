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
    public static JsonHelper set(JsonHelper json) {
        mJsonHelper = json;
        return mJsonHelper;
    }

    /**
     * get default mJsonHelper handler
     *
     * @return Json
     */
    public static JsonHelper getDefault() {
        if (mJsonHelper == null) {
            mJsonHelper = new GsonImpl();
        }
        return mJsonHelper;
    }

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> clazz);

    public abstract <T> T toObject(String json, Type clazz);

    public abstract <T> T toObject(byte[] bytes, Class<T> clazz);
}
