package com.mjiayou.trecore.test.tcknife;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.TCBindView;
import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.ToastUtils;
import com.mjiayou.trerouter.TCKnife;

public class TCKnifeActivity extends TCActivity {

    @TCBindView(R.id.tvTCKnife1)
    public TextView tvTCKnife1;

    @TCBindView(R.id.tvTCKnife2)
    public TextView tvTCKnife2;

    @TCBindView(R.id.tvTCKnife3)
    public TextView tvTCKnife3;

    @TCBindView(R.id.tvTCKnife4)
    public TextView tvTCKnife4;

    @TCBindView(R.id.tvTCKnife5)
    public TextView tvTCKnife5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knife;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        TCKnife.bind(this);

        tvTCKnife1.setText("tvTCKnife1-Modify");
        tvTCKnife2.setText("tvTCKnife2-Modify");
        tvTCKnife3.setText("tvTCKnife3-Modify");
        tvTCKnife4.setText("tvTCKnife4-Modify");
        tvTCKnife5.setText("tvTCKnife5-Modify");

        tvTCKnife5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("I AM NOT NULL");
            }
        });
    }
}
