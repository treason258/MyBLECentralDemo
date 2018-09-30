package com.mjiayou.trecorelib.json;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public class FastjsonImpl extends JsonHelper {

    @Override
    public String toJson(Object src) {
        return JSON.toJSONString(src);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> T fromJson(String json, Type clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> T fromJson(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
