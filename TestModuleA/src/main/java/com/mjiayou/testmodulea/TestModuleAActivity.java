package com.mjiayou.testmodulea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.BindPath;
import com.mjiayou.trerouter.RouterName;
import com.mjiayou.trerouter.TCRouter;

@BindPath(RouterName.TEST_MODULE_A_ACTIVITY)
public class TestModuleAActivity extends AppCompatActivity {

    private TextView tvTestModuleA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module_a);

        tvTestModuleA = (TextView) findViewById(R.id.tvTestModuleA);
        tvTestModuleA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.get().startActivity(TestModuleAActivity.this, RouterName.TEST_MODULE_B_ACTIVITY, null);
            }
        });
    }
}
