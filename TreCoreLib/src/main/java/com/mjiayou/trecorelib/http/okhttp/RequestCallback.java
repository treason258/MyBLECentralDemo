package com.mjiayou.trecorelib.http.okhttp;

import com.google.gson.reflect.TypeToken;
import com.mjiayou.trecorelib.http.BaseCallback;
import com.mjiayou.trecorelib.json.JsonHelper;
import com.mjiayou.trecorelib.util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 网络请求回调类
 */
public abstract class RequestCallback<T> implements BaseCallback {

    // 预留接口，保留 onStart、onResult、onSuccess、onFailure、onException 流程
    // 请求开始调用 onStart（抽象）
    // 请求返回调用 onResult，由此分发 onSuccess（抽象）、onFailure（抽象）
    // 请求异常调用 onException，最终调用 code=CODE_EXCEPTION 的 onFailure

    public static final String TAG = RequestCallback.class.getSimpleName();

    //接口定义
    //SUCCESS(900, "请求成功"),
    //SERVER_ERR(901, "服务异常"),
    //PARAM_ERR(902, "参数异常"),
    //URL_404(903, "404异常"),
    //HTTP_METHOD_ERR(904, "HTTP请求方法异常"),
    //TOKEN_EXPIRE(905, "TOKEN过期");

    private final int CODE_SUCCESS = 900; // 请求成功

    private final int CODE_FAILURE_JSON = -1; // 解析失败
    private final int CODE_FAILURE_EXCEPTION = -2; // 解析失败
    private final int CODE_FAILURE_SERVICE = 901; // 服务异常
    private final int CODE_FAILURE_PARAM = 902; // 参数异常
    private final int CODE_FAILURE_404 = 903; // 404异常
    private final int CODE_FAILURE_HTTP = 904; // HTTP请求方法异常
    private final int CODE_FAILURE_TOKEN = 905; // TOKEN过期

    private final String MSG_FAILURE_JSON = "解析失败"; // 解析失败
    private final String MSG_FAILURE_EXCEPTION = "异常"; // 异常
    private final String MSG_FAILURE_SERVICE = "服务异常"; // 服务异常
    private final String MSG_FAILURE_PARAM = "参数异常"; // 参数异常
    private final String MSG_FAILURE_404 = "404异常"; // 404异常
    private final String MSG_FAILURE_HTTP = "HTTP请求方法异常"; // HTTP请求方法异常
    private final String MSG_FAILURE_TOKEN = "TOKEN过期"; // TOKEN过期

    /**
     * 请求返回
     *
     * @param response 网络请求返回数据
     */
    @Override
    public void onResult(String response) {
        ResponseBean<T> responseBean = null;
        try {
            responseBean = JsonHelper.get().fromJson(response, new TypeToken<ResponseBean<Object>>() {
            }.getType());
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }

        if (responseBean == null) {
            onFailure(CODE_FAILURE_JSON, MSG_FAILURE_JSON); // 解析失败
        } else {
            switch (responseBean.getStatusCode()) {
                case CODE_SUCCESS:
                    if (getObjectType() == String.class) {
                        T object = null;
                        try {
                            object = (T) JsonHelper.get().toJson(responseBean.getData());
                        } catch (Exception e) {
                            LogUtils.printStackTrace(e);
                        }
                        onSuccess(CODE_SUCCESS, object);
                    } else {
                        responseBean = getResponseBean(response);
                        if (responseBean == null) {
                            onFailure(CODE_FAILURE_JSON, MSG_FAILURE_JSON);
                        } else {
                            onSuccess(CODE_SUCCESS, responseBean.getData());
                        }
                    }
                    break;
                case CODE_FAILURE_SERVICE:
                case CODE_FAILURE_PARAM:
                case CODE_FAILURE_404:
                case CODE_FAILURE_HTTP:
                case CODE_FAILURE_TOKEN:
                default:
                    if (responseBean.getMsg() == null) {
                        responseBean.setMsg("null");
                    }
                    onFailure(responseBean.getStatusCode(), responseBean.getMsg());
                    break;
            }
        }
    }

    /**
     * 请求异常
     *
     * @param ex 异常ex
     */
    @Override
    public void onException(Exception ex) {
        LogUtils.printStackTrace(ex);
        onFailure(CODE_FAILURE_EXCEPTION, MSG_FAILURE_EXCEPTION);
    }

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

    /**
     * getResponseBean
     */
    private ResponseBean<T> getResponseBean(String response) {
        ResponseBean<T> responseBean = null;
        try {
            if (getObjectType() != String.class) {
                Type type = getParameterizedType(ResponseBean.class, getObjectType());
                responseBean = JsonHelper.get().fromJson(response, type);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return responseBean;
    }

    /**
     * getObjectType
     */
    private Type getObjectType() {
        ParameterizedType parameterizedType = ((ParameterizedType) this.getClass().getGenericSuperclass());
        if (parameterizedType != null) {
            return parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * getParameterizedType
     */
    private ParameterizedType getParameterizedType(final Class raw, final Type... args) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return args;
            }

            @Override
            public Type getRawType() {
                return raw;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
