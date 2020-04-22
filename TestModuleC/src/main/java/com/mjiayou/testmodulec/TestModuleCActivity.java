package com.mjiayou.testmodulec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.BindPath;
import com.mjiayou.treannotation.RouterName;

@BindPath(RouterName.TEST_MODULE_C_ACTIVITY)
public class TestModuleCActivity extends AppCompatActivity {

    private TextView tvTestModuleC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module_c);

        tvTestModuleC = (TextView) findViewById(R.id.tvTestModuleC);
        tvTestModuleC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
