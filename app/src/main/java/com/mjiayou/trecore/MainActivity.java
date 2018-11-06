package com.mjiayou.trecore;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.trecore.test.TestActivity;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.manager.ActivityManager;
import com.mjiayou.trecorelib.manager.CrashHandler;

import butterknife.Bind;

/**
 * MainActivity
 */
public class MainActivity extends TCActivity {

    @Bind(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // 初始化异常捕获
        CrashHandler.get().init(mActivity, new CrashHandler.OnCaughtExceptionListener() {
            @Override
            public boolean onCaughtException(Thread thread, Throwable throwable) {
                return false;
            }
        });

        // mTitleBar
        getTitleBar().setTitleOnly(getString(R.string.app_name));
        getTitleBar().addRightTextView("TEST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.open(mContext);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ActivityManager.get().pressAgainToExit(mActivity, keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
