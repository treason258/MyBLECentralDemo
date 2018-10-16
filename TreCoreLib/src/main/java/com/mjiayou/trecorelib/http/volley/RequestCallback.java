package com.mjiayou.trecorelib.http.volley;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.mjiayou.trecorelib.bean.TCResponse;
import com.mjiayou.trecorelib.common.Configs;
import com.mjiayou.trecorelib.http.RequestEntity;
import com.mjiayou.trecorelib.http.RequestMethod;
import com.mjiayou.trecorelib.json.JsonParser;
import com.mjiayou.trecorelib.util.ConvertUtils;
import com.mjiayou.trecorelib.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RequestCallback<T extends TCResponse> extends Request<T> {

    private RequestEntity mRequestEntity;
    private final Class<T> mClazz;
    private final Listener<T> mResponseListener;
    private final JsonParser mJsonParser;

    /**
     * 构造函数
     */
    public RequestCallback(RequestEntity requestEntity, ErrorListener errorListener, Class<T> clazz, Listener<T> responseListener) {
        super(RequestMethod.getVolleyMethod(requestEntity.getMethod()), requestEntity.getUrl(), errorListener);
        this.mRequestEntity = requestEntity;
        this.mClazz = clazz;
        this.mResponseListener = responseListener;
        this.mJsonParser = JsonParser.get();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mRequestEntity.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (!TextUtils.isEmpty(mRequestEntity.getContent())) {
            return mRequestEntity.getContent().getBytes();
        }
        return super.getBody();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestEntity.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString;
            String encoding = response.headers.get("Content-Encoding");
            if (!TextUtils.isEmpty(encoding) && encoding.equals("gzip")) {
                responseString = ConvertUtils.parseString(response.data);
            } else {
                responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            }
            T result = mJsonParser.toObject(responseString, mClazz);
            String responseInfo = "request_info | " + "\n" +
                    "response_data_string -> " + responseString + "\n" +
                    "response_data_object -> " + mJsonParser.toJson(result) + "\n";
            LogUtils.i(Configs.TAG_VOLLEY, responseInfo);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mResponseListener.onResponse(response);
    }
}
