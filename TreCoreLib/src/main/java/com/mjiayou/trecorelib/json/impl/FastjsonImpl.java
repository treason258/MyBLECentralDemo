package com.mjiayou.trecorelib.json.impl;

import com.alibaba.fastjson.JSON;
import com.mjiayou.trecorelib.json.JsonParser;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public class FastjsonImpl extends JsonParser {

    @Override
    public String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> T toObject(String json, Type clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> T toObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
