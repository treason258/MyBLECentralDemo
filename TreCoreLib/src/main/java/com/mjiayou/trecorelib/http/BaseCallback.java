package com.mjiayou.trecorelib.http;

/**
 * Created by xin on 17/4/28.
 */
public interface BaseCallback {

    void onStart();

    void onResult(String response);

    void onException(Exception ex);
}
