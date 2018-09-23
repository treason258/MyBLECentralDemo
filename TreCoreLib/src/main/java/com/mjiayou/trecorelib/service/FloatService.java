package com.mjiayou.trecorelib.service;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.base.TCService;
import com.mjiayou.trecorelib.util.ServiceUtils;
import com.mjiayou.trecorelib.util.ToastUtils;

/**
 * Created by treason on 2017/2/7.
 */
public class FloatService extends TCService {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private TextView mTvText;

    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;

    @Override
    public void onCreate() {
        super.onCreate();

        addView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        removeView();
    }

    /**
     * 显示悬浮窗口
     */
    private void addView() {
        // mView
        if (mView == null || mTvText == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.tc_service_float, null);
            mTvText = (TextView) mView.findViewById(R.id.tv_text);
        }
        mTvText.setText(TAG);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("onClick | isAppRunningBackground -> " + ServiceUtils.isAppRunningBackground(mContext));
            }
        });
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        mStartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        mEndY = (int) event.getRawY();
                        if (needIntercept()) {
                            // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                            mLayoutParams.x = (int) event.getRawX() - mView.getMeasuredWidth() / 2;
                            mLayoutParams.y = (int) event.getRawY() - mView.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(mView, mLayoutParams);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // mLayoutParams
        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.format = PixelFormat.TRANSLUCENT; // 期望的位图格式，PixelFormat类型。更多format: https://developer.android.com/reference/android/graphics/PixelFormat.html
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST; // 设置窗体显示类型。更多type: https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_TOAST
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 更多falgs: https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        }

        // addViewToWindow
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        try {
            mWindowManager.addView(mView, mLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除悬浮窗口
     */
    private void removeView() {
        if (mWindowManager != null && mView != null) {
            try {
                mWindowManager.removeView(mView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否拦截
     *
     * @return true-拦截;false-不拦截.
     */
    private boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30) {
            return true;
        }
        return false;
    }
}
