package com.mjiayou.trecorelib.json;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;

/**
 * Created by treason on 2018/9/29.
 */

public class FastjsonImpl extends JsonParser {

    @Override
    public String toJson(Object src) {
        return JSON.toJSONString(src);
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
