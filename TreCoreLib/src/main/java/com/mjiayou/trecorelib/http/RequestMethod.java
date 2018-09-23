package com.mjiayou.trecorelib.http;

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
}
