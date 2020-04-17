package com.mjiayou.trecore.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.service.FloatService;
import com.mjiayou.trecorelib.service.HeartService;
import com.mjiayou.trecorelib.util.ServiceUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ServiceActivity
 */
public class ServiceActivity extends TCActivity {

    @BindView(R.id.tv_info)
    TextView mTvInfo;
    @BindView(R.id.btn_start_heart_service)
    Button mBtnStartHeartService;
    @BindView(R.id.btn_start_float_service)
    Button mBtnStartFloatService;

    @OnClick({R.id.btn_start_heart_service, R.id.btn_start_float_service})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_heart_service:
                startHeartService();
                break;
            case R.id.btn_start_float_service:
                startFloatService();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // mTitleBar
        getTitleBar().setTitle(TAG);

        refreshInfo();
    }

    /**
     * 刷新文字提示信息
     */
    private void refreshInfo() {
        // mTvInfo
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HeartService 运行状态：");
        if (ServiceUtils.isServiceRunning(mContext, HeartService.class)) {
            stringBuilder.append("正在运行");
            mBtnStartHeartService.setText("点击停止 HeartService");
        } else {
            stringBuilder.append("已停止");
            mBtnStartHeartService.setText("点击开始 HeartService");
        }
        stringBuilder.append("\n");
        stringBuilder.append("FloatService 运行状态：");
        if (ServiceUtils.isServiceRunning(mContext, FloatService.class)) {
            stringBuilder.append("正在运行");
            mBtnStartFloatService.setText("点击停止 FloatService");
        } else {
            stringBuilder.append("已停止");
            mBtnStartFloatService.setText("点击开始 FloatService");
        }
        mTvInfo.setText(stringBuilder.toString());
    }

    /**
     * HeartService
     */
    private void startHeartService() {
        Intent intent = new Intent(mContext, HeartService.class);
        if (ServiceUtils.isServiceRunning(mContext, HeartService.class)) {
            stopService(intent);
        } else {
            startService(intent);
        }
        refreshInfo();
    }

    /**
     * FloatService
     */
    private void startFloatService() {
        Intent intent = new Intent(mContext, FloatService.class);
        if (ServiceUtils.isServiceRunning(mContext, FloatService.class)) {
            stopService(intent);
        } else {
            startService(intent);
        }
        refreshInfo();
    }
}
