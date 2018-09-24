package com.mjiayou.trecorelib.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘显示隐藏操作
 */
public class KeyBoardUtils {

    /**
     * 显示
     */
    public static void show(Context context, View view) {
        if (context != null && view != null) {
            view.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, 0);
            }
        }
    }

    /**
     * 隐藏
     */
    public static void hide(Context context, View view) {
        if (context != null && view != null) {
            if (view.hasFocus()) {
                view.clearFocus();
            }
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 开关
     */
    public static void toggle(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(0, 0);
            }
        }
    }
}
