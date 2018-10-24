package com.mjiayou.trecorelib.http.impl;

import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;
import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.common.Configs;
import com.mjiayou.trecorelib.helper.VolleyHelper;
import com.mjiayou.trecorelib.http.callback.BaseCallback;
import com.mjiayou.trecorelib.http.RequestEntity;
import com.mjiayou.trecorelib.http.RequestMethod;
import com.mjiayou.trecorelib.http.RequestSender;
import com.mjiayou.trecorelib.util.ConvertUtils;
import com.mjiayou.trecorelib.util.ToastUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by xin on 18/10/16.
 */

public class VolleyImpl extends RequestSender {

  @Override
  public void send(RequestEntity requestEntity, BaseCallback baseCallback) {
    logRequest(TAG, requestEntity);

    if (baseCallback != null) {
      baseCallback.onStart();
    }
    Request request = new TCRequest(requestEntity, baseCallback);
    request.setShouldCache(true);
    request.setRetryPolicy(new DefaultRetryPolicy(Configs.DEFAULT_TIMEOUT_MS, Configs.DEFAULT_MAX_RETRIES, Configs.DEFAULT_BACKOFF_MULT)); // 设置超时时间、重连次数
    VolleyHelper.getRequestQueue().add(request);
  }

  @Override
  public void downloadFile(String url, FileCallBack fileCallBack) {

  }

  /**
   * TCRequest
   */
  private class TCRequest extends Request<String> {

    private RequestEntity mRequestEntity;
    private BaseCallback mBaseCallback;

    private TCRequest(RequestEntity requestEntity, BaseCallback baseCallback) {
      super(RequestMethod.getVolleyMethod(requestEntity.getMethod()), requestEntity.getUrl(), new TCErrorListener(baseCallback));
      this.mRequestEntity = requestEntity;
      this.mBaseCallback = baseCallback;
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
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
      try {
        String responseString;
        String encoding = networkResponse.headers.get("Content-Encoding");
        if (!TextUtils.isEmpty(encoding) && encoding.equals("gzip")) {
          responseString = ConvertUtils.parseString(networkResponse.data);
        } else {
          responseString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        }
        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(networkResponse));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return Response.error(new ParseError(e));
      } catch (JsonSyntaxException e) {
        e.printStackTrace();
        return Response.error(new ParseError(e));
      }
    }

    @Override protected void deliverResponse(String responseData) {
      logResponse(TAG, mRequestEntity, responseData);

      if (mBaseCallback != null) {
        mBaseCallback.onResult(responseData);
      }
    }
  }

  /**
   * 异常处理
   */
  private class TCErrorListener implements Response.ErrorListener {

    private BaseCallback mBaseCallback;

    private TCErrorListener(BaseCallback baseCallback) {
      this.mBaseCallback = baseCallback;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
      if (mBaseCallback != null) {
        mBaseCallback.onException(error);
      }
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
