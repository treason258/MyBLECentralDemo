//package com.mjiayou.trecorelib.http.callback;
//
///**
// * Created by xin on 18/10/25.
// */
//
//public abstract class TCCallback<T> {
//    /**
//     * UI Thread
//     */
//    public void onBefore() {
//    }
//
//    /**
//     * UI Thread
//     */
//    public void onAfter() {
//    }
//
//    /**
//     * UI Thread
//     */
//    public void onProgress(float progress, long total) {
//
//    }
//
//    /**
//     * Thread Pool Thread
//     */
//    public abstract T parseNetworkResponse(String responseData) throws Exception;
//
//    public abstract void onError(Exception e);
//
//    public abstract void onResponse(T response, int id);
//
//    /**
//     * CALLBACK_DEFAULT
//     */
//    public static TCCallback CALLBACK_DEFAULT = new TCCallback() {
//
//        @Override
//        public Object parseNetworkResponse(String responseData) throws Exception {
//            return null;
//        }
//
//        @Override
//        public void onError(Exception e) {
//
//        }
//
//        @Override
//        public void onResponse(Object response, int id) {
//
//        }
//    };
//
//}