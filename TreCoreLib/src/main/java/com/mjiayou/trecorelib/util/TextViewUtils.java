package com.mjiayou.trecorelib.util;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by treason on 15-12-16.
 */
public class TextViewUtils {

    public static final String TAG = "TextViewUtil";

    /**
     * 显示Text
     */
    public static void setText(TextView textView, String text) {
        if (textView == null) {
            LogUtils.i("textView == null");
            return;
        }
        if (TextUtils.isEmpty(text)) {
            LogUtils.i("TextUtils.isEmpty(text)");
            return;
        }
        textView.setText(text);
    }

    /**
     * 处理符号转换
     */
    public static String dealWithQuot(String str) {
        if (str != null) {
            if (str.contains("&quot;")) {
                str = str.replace("&quot;", "\"");
            }
            if (str.contains("&ldquo;")) {
                str = str.replace("&ldquo;", "“");
            }
            if (str.contains("&rdquo;")) {
                str = str.replace("&rdquo;", "”");
            }
        }
        return str;
    }
}
