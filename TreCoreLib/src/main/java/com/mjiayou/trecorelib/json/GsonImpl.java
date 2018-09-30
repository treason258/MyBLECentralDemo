package com.mjiayou.trecorelib.json;

import com.google.gson.Gson;
import com.mjiayou.trecorelib.helper.GsonHelper;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public class GsonImpl extends JsonHelper {

    private Gson mGson = GsonHelper.get();

    @Override
    public String toJson(Object src) {
        return mGson.toJson(src);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return mGson.fromJson(json, clazz);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        return mGson.fromJson(json, type);
    }

    @Override
    public <T> T fromJson(byte[] bytes, Class<T> clazz) {
        return mGson.fromJson(new String(bytes), clazz);
    }
}
