package com.mjiayou.trecorelib.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mjiayou.trecorelib.util.LogUtils;

/**
 * TCService
 */
public class TCService extends Service {

    // TAG
    protected final String TAG = this.getClass().getSimpleName();

    // var
    protected Context mContext;
    protected Intent mIntent;

    @Override
    public void onCreate() {
        LogUtils.printLifeRecycle(TAG, "onCreate");
        super.onCreate();

        // var
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.printLifeRecycle(TAG, "onStartCommand | intent.getAction() -> " + intent.getAction() + " | flags -> " + flags + " | startId -> " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.printLifeRecycle(TAG, "onBind | intent.getAction() -> " + intent.getAction());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.printLifeRecycle(TAG, "onUnbind | intent.getAction() -> " + intent.getAction());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.printLifeRecycle(TAG, "onDestroy");
        super.onDestroy();
    }
}
