package com.mjiayou.trecore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mjiayou.trecore.test.WebViewActivity;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.util.AppUtils;
import com.mjiayou.trecorelib.util.HelloUtils;

/**
 * MainActivity
 */
public class MainActivity extends TCActivity {

    private Button mBtnTest;
    private TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findViewById
        mBtnTest = (Button) findViewById(R.id.btn_test);
        mTvInfo = (TextView) findViewById(R.id.tv_info);

        // mTvInfo
        mTvInfo.append("\n");
        mTvInfo.append(HelloUtils.getHI());
        mTvInfo.append("\n");
        mTvInfo.append(AppUtils.getAppInfoDetail(mContext));
        mTvInfo.append("\n");

        // mBtnTest
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, WebViewActivity.class));
            }
        });
    }
}
