package com.mjiayou.trecorelib.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.util.LogUtils;

/**
 * TCDialog
 */
public class TCDialog extends Dialog {

    // TAG
    protected final String TAG = this.getClass().getSimpleName();

    protected final static double WIDTH_RATIO_DEFAULT = 0.85;
    protected final static double WIDTH_RATIO_BIG = 0.95;
    protected final static double WIDTH_RATIO_FULL = 1.0;
    protected final static double WIDTH_RATIO_HALF = 0.6;

    // var
    protected Context mContext;

    public TCDialog(Context context, int themeResId) {
        super(context, themeResId);
        LogUtils.printLifeRecycle(TAG, "onConstruct");

        // var
        mContext = context;

        // 窗口显示位置 - 默认位置居中
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    public TCDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.printLifeRecycle(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        LogUtils.printLifeRecycle(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        LogUtils.printLifeRecycle(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void dismiss() {
        LogUtils.printLifeRecycle(TAG, "dismiss");
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        LogUtils.printLifeRecycle(TAG, "onBackPressed");
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.printLifeRecycle(TAG, "onKeyDown | keyCode -> " + keyCode + " | event -> " + event.toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.printLifeRecycle(TAG, "onTouchEvent | event -> " + event.toString());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.printLifeRecycle(TAG, "dispatchTouchEvent | event -> " + ev.toString());
        return super.dispatchTouchEvent(ev);
    }
}
