package com.mjiayou.trecorelib.http;

import android.text.TextUtils;

import com.android.volley.Request;
import com.mjiayou.trecorelib.common.Caches;
import com.mjiayou.trecorelib.common.Params;
import com.mjiayou.trecorelib.helper.GsonHelper;
import com.mjiayou.trecorelib.util.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * 网络请求参数实体类--OK
 */
public class RequestEntity implements Serializable {

    private static final long serialVersionUID = -2329339308310833689L;

    private boolean enableCookie = false;
    private RequestMethod method;
    private int methodVolley = 0;

    private String url = "";
    private Map<String, String> headers = new TreeMap<>();
    private Map<String, String> params = new TreeMap<>();
    private Map<String, File> files = new TreeMap<>();
    private String content = "";
    private String filePath = "";
    private JSONObject jsonObject = new JSONObject();

    /**
     * 构造函数
     */
    public RequestEntity(String url) {
        this.enableCookie = false; // 默认不保存cookie
        this.method = RequestMethod.POST; // 默认post请求
        this.methodVolley = Request.Method.POST; // 默认post请求
        this.url = url;

        // 公共参数-headers
        // headers.put("Accept-Encoding", "gzip");
        // headers.put("Accept", "application/json");
        // headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"); // application/json; charset=UTF-8 | text/html; charset=UTF-8
        headers.put(Params.KEY_PLATFORM, Params.VALUE_PLATFORM);

        // 公共参数-params
        params.put(Params.KEY_TIME, String.valueOf(System.currentTimeMillis()));
        params.put(Params.KEY_VERSION_CODE, String.valueOf(Caches.get().getVersionCode()));
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    // ******************************** Getter and Setter ********************************

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod methodCode) {
        this.method = methodCode;
    }

    public int getMethodVolley() {
        return methodVolley;
    }

    public void setMethodVolley(int methodVolley) {
        this.methodVolley = methodVolley;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //    public String getRequestBody() {
//        return requestBody;
//    }

//    public void setRequestBody(String requestBody) {
//        this.requestBody = requestBody;
//    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    public boolean isEnableCookie() {
        return enableCookie;
    }

    public void setEnableCookie(boolean enableCookie) {
        this.enableCookie = enableCookie;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // ******************************** 自定义操作 ********************************

    public void addHeader(String key, String value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            headers.put(key, value);
        }
    }

    public void addParam(String key, String value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            params.put(key, value);
        }
    }

    public void addParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
    }

    public void addParams(Object object) {
        if (object != null) {
            try {
                Class<? extends Object> clazz = object.getClass();
                ArrayList<Field> fieldList = new ArrayList<>();
                do {
                    fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
                    clazz = clazz.getSuperclass();
                } while (clazz != Object.class);

                for (Field field : fieldList) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(object);
                    if (!Modifier.isStatic(field.getModifiers()) && field.getType() != String[].class) {
                        if (value == null) {
                            params.put(name.toLowerCase(), "");
                        } else {
                            params.put(name.toLowerCase(), String.valueOf(value));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                LogUtils.printStackTrace(e);
            } catch (IllegalArgumentException e) {
                LogUtils.printStackTrace(e);
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }
        }
    }

    public void addFile(String name, File file) {
        if (!TextUtils.isEmpty(name) && file != null) {
            files.put(name, file);
        }
    }

    public void setContent(Object object) {
        if (object != null) {
            this.content = GsonHelper.get().toJson(object);
        }
    }
}