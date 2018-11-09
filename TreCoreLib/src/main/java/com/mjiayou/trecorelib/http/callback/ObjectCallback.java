package com.mjiayou.trecorelib.http.callback;

import com.mjiayou.trecorelib.json.JsonParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by treason on 2018/10/24.
 */

public abstract class ObjectCallback<T> extends BaseCallback<T> {

    @Override
    public void onResponse(String response) {
        try {
            T result = JsonParser.get().toObject(response, getObjectType());
            onSuccess(TC_CODE_SUCCESS, result);
        } catch (Exception e) {
            onFailure(TC_CODE_FAILURE_JSON, TC_MSG_FAILURE_JSON);
        }
    }

    /**
     * getObjectType
     */
    private Type getObjectType() {
        ParameterizedType parameterizedType = ((ParameterizedType) this.getClass().getGenericSuperclass());
        if (parameterizedType != null) {
            return parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }
}
