package com.mjiayou.trecorelib.base;

import android.app.Fragment;
import android.os.Bundle;

import com.mjiayou.trecorelib.util.ConvertUtils;
import com.mjiayou.trecorelib.util.LogUtils;

import androidx.annotation.Nullable;

/**
 * TCFragment
 */
public class TCFragment extends Fragment {

    // TAG
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.printLifeRecycle(TAG, "onCreate | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtils.printLifeRecycle(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtils.printLifeRecycle(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        LogUtils.printLifeRecycle(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtils.printLifeRecycle(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        LogUtils.printLifeRecycle(TAG, "onDestroy");
        super.onDestroy();
    }
}
