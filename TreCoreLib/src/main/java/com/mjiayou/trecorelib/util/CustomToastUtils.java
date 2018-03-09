package com.mjiayou.trecorelib.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.helper.TCHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * CustomToastUtils
 */
public class CustomToastUtils {

    private static final String TAG = ToastUtils.class.getSimpleName();

    private static Toast mToastCustom = null;
    private static Timer timer4Show;
    private static Timer timer4Cancel;

    /**
     * Toast显示指定时间
     */
    public static void showInDuration(String text, int duration) {
        Context context = TCApp.get();
        if (context == null) {
            LogUtils.e(TAG, TCHelper.ERROR_CONTEXT_NULL);
            return;
        }

        // view
        View view = LayoutInflater.from(context).inflate(R.layout.tc_layout_custom_toast, null);
        TextView tvText = (TextView) view.findViewById(R.id.tv_text);
        tvText.setText(text);

        // mToastCustom
        if (mToastCustom == null) {
            mToastCustom = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }
        mToastCustom.setView(view);
        mToastCustom.setGravity(Gravity.CENTER, 0, 0);

        // timer4Show
        if (timer4Show != null) {
            timer4Show.cancel();
            timer4Show = null;
        }
        timer4Show = new Timer();
        timer4Show.schedule(new TimerTask() {
            @Override
            public void run() {
                mToastCustom.show();
            }
        }, 0, 3000);

        // timer4Cancel
        if (timer4Cancel != null) {
            timer4Cancel.cancel();
            timer4Cancel = null;
        }
        timer4Cancel = new Timer();
        timer4Cancel.schedule(new TimerTask() {
            @Override
            public void run() {
                mToastCustom.cancel();
                timer4Show.cancel();
            }
        }, duration);
    }

    private static View mView;
    private static TextView mTvText;
    private static WindowManager mWindowManager;
    private static WindowManager.LayoutParams mLayoutParams;
    private static boolean mShow = false;
    private static Timer timer4Cancel4WindowManager;

    /**
     * Toast显示指定时间
     */
    public static void showInDurationByWindowManager(String text, int duration) {
        // context
        Context context = TCApp.get();
        if (context == null) {
            LogUtils.e(TAG, TCHelper.ERROR_CONTEXT_NULL);
            return;
        }

        // mView
        if (mView == null || mTvText == null) {
            mView = LayoutInflater.from(context).inflate(R.layout.tc_layout_custom_toast, null);
            mTvText = (TextView) mView.findViewById(R.id.tv_text);
        }
        mTvText.setText(text);

        // mLayoutParams
        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.format = PixelFormat.TRANSLUCENT;
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.gravity = Gravity.CENTER;
        }

        // mWindowManager
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mShow) {
            try {
                mWindowManager.removeView(mView);
                mShow = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            mWindowManager.addView(mView, mLayoutParams);
            mShow = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // timer4Cancel4WindowManager
        if (timer4Cancel4WindowManager != null) {
            timer4Cancel4WindowManager.cancel();
            timer4Cancel4WindowManager = null;
        }
        timer4Cancel4WindowManager = new Timer();
        timer4Cancel4WindowManager.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    mWindowManager.removeView(mView);
                    mShow = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, duration);
    }

    /**
     * 显示Toast
     */
    public static void showGlobalToast() {
        // context
        Context context = TCApp.get();
        if (context == null) {
            LogUtils.e(TAG, TCHelper.ERROR_CONTEXT_NULL);
            return;
        }

        // mView
        if (mView == null || mTvText == null) {
            mView = LayoutInflater.from(context).inflate(R.layout.tc_layout_custom_toast, null);
            mTvText = (TextView) mView.findViewById(R.id.tv_text);
        }
        mTvText.setText("WINDOW_SERVICE");

        // mLayoutParams
        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.format = PixelFormat.TRANSLUCENT;
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.gravity = Gravity.CENTER;
        }

        // mWindowManager
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mShow) {
            try {
                mWindowManager.removeView(mView);
                mShow = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            mWindowManager.addView(mView, mLayoutParams);
            mShow = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 移除Toast
     */
    public static void hideGlobalToast() {
        // context
        Context context = TCApp.get();
        if (context == null) {
            LogUtils.e(TAG, TCHelper.ERROR_CONTEXT_NULL);
            return;
        }

        // mWindowManager
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mShow) {
            try {
                mWindowManager.removeView(mView);
                mShow = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
