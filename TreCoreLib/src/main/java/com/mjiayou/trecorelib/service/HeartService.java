package com.mjiayou.trecorelib.service;

import com.mjiayou.trecorelib.base.TCService;
import com.mjiayou.trecorelib.util.DeviceUtils;
import com.mjiayou.trecorelib.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * HeartService
 */
public class HeartService extends TCService {

    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        startHeartBeat();
    }

    @Override
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        super.onDestroy();
    }

    /**
     * 心跳，3秒执行一次
     */
    private void startHeartBeat() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtils.i(TAG, "startHeartBeat | currentTimeMillis -> " + DeviceUtils.getSystemTime());
            }
        };
        mTimer = new Timer();
        mTimer.schedule(timerTask, 0, 3000);
    }
}
