package com.mjiayou.testmoduleb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.TCBindPath;
import com.mjiayou.treannotation.TCRouterName;
import com.mjiayou.treannotation.TCRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@TCBindPath(TCRouterName.TEST_MODULE_B_ACTIVITY)
public class TestModuleBActivity extends AppCompatActivity {

    private TextView tvTestModuleB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module_b);

        tvTestModuleB = (TextView) findViewById(R.id.tvTestModuleB);
        tvTestModuleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.get().startActivity(TestModuleBActivity.this, TCRouterName.TEST_MODULE_C_ACTIVITY, null);
            }
        });
    }
}
