package com.mjiayou.trecore.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.bean.entity.TCMenu;
import com.mjiayou.trecorelib.dialog.DialogHelper;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * WebViewActivity
 * <p>
 * Created by treason on 2017/9/25.
 */
public class WebViewActivity extends TCActivity {

    private WebView mWebView;
    private Button mBtnMenu;

    private String mUrl;

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
        mBtnMenu = (Button) findViewById(R.id.btn_menu);

        // mWebView
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "mjiayou");
        // 同步cookie
        addCookie();
        // loadUrl
        mWebView.loadUrl("https://m.cnblogs.com/");

        // mBtnMenu
        mBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> urls = new ArrayList<>();
                urls.add("file:///android_asset/javascript.html");
                urls.add("https://www.baidu.com/");
                urls.add("https://m.cnbeta.com/");
                urls.add("https://m.cnblogs.com/");
                urls.add("https://m.cnblogs.com/blog/");

                List<TCMenu> tcMenus = new ArrayList<>();
                for (int i = 0; i < urls.size(); i++) {
                    final int finalI = i;
                    tcMenus.add(new TCMenu("load " + urls.get(i), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addCookie();
                            mWebView.loadUrl(urls.get(finalI));
                        }
                    }));
                }
                tcMenus.add(new TCMenu("javascript.html -> callJS", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 约定Android调用JS的方法
                        String param = "From Android";
                        mWebView.loadUrl("javascript:callJS('" + param + "')");
                    }
                }));
                DialogHelper.createTCAlertMenuDialog(mContext, "菜单", null, true, tcMenus).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
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

    /**
     * 同步Cookie
     */
    private void addCookie() {
        List<String> cookies = new ArrayList<>();
        cookies.add("platform=android");
        cookies.add("version=1.0");

        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        for (String cookie : cookies) {
            /*
             *
             */
            cookieManager.setCookie("https://m.cnblogs.com/blog/", cookie);
            CookieSyncManager.getInstance().sync();
        }
    }
}
