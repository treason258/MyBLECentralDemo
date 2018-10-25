package com.mjiayou.trecorelib.http.callback;

/**
 * Created by xin on 17/4/28.
 */
public abstract class BaseCallback<T> {

    // ******************************** 抽象方法，子类务必实现 ********************************

    /**
     * 解析请求结果
     */
    public abstract void onResult(String response);

    /**
     * 请求开始
     */
    public abstract void onStart();

    /**
     * 请求返回 - 成功
     *
     * @param code   状态码
     * @param object 返回对象
     */
    public abstract void onSuccess(int code, T object);

    /**
     * 请求返回 - 失败
     *
     * @param code 状态码
     * @param msg  错误信息
     */
    public abstract void onFailure(int code, String msg);

    // ******************************** 辅助方法 ********************************

    /**
     * 请求进行中（主要用于上传和下载）
     *
     * @param progress 当前进度
     * @param total    总量
     */
    public void onProgress(float progress, long total) {
    }

    /**
     * 请求返回 - 请求异常
     *
     * @param e 异常信息
     */
    public void onException(Exception e) {
    }

    /**
     * 请求结束
     */
    public void onEnd() {
    }

    // ******************************** 默认Callback ********************************

    /**
     * CALLBACK_DEFAULT
     */
    public static BaseCallback CALLBACK_DEFAULT = new BaseCallback() {

        @Override
        public void onResult(String response) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(int code, Object object) {

        }

        @Override
        public void onFailure(int code, String msg) {

        }
    };
}
