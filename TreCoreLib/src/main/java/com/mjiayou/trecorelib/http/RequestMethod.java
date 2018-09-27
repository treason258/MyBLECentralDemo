package com.mjiayou.trecorelib.http;

import com.android.volley.Request;

/**
 * Created by treason on 2016/10/28.
 */
public enum RequestMethod {

    DEPRECATED_GET_OR_POST("DEPRECATED_GET_OR_POST"),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH"),

    COPY("COPY"),
    MOVE("MOVE"),
    CONNECT("CONNECT"),
    
    POST_STRING("POST_STRING");

    private final String value;

    RequestMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * getVolleyMethod
     */
    public static int getVolleyMethod(RequestMethod requestMethod) {
//        int DEPRECATED_GET_OR_POST = -1;
//        int GET = 0;
//        int POST = 1;
//        int PUT = 2;
//        int DELETE = 3;
//        int HEAD = 4;
//        int OPTIONS = 5;
//        int TRACE = 6;
//        int PATCH = 7;
        switch (requestMethod) {
            case DEPRECATED_GET_OR_POST:
                return Request.Method.DEPRECATED_GET_OR_POST;
            case GET:
                return Request.Method.GET;
            case POST:
                return Request.Method.POST;
            case PUT:
                return Request.Method.PUT;
            case DELETE:
                return Request.Method.DELETE;
            case HEAD:
                return Request.Method.HEAD;
            case OPTIONS:
                return Request.Method.OPTIONS;
            case TRACE:
                return Request.Method.TRACE;
            case PATCH:
                return Request.Method.PATCH;
        }
        return Request.Method.POST;
    }
}
