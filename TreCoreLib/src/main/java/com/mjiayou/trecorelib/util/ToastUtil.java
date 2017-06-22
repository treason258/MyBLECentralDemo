package com.mjiayou.trecorelib.util;

import android.content.Context;
import android.widget.Toast;

import com.mjiayou.trecorelib.helper.TCHelper;

/**
 * Toast显示封装
 * <p>
 * Created by treason on 16/5/14.
 */
public class ToastUtil {

    private static final String TAG = ToastUtil.class.getSimpleName();

    private static Toast mToast = null;

    /**
     * 显示 Toast - 默认 context
     */
    public static void show(String text, int duration) {
        showToast(text, duration);
    }

    /**
     * 显示 Toast - 默认 LENGTH_SHORT - 默认 context
     */
    public static void show(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 Toast - core
     */
    private static void showToast(String text, int duration) {
        Context context = TCHelper.getContext();
        if (context == null) {
            LogUtil.e(TAG, TCHelper.ERROR_CONTEXT_NULL);
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
        LogUtil.i(TAG, text);
    }
}
