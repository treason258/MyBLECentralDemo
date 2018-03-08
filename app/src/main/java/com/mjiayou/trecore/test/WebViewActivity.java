package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.ToastUtils;

/**
 * WebViewActivity
 * <p>
 * Created by treason on 2017/9/25.
 */
public class WebViewActivity extends TCActivity {

    private WebView mWebView;
    private Button mBtnCallJS;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // mTitleBar
        getTitleBar().setTitle(TAG);

        // findViewById
        mWebView = (WebView) findViewById(R.id.webview);
        mBtnCallJS = (Button) findViewById(R.id.btn_call_js);

        // mWebView
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "mjiayou");
        mWebView.loadUrl("file:///android_asset/javascript.html");

        // mBtnCallJS
        mBtnCallJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 约定Android调用JS的方法
                String param = "From Android";
                mWebView.loadUrl("javascript:callJS('" + param + "')");
            }
        });
    }

    /**
     * MyJavascriptInterface
     */
    private class MyJavascriptInterface {

        /**
         * 约定JS调用Android的方法
         */
        @JavascriptInterface
        public void callAndroid(final String param) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("callAndroid | param -> " + param);
                    ToastUtils.show("JS调用了Android的callAndroid(" + param + ")方法");
                }
            });
        }
    }

    /**
     * mWebViewClient
     */
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    };

    /**
     * mWebChromeClient
     */
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };
}
