package com.mjiayou.trecore;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjiayou.trecore.test.TestActivity;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.bean.entity.TCMenu;
import com.mjiayou.trecorelib.helper.UmengHelper;
import com.mjiayou.trecorelib.manager.ActivityManager;
import com.mjiayou.trecorelib.manager.CrashHandler;
import com.mjiayou.trecorelib.util.AppUtils;
import com.mjiayou.trecorelib.util.HelloUtils;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.MenuUtil;
import com.mjiayou.trecorelib.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * MainActivity
 */
public class MainActivity extends TCActivity {

    @InjectView(R.id.layout_menu_container)
    LinearLayout mLayoutMenuContainer;
    @InjectView(R.id.tv_info)
    TextView mTvInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.tc_activity_menu;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // 初始化异常捕获
        CrashHandler.get().init(mActivity, new CrashHandler.OnCaughtExceptionListener() {
            @Override
            public boolean onCaughtException(Thread thread, Throwable throwable) {
                UmengHelper.reportError(TCApp.get(), throwable);
                return false;
            }
        });

        // mTitleBar
        getTitleBar().setTitleOnly(getString(R.string.app_name));
        getTitleBar().addRightTextView("TEST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestActivity.open(mContext);
            }
        });

        // mLayoutMenuContainer
        MenuUtil.setMenus(mContext, mLayoutMenuContainer, getMenus());

        // mTvInfo
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append(HelloUtils.getHI());
        builder.append("\n");
        builder.append(AppUtils.getAppInfoDetail(mContext));
        builder.append("\n");
        MenuUtil.setInfo(mTvInfo, builder.toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ActivityManager.get().pressAgainToExit(mActivity, keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * getMenus
     */
    public List<TCMenu> getMenus() {
        List<TCMenu> tcMenus = new ArrayList<>();
        tcMenus.add(new TCMenu("LOG AND TOAST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("LOG AND TOAST TEST");
                LogUtils.i("LOG AND TOAST TEST");
            }
        }));
        return tcMenus;
    }
}
