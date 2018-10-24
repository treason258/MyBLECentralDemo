package com.mjiayou.trecorelib.http.callback;

/**
 * Created by xin on 17/4/28.
 */
public interface BaseCallback {

    void onStart();

    void inProgress(float progress, long total);

    void onResult(String response);

    void onException(Exception ex);
}
