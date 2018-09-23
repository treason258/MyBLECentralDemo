package com.mjiayou.trecorelib.manager;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.util.ViewUtils;

/**
 * Created by treason on 2016/12/7.
 */
public class StatusViewManager {

    // 正在加载
    private View mViewLoading;
    private AnimationDrawable mAnimLoading;

    // 异常页面
    private View mViewNoNetwork;
    private ImageView mIvHint;
    private TextView mTvText;
    private TextView mTvButton;

    public StatusViewManager(ViewGroup container, LayoutInflater inflater) {
        // findViewById
        // 正在加载
        mViewLoading = inflater.inflate(R.layout.tc_layout_status_loading, container, false);
        mAnimLoading = (AnimationDrawable) mViewLoading.findViewById(R.id.ivAnim).getBackground();
        // 异常页面
        mViewNoNetwork = inflater.inflate(R.layout.tc_layout_status_empty, container, false);
        mIvHint = (ImageView) mViewNoNetwork.findViewById(R.id.iv_hint);
        mTvText = (TextView) mViewNoNetwork.findViewById(R.id.tv_message);
        mTvButton = (TextView) mViewNoNetwork.findViewById(R.id.tv_button);

        // 添加
        container.addView(mViewNoNetwork);
        container.addView(mViewLoading);

        // mViewLoading
        mViewLoading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        // 默认隐藏
        ViewUtils.setVisibility(mViewLoading, false);
        ViewUtils.setVisibility(mViewNoNetwork, false);
    }

    /**
     * 正在加载
     */
    public void onLoading() {
        ViewUtils.setVisibility(mViewLoading, true);
        ViewUtils.setVisibility(mViewNoNetwork, false);
        mAnimLoading.start();
    }

    /**
     * 加载成功
     */
    public void onSuccess() {
        ViewUtils.setVisibility(mViewLoading, false);
        ViewUtils.setVisibility(mViewNoNetwork, false);
        mAnimLoading.stop();
    }

    /**
     * 加载失败
     */
    public void onFailure() {
        ViewUtils.setVisibility(mViewLoading, false);
        ViewUtils.setVisibility(mViewNoNetwork, true);
        mAnimLoading.stop();

        ViewUtils.setVisibility(mTvButton, false);
    }

    public void onFailure(View.OnClickListener listener) {
        ViewUtils.setVisibility(mViewLoading, false);
        ViewUtils.setVisibility(mViewNoNetwork, true);
        mAnimLoading.stop();

        if (listener == null) {
            ViewUtils.setVisibility(mTvButton, false);
        } else {
            ViewUtils.setVisibility(mTvButton, true);
            ViewUtils.setOnClickListener(mTvButton, listener);
        }
    }

    public void onFailure(String message, View.OnClickListener listener) {
        ViewUtils.setVisibility(mViewLoading, false);
        ViewUtils.setVisibility(mViewNoNetwork, true);
        mAnimLoading.stop();

        mTvText.setText(message);

        if (listener == null) {
            ViewUtils.setVisibility(mTvButton, false);
        } else {
            ViewUtils.setVisibility(mTvButton, true);
            ViewUtils.setOnClickListener(mTvButton, listener);
        }
    }
}
