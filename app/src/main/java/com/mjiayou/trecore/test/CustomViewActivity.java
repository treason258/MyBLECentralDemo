package com.mjiayou.trecore.test;

import android.os.Bundle;

import com.mjiayou.trecore.R;
import com.mjiayou.trecore.widget.UxinBounceView;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.ToastUtils;

/**
 * CustomViewActivity
 */
public class CustomViewActivity extends TCActivity {

    private UxinBounceView uxinBounceView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // mTitleBar
        getTitleBar().setTitle(TAG);

        // uxinBounceView
        uxinBounceView = (UxinBounceView) findViewById(R.id.uxinBounceView);
        uxinBounceView.setOnViewClickListener(new UxinBounceView.OnViewClickListener() {
            @Override
            public void onclick() {
                ToastUtils.show("uxinBounceView onClick");
            }
        });
        uxinBounceView.setTotalNum("1234567");
    }
}
