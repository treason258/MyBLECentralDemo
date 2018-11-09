package com.mjiayou.trecorelib.http.callback;

/**
 * Created by xin on 17/4/28.
 */
public abstract class BaseCallback<T> {

    public static final String TAG = BaseCallback.class.getSimpleName();

    // 成功
    public static final int TC_CODE_SUCCESS = 200;

    // 连接服务器失败
    public static final int TC_CODE_FAILURE_SERVER = -258;
    public static final String TC_MSG_FAILURE_SERVER = "连接服务器失败";

    // 解析失败
    public static final int TC_CODE_FAILURE_JSON = -259;
    public static final String TC_MSG_FAILURE_JSON = "解析数据失败";

    /**
     * 解析请求结果
     */
    public abstract void onResponse(String response);

    // ******************************** 抽象方法，子类务必实现 ********************************

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
     * 请求结束
     */
    public void onEnd() {
    }

    // ******************************** 默认Callback ********************************

    /**
     * CALLBACK_DEFAULT
     */
    public static BaseCallback DEFAULT = new BaseCallback() {

        @Override
        public void onResponse(String response) {

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
