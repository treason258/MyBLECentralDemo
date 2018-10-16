package com.mjiayou.trecorelib.json;

import com.mjiayou.trecorelib.json.impl.GsonImpl;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public abstract class JsonParser {

    private static JsonParser mJsonParser;

    /**
     * set mJsonParser
     */
    public static void set(JsonParser jsonParser) {
        mJsonParser = jsonParser;
    }

    /**
     * get mJsonParser
     */
    public static JsonParser get() {
        if (mJsonParser == null) {
            mJsonParser = new GsonImpl();
        }
        return mJsonParser;
    }

    public abstract String toJson(Object object);

    public abstract <T> T toObject(String json, Class<T> clazz);

    public abstract <T> T toObject(String json, Type type);

    public abstract <T> T toObject(byte[] bytes, Class<T> clazz);
}
