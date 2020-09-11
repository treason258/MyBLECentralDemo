package com.mjiayou.trecore.test.router;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mjiayou.myannotation.TCBindPath;
import com.mjiayou.trecore.R;
import com.mjiayou.treannotation.TCRouterName;
import com.mjiayou.treannotation.TCRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@TCBindPath(TCRouterName.ROUTER_ACTIVITY)
public class RouterActivity extends AppCompatActivity {

    private TextView tvRouter;

//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_router;
//    }
//
//    @Override
//    protected void afterOnCreate(Bundle savedInstanceState) {
//        getTitleBar().setTitle("RouterActivity");
//
//        tvRouter = (TextView) findViewById(R.id.tvRouter);
//        tvRouter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TCRouter.get().startActivity(mContext, "TestModuleAActivity", null);
//            }
//        });
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router);

        tvRouter = (TextView) findViewById(R.id.tvRouter);
        tvRouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCRouter.get().startActivity(RouterActivity.this, TCRouterName.TEST_MODULE_A_ACTIVITY, null);
            }
        });
    }
}
