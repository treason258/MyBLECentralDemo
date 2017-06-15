package com.mjiayou.trecore;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mjiayou.trecorelib.util.HelloUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mTvInfo;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // findViewById
        mTvInfo = (TextView) findViewById(R.id.tv_info);

        // mTvInfo
        mTvInfo.append("\n" + HelloUtil.getHI() + "\n");
    }
}
