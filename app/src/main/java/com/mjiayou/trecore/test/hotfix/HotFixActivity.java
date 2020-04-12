package com.mjiayou.trecore.test.hotfix;

import android.os.Bundle;
import android.view.View;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;

public class HotFixActivity extends TCActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        getTitleBar().setTitle("HotFixActivity");
    }

    public void crash(View view) {
        CrashClass.crash();
    }

    public void fix(View view) {
        HotFixUtils.loadDex(mContext);
    }
}
