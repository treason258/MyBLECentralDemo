package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.view.View;

import com.mjiayou.trecore.R;
import com.mjiayou.trecore.databinding.DemoDataBinding;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.ToastUtils;

/**
 * DataBindingActivity
 */
public class DataBindingActivity extends TCActivity<DemoDataBinding> {

    @Override
    protected boolean checkUseDataBinding() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_binding;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // include
        mBinding.includeHeader.tvInfoInclude.setText("mTvInfoInclude");
        mBinding.includeHeader.btnSubmitInclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("mBtnSubmitInclude");
            }
        });

        mBinding.tvInfo.setText("mTvInfo");
        mBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("mBtnSubmit");
            }
        });

    }
}
