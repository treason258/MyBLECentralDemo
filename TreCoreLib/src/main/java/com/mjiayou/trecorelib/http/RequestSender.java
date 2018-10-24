package com.mjiayou.trecorelib.http;

import com.mjiayou.trecorelib.http.callback.BaseCallback;
import com.mjiayou.trecorelib.http.impl.OkHttpImpl;
import com.mjiayou.trecorelib.util.LogUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

/**
 * Created by xin on 18/10/16.
 */

public abstract class RequestSender {

    public static final String TAG = RequestSender.class.getSimpleName();

    private static RequestSender mRequestSender;

    /**
     * set mRequestSender
     */
    public static void set(RequestSender requestSender) {
        mRequestSender = requestSender;
    }

    /**
     * get mRequestSender
     */
    public static RequestSender get() {
        if (mRequestSender == null) {
            synchronized (RequestSender.class) {
                if (mRequestSender == null) {
                    mRequestSender = new OkHttpImpl();
                }
            }
        }
        return mRequestSender;
    }

    // ******************************** method ********************************

    public abstract void send(RequestEntity requestEntity, final BaseCallback baseCallback);

    public abstract void downloadFile(String url, FileCallBack fileCallBack);

    // ******************************** 工具 ********************************

    /**
     * 网络请求打印LOG
     */
    public void logRequest(String tag, RequestEntity requestEntity) {
        String requestInfo = tag + " -> request_info | " + "\n" +
                "request_url -> " + requestEntity.getUrl() + "\n" +
                "request_method -> " + requestEntity.getMethod().toString() + "\n" +
                "request_headers -> " + requestEntity.getHeaders() + "\n" +
                "request_params -> " + requestEntity.getParams() + "\n" +
                "request_files -> " + requestEntity.getFiles() + "\n" +
                "request_content -> " + requestEntity.getContent() + "\n" +
                "request_filePath -> " + requestEntity.getFilePath() + "\n" +
                "request_jsonObject -> " + requestEntity.getJsonObject() + "\n";
        LogUtils.d(TAG, requestInfo);
    }

    /**
     * 网络返回打印LOG
     */
    public void logResponse(String tag, RequestEntity requestEntity, String responseData) {
        String responseInfo = tag + " -> response_info | " + "\n" +
                "request_url -> " + requestEntity.getUrl() + "\n" +
                "response_data -> " + responseData + "\n";
        LogUtils.d(TAG, responseInfo);
    }
}
