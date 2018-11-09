package com.mjiayou.trecorelib.http.callback;

/**
 * Created by treason on 2018/10/24.
 */

public abstract class StringCallback extends BaseCallback<String> {

    @Override
    public void onResponse(String response) {
        onSuccess(TC_CODE_SUCCESS, response);
    }
}
