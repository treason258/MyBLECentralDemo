package com.mjiayou.testmodulec;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.TCBindPath;
import com.mjiayou.treannotation.TCRouterName;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@TCBindPath(TCRouterName.TEST_MODULE_C_ACTIVITY)
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
