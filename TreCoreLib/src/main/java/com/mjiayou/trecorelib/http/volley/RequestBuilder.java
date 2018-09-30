package com.mjiayou.trecorelib.http.volley;

import android.os.Handler;
import android.os.Message;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.bean.TCResponse;
import com.mjiayou.trecorelib.common.Configs;
import com.mjiayou.trecorelib.http.RequestEntity;
import com.mjiayou.trecorelib.json.JsonHelper;
import com.mjiayou.trecorelib.util.ConvertUtils;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.StreamUtils;
import com.mjiayou.trecorelib.util.ToastUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class RequestBuilder {

    private Object mTagObject;
    private RequestQueue mRequestQueue;
    private Handler mResponseHandler;
    private JsonHelper mJsonHelper;

    /**
     * 构造函数
     */
    public RequestBuilder(Object tagObject, RequestQueue requestQueue, Handler responseHandler) {
        this.mTagObject = tagObject;
        this.mRequestQueue = requestQueue;
        this.mResponseHandler = responseHandler;
        this.mJsonHelper = JsonHelper.get();
    }

    /**
     * Volley请求
     */
    public <T extends TCResponse> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category, Listener<T> responseListener) {
        String requestInfo = "request_info | " + "\n" +
                "request_method -> " + requestEntity.getMethod() + "\n" +
                "request_url -> " + requestEntity.getUrl() + "\n" +
                "request_content -> " + requestEntity.getContent() + "\n" +
                "request_headers -> " + requestEntity.getHeaders() + "\n" +
                "request_params -> " + requestEntity.getParams() + "\n";
        LogUtils.i(Configs.TAG_VOLLEY, requestInfo);

        if (responseListener == null) {
            responseListener = new Listener<T>() {
                @Override
                public void onResponse(T response) {
                    mResponseHandler.sendMessage(Message.obtain(null, category, response));
                }
            };
        }
        RequestCallback<T> request = new RequestCallback<>(requestEntity, new TCErrorListener(category), clazz, responseListener);
        request.setTag(mTagObject);
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(Configs.DEFAULT_TIMEOUT_MS, Configs.DEFAULT_MAX_RETRIES, Configs.DEFAULT_BACKOFF_MULT)); // 设置超时时间、重连次数
        mRequestQueue.add(request);
    }

    public <T extends TCResponse> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category) {
        buildAndAddRequest(requestEntity, clazz, category, null);
    }

    /**
     * Http请求
     */
    public <T> void buildAndAddRequestByHttpClient(final RequestEntity requestEntity, final Class<T> clazz, final int category, final Listener<T> responseListener) {
        String requestInfo = "request_info | " + "\n" +
                "request_method -> " + requestEntity.getMethod() + "\n" +
                "request_url -> " + requestEntity.getUrl() + "\n" +
                "request_content -> " + requestEntity.getContent() + "\n" +
                "request_headers -> " + requestEntity.getHeaders() + "\n" +
                "request_params -> " + requestEntity.getParams() + "\n";
        LogUtils.i(Configs.TAG_VOLLEY, requestInfo);

        new Thread() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(requestEntity.getUrl());
                MultipartEntity multipartEntity = new MultipartEntity();

                try {
                    for (Map.Entry<String, String> entry : requestEntity.getParams().entrySet()) {
                        if (entry.getValue() != null) {
                            multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName(HTTP.UTF_8)));
                        }
                    }
                    for (Map.Entry<String, File> entry : requestEntity.getFiles().entrySet()) {
                        if (entry.getValue() != null) {
                            String mimeType; // image/jpg
                            try {
                                File file = entry.getValue();
                                mimeType = "image/" + file.getName().substring(file.getName().lastIndexOf(".") + 1);
                            } catch (Exception e) {
                                LogUtils.printStackTrace(e);
                                mimeType = "image/jpg";
                            }
                            multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue(), mimeType));
                        }
                    }

                    httpPost.setEntity(multipartEntity);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity httpEntity = response.getEntity();
                    if (httpEntity != null) {
                        InputStream inputStream = null;
                        String responseString;
                        try {
                            inputStream = httpEntity.getContent();
                            responseString = ConvertUtils.parseString(inputStream);

                            T result = mJsonHelper.fromJson(responseString, clazz);
                            String responseInfo = "request_info | " + "\n" +
                                    "response_data_string -> " + responseString + "\n" +
                                    "response_data_object -> " + mJsonHelper.toJson(result) + "\n";
                            LogUtils.i(Configs.TAG_VOLLEY, responseInfo);
                            responseListener.onResponse(result);
                        } catch (Exception e) {
                            LogUtils.printStackTrace(e);
                        } finally {
                            StreamUtils.closeQuietly(inputStream);
                        }
                    }
                } catch (Exception e) {
                    LogUtils.printStackTrace(e);
                }
            }
        }.start();
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mRequestQueue.cancelAll(mTagObject);
    }

    /**
     * 异常处理
     */
    private class TCErrorListener implements ErrorListener {
        private final int mCategory;

        public TCErrorListener(int category) {
            mCategory = category;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mResponseHandler.sendMessage(Message.obtain(null, mCategory, null));
            if (error instanceof NoConnectionError) {
                ToastUtils.show(TCApp.get().getString(R.string.tc_error_no_connection));
            } else if (error instanceof TimeoutError) {
                ToastUtils.show(TCApp.get().getString(R.string.tc_error_time_out));
            } else if (error instanceof ServerError) {
                ToastUtils.show(TCApp.get().getString(R.string.tc_error_server));
            } else if (error instanceof ParseError) {
                ToastUtils.show(TCApp.get().getString(R.string.tc_error_parse));
            } else {
                ToastUtils.show(TCApp.get().getResources().getString(R.string.tc_error_other) + ":" + error.toString());
            }
        }
    }
}