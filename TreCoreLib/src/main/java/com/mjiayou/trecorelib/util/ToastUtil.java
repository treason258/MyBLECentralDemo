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
    private static final String ERROR_CONTEXT_NULL = "context == null";

    private static Toast mToast = null;

    /**
     * 显示 Toast
     */
    public static void show(Context context, String text, int duration) {
        showToast(context, text, duration);
    }

    /**
     * 显示 Toast - 默认 LENGTH_SHORT
     */
    public static void show(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 Toast - 默认 context
     */
    public static void show(String text, int duration) {
        if (TCHelper.mContext == null) {
            LogUtil.e(TAG, ERROR_CONTEXT_NULL);
        }
        showToast(TCHelper.mContext, text, duration);
    }

    /**
     * 显示 Toast - 默认 LENGTH_SHORT - 默认 context
     */
    public static void show(String text) {
        if (TCHelper.mContext == null) {
            LogUtil.e(TAG, ERROR_CONTEXT_NULL);
        }
        showToast(TCHelper.mContext, text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示 Toast - core
     */
    private static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
        LogUtil.i(TAG, text);
    }
}
