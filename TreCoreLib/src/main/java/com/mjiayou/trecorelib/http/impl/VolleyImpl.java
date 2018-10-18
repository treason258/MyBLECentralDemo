//package com.mjiayou.trecorelib.http.impl;
//
//import android.os.Message;
//import android.support.annotation.NonNull;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.mjiayou.trecorelib.R;
//import com.mjiayou.trecorelib.base.TCApp;
//import com.mjiayou.trecorelib.common.Configs;
//import com.mjiayou.trecorelib.helper.VolleyHelper;
//import com.mjiayou.trecorelib.http.BaseCallback;
//import com.mjiayou.trecorelib.http.RequestEntity;
//import com.mjiayou.trecorelib.http.RequestSender;
//import com.mjiayou.trecorelib.http.volley.RequestBuilder;
//import com.mjiayou.trecorelib.http.volley.RequestCallback;
//import com.mjiayou.trecorelib.util.LogUtils;
//import com.mjiayou.trecorelib.util.ToastUtils;
//
///**
// * Created by xin on 18/10/16.
// */
//
//public class VolleyImpl extends RequestSender {
//
//
//    @Override
//    public void send(RequestEntity requestEntity, BaseCallback baseCallback) {
//        String requestInfo = "request_info | " + "\n" +
//                "request_method -> " + requestEntity.getMethod() + "\n" +
//                "request_url -> " + requestEntity.getUrl() + "\n" +
//                "request_content -> " + requestEntity.getContent() + "\n" +
//                "request_headers -> " + requestEntity.getHeaders() + "\n" +
//                "request_params -> " + requestEntity.getParams() + "\n";
//        LogUtils.i(Configs.TAG_VOLLEY, requestInfo);
//
//        RequestCallback request = new RequestCallback<>(requestEntity, new TCErrorListener(), clazz, new Response.Listener() {
//            @Override
//            public void onResponse(Object o) {
//
//            }
//        });
//
//
//        Request request1 = new Request() {
//            @Override
//            protected Response parseNetworkResponse(NetworkResponse networkResponse) {
//                return null;
//            }
//
//            @Override
//            protected void deliverResponse(Object o) {
//
//            }
//
//            @Override
//            public int compareTo(@NonNull Object o) {
//                return 0;
//            }
//        }
//        request.setShouldCache(true);
//        request.setRetryPolicy(new DefaultRetryPolicy(Configs.DEFAULT_TIMEOUT_MS, Configs.DEFAULT_MAX_RETRIES, Configs.DEFAULT_BACKOFF_MULT)); // 设置超时时间、重连次数
//        VolleyHelper.getRequestQueue().add(request);
//    }
//
//    /**
//     * 异常处理
//     */
//    private class TCErrorListener implements Response.ErrorListener {
//
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            if (error instanceof NoConnectionError) {
//                ToastUtils.show(TCApp.get().getString(R.string.tc_error_no_connection));
//            } else if (error instanceof TimeoutError) {
//                ToastUtils.show(TCApp.get().getString(R.string.tc_error_time_out));
//            } else if (error instanceof ServerError) {
//                ToastUtils.show(TCApp.get().getString(R.string.tc_error_server));
//            } else if (error instanceof ParseError) {
//                ToastUtils.show(TCApp.get().getString(R.string.tc_error_parse));
//            } else {
//                ToastUtils.show(TCApp.get().getResources().getString(R.string.tc_error_other) + ":" + error.toString());
//            }
//        }
//    }
//}
