package com.mjiayou.trecorelib.json;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public abstract class JsonParser {

    private static JsonParser mJsonParser;

    /**
     * set new mJsonParser instance
     *
     * @param json new instance
     * @return new instance
     */
    public static void set(JsonParser json) {
        mJsonParser = json;
    }

    /**
     * get default mJsonParser handler
     *
     * @return Json
     */
    public static JsonParser get() {
        if (mJsonParser == null) {
            mJsonParser = new GsonImpl();
        }
        return mJsonParser;
    }

    public abstract String toJson(Object src);

    public abstract <T> T toObject(String json, Class<T> clazz);

    public abstract <T> T toObject(String json, Type type);

    public abstract <T> T toObject(byte[] bytes, Class<T> clazz);
}
