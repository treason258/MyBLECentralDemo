package com.mjiayou.trecorelib.http.okhttp;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.mjiayou.trecorelib.http.BaseCallback;
import com.mjiayou.trecorelib.http.RequestEntity;
import com.mjiayou.trecorelib.util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * 网络请求构建类
 */
public class RequestBuilder {

    public static final String TAG = RequestBuilder.class.getSimpleName();

    private static RequestBuilder mInstance;

    public RequestBuilder() {
    }

    /**
     * 单例模式获取实例
     */
    public static RequestBuilder get() {
        if (mInstance == null) {
            synchronized (RequestBuilder.class) {
                if (mInstance == null) {
                    mInstance = new RequestBuilder();
                }
            }
        }
        return mInstance;
    }

    // ******************************** OkHttp 网络请求 ********************************

    /**
     * 异步请求
     */
    public void send(final RequestEntity requestEntity, final BaseCallback callback) {
        final String logTag = "send";
        logRequest(logTag, requestEntity);

        RequestCall requestCall = null;
        switch (requestEntity.getMethod()) {
            case POST_STRING:
                requestCall = OkHttpUtils
                        .postString()
                        .url(requestEntity.getUrl())
                        .headers(requestEntity.getHeaders())
                        .content(requestEntity.getContent())
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build();
                break;
            case POST:
                requestCall = OkHttpUtils
                        .post()
                        .url(requestEntity.getUrl())
                        .headers(requestEntity.getHeaders())
                        .params(requestEntity.getParams())
                        .build();
                break;
            case GET:
                requestCall = OkHttpUtils
                        .get()
                        .url(requestEntity.getUrl())
                        .headers(requestEntity.getHeaders())
                        .params(requestEntity.getParams())
                        .build();
                break;
            case POST_FILE:
                PostFormBuilder postFormBuilder = OkHttpUtils.post();
                Map<String, File> files = requestEntity.getFiles();
                Set<Map.Entry<String, File>> sets = files.entrySet();
                for (Map.Entry<String, File> entry : sets) {
                    File file = entry.getValue();
                    postFormBuilder.addFile(entry.getKey(), file.getName(), file);
                }
                requestCall = postFormBuilder
                        .url(requestEntity.getUrl())
                        .headers(requestEntity.getHeaders())
                        .params(requestEntity.getParams())
                        .build();
                break;
            default:
                break;
        }

        if (requestCall != null) {
            requestCall.execute(new StringCallback() {
                @Override
                public void onBefore(Request request, int id) {
                    super.onBefore(request, id);
                    LogUtils.d(TAG, "onBefore() called with: request = [" + request + "], id = [" + id + "]");
                    if (callback != null) {
                        callback.onStart();
                    }
                }

                @Override
                public void onAfter(int id) {
                    super.onAfter(id);
                    LogUtils.d(TAG, "onAfter() called with: id = [" + id + "]");
                }

                @Override
                public void inProgress(float progress, long total, int id) {
                    super.inProgress(progress, total, id);
                    LogUtils.d(TAG, "inProgress() called with: progress = [" + progress + "], total = [" + total + "], id = [" + id + "]");
                    if (callback != null) {
                        callback.inProgress(progress, total);
                    }
                }

                @Override
                public void onError(Call call, Exception ex, int id) {
                    LogUtils.d(TAG, "onError() called with: call = [" + call + "], ex = [" + ex + "], id = [" + id + "]");
                    if (callback != null) {
                        callback.onException(ex);
                    }
                }

                @Override
                public void onResponse(String responseData, int id) {
                    LogUtils.d(TAG, "onResponse() called with: responseData = [" + responseData + "], id = [" + id + "]");
                    logResponse(logTag, requestEntity, responseData);

                    if (callback != null) {
                        callback.onResult(responseData);
                    }
                }
            });
        }
    }

    /**
     * downloadFile
     */
    public void downloadFile(String url, FileCallBack fileCallBack) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(fileCallBack);
    }

    /**
     * downLoadBitmap
     */
    public void downLoadBitmap(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {

                    }
                });
    }

    // ******************************** CookieStore ********************************

    // ******************************** 上传图片 ********************************

    // ******************************** 工具 ********************************

    /**
     * 网络请求打印LOG
     */
    private void logRequest(String tag, RequestEntity requestEntity) {
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
    private void logResponse(String tag, RequestEntity requestEntity, String responseData) {
        String responseInfo = tag + " -> response_info | " + "\n" +
                "request_url -> " + requestEntity.getUrl() + "\n" +
                "response_data -> " + responseData + "\n";
        LogUtils.d(TAG, responseInfo);
    }
}