package com.mjiayou.trecorelib.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.bean.TCResponse;
import com.mjiayou.trecorelib.dialog.TCLoadingDialog;
import com.mjiayou.trecorelib.http.volley.RequestAdapter;
import com.mjiayou.trecorelib.manager.ActivityManager;
import com.mjiayou.trecorelib.manager.StatusViewManager;
import com.mjiayou.trecorelib.util.ConvertUtils;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.StatusBarUtils;
import com.mjiayou.trecorelib.widget.TitleBar;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * TCActivity
 */
public abstract class TCActivity extends AppCompatActivity implements RequestAdapter.DataRequest, RequestAdapter.DataResponse {

    // TAG
    protected final String TAG = this.getClass().getSimpleName();

    // var
    protected Activity mActivity;
    protected Context mContext;
    protected Intent mIntent;
//    protected VB mBinding;

    // view
    private LinearLayout mLayoutRoot;
    private TitleBar mTitleBar;
    private FrameLayout mLayoutContainer;

    // 正在加载
    private Dialog mLoadingDialog;
    // 页面状态管理
    private StatusViewManager mStatusViewManager;
    // 网络请求
    private RequestAdapter mRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // beforeOnCreate
        beforeOnCreate(savedInstanceState);

        // onCreate
        super.onCreate(savedInstanceState);
        LogUtils.printLifeRecycle(TAG, "onCreate | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));

        // var
        mActivity = this;
        mContext = this;

        // setContentView
        View rootView = getLayoutInflater().inflate(R.layout.tc_activity_base, null, false);
        mLayoutRoot = (LinearLayout) rootView.findViewById(R.id.layout_root);
        mTitleBar = (TitleBar) rootView.findViewById(R.id.titlebar);
        mLayoutContainer = (FrameLayout) rootView.findViewById(R.id.layout_container);
        super.setContentView(rootView);

        // getLayoutId
        if (getLayoutId() == 0) {
            LogUtils.printLifeRecycle(TAG, "getLayoutId() == 0");
        } else {
            View containerView = getLayoutInflater().inflate(getLayoutId(), null, false);
            mLayoutContainer.addView(containerView);
            // 控件注解
//            if (checkUseDataBinding()) {
//                mBinding = DataBindingUtil.bind(containerView);
//            } else {
                ButterKnife.bind(this);
//            }
            LogUtils.printLifeRecycle(TAG, "getLayoutId() -> " + getLayoutId());
        }

        // 沉浸式状态栏
        StatusBarUtils.with(mActivity).init();
        StatusBarUtils.setAndroidNativeLightStatusBar(mActivity, false);

        // Activity压栈操作
        ActivityManager.get().addActivity(mActivity);

        // 是否隐藏标题栏
        if (checkHideTitleBar()) {
            mTitleBar.setVisible(false);
        } else {
            mTitleBar.setVisible(true);
        }

        // 是否根据传感器旋转屏幕
        if (checkRotateScreeBySensor()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // afterOnCreate
        afterOnCreate(savedInstanceState);
        LogUtils.printLifeRecycle(TAG, "afterOnCreate | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));
    }

    @Override
    protected void onRestart() {
        LogUtils.printLifeRecycle(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        LogUtils.printLifeRecycle(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtils.printLifeRecycle(TAG, "onResume");
        super.onResume();
        // 打印ActivityManager
        LogUtils.printActivityList(ActivityManager.get().getActivityList());
    }

    @Override
    protected void onPause() {
        LogUtils.printLifeRecycle(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtils.printLifeRecycle(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.printLifeRecycle(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.printLifeRecycle(TAG, "onPostCreate | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        LogUtils.printLifeRecycle(TAG, "onPostResume");
        super.onPostResume();
    }

    @Override
    public void setContentView(int layoutResID) {
        LogUtils.printLifeRecycle(TAG, "setContentView | layoutResID -> " + layoutResID);
        super.setContentView(layoutResID);
    }

    @Nullable
    @Override
    public View findViewById(@IdRes int id) {
        LogUtils.printLifeRecycle(TAG, "findViewById | id -> " + id);
        return super.findViewById(id);
    }

    @Override
    public void finish() {
        LogUtils.printLifeRecycle(TAG, "finish");
        super.finish();
        // Activity管理，出栈操作
        ActivityManager.get().removeActivity(mActivity);
    }

    @Override
    public void startActivity(Intent intent) {
        LogUtils.printLifeRecycle(TAG, "startActivity | intent -> " + intent.toString());
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        LogUtils.printLifeRecycle(TAG, "startActivityForResult | intent -> " + intent.toString() + " | requestCode -> " + requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onBackPressed() {
        LogUtils.printLifeRecycle(TAG, "onBackPressed");
        super.onBackPressed();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.printLifeRecycle(TAG, "onKeyDown | keyCode -> " + keyCode + " | event -> " + event.toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.printLifeRecycle(TAG, "onTouchEvent | event -> " + event.toString());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.printLifeRecycle(TAG, "dispatchTouchEvent | event -> " + ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        LogUtils.printLifeRecycle(TAG, "onTitleChanged | title —> " + title + " | color -> " + color);
        super.onTitleChanged(title, color);
    }

    @Override
    public void setTheme(@StyleRes int resid) {
        LogUtils.printLifeRecycle(TAG, "setTheme | resid -> " + resid);
        super.setTheme(resid);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.printLifeRecycle(TAG, "onNewIntent | intent -> " + intent.toString());
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtils.printLifeRecycle(TAG, "onSaveInstanceState | outState -> " + ConvertUtils.parseString(outState));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LogUtils.printLifeRecycle(TAG, "onRestoreInstanceState | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.printLifeRecycle(TAG, "onConfigurationChanged | newConfig -> " + newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        LogUtils.printLifeRecycle(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtils.printLifeRecycle(TAG, "onTrimMemory | level -> " + level);
        // 因为onTrimMemory() 是在API 14 里添加的，你可以在老版本里使用onLowMemory() 回调，大致跟TRIM_MEMORY_COMPLETE事件相同。
        switch (level) {
            case TRIM_MEMORY_RUNNING_MODERATE:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 你的应用正在运行，并且不会被杀死，但设备已经处于低内存状态，并且开始杀死LRU缓存里的内存。");
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 你的应用正在运行，并且不会被杀死，但设备处于内存更低的状态，所以你应该释放无用资源以提高系统性能（直接影响app性能）");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 你的应用还在运行，但系统已经杀死了LRU缓存里的大多数进程，所以你应该在此时释放所有非关键的资源。如果系统无法回收足够的内存，它会清理掉所有LRU缓存，并且开始杀死之前优先保持的进程，像那些运行着service的。同时，当你的app进程当前被缓存，你可能会从onTrimMemory() 收到下面的几种level。");
                break;
            case TRIM_MEMORY_BACKGROUND:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 系统运行在低内存状态，并且你的进程已经接近LRU列表的顶端（即将被清理）。虽然你的app进程还没有很高的被杀死风险，系统可能已经清理LRU里的进程，你应该释放那些容易被恢复的资源，如此可以让你的进程留在缓存里，并且当用户回到app时快速恢复。");
                break;
            case TRIM_MEMORY_MODERATE:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 系统运行在低内存状态，你的进程在LRU列表中间附近。如果系统变得内存紧张，可能会导致你的进程被杀死。");
                break;
            case TRIM_MEMORY_COMPLETE:
                LogUtils.printLifeRecycle(TAG, "onTrimMemory -> 系统运行在低内存状态，如果系统没有恢复内存，你的进程是首先被杀死的进程之一。你应该释放所有不重要的资源来恢复你的app状态。");
                break;
        }
        super.onTrimMemory(level);
    }

    // ******************************** lifeCycle ********************************

    /**
     * 是否使用DataBinding功能
     */
    protected boolean checkUseDataBinding() {
        LogUtils.printLifeRecycle(TAG, "checkUseDataBinding");
        return false;
    }

    /**
     * 是否隐藏标题栏
     */
    protected boolean checkHideTitleBar() {
        LogUtils.printLifeRecycle(TAG, "checkHideTitleBar");
        return false;
    }

    /**
     * 是否根据传感器旋转屏幕
     */
    protected boolean checkRotateScreeBySensor() {
        LogUtils.printLifeRecycle(TAG, "checkRotateScreeBySensor");
        return false;
    }

    /**
     * 在 onCreate 之前执行
     */
    protected void beforeOnCreate(Bundle savedInstanceState) {
        LogUtils.printLifeRecycle(TAG, "beforeOnCreate | savedInstanceState -> " + ConvertUtils.parseString(savedInstanceState));
    }

    /**
     * 获取activity对应的layout资源文件
     */
    protected abstract int getLayoutId();


    /**
     * 在 onCreate 之后执行
     */
    protected abstract void afterOnCreate(Bundle savedInstanceState);

    // ******************************** RequestAdapter.DataRequest ********************************

    @Override
    public void initView() {
        LogUtils.printLifeRecycle(TAG, "initView");
    }

    @Override
    public void getData(int pageNumber) {
        LogUtils.printLifeRecycle(TAG, "getData");
    }

    @Override
    public void refreshData() {
        LogUtils.printLifeRecycle(TAG, "refreshData");
    }

    @Override
    public void loadMoreData() {
        LogUtils.printLifeRecycle(TAG, "loadMoreData");
    }

    @Override
    public void submitData() {
        LogUtils.printLifeRecycle(TAG, "submitData");
    }

    // ******************************** RequestAdapter.DataResponse ********************************

    @Override
    public void callback(Message msg) {
        LogUtils.printLifeRecycle(TAG, "callback");
    }

    @Override
    public void refreshView(TCResponse response) {
        LogUtils.printLifeRecycle(TAG, "refreshView");
    }

    // ******************************** showLoading ********************************

    /**
     * 显示、隐藏正在加载对话框
     */
    public void showLoading(boolean show) {
        LogUtils.printLifeRecycle(TAG, "showLoading | show -> " + show);
        try {
            // 需要显示时，如果页面已经finish，则return
            if (show && isFinishing()) {
                return;
            }

            // 需要显示时，如果正在显示，则return
            if (show && (mLoadingDialog != null && mLoadingDialog.isShowing())) {
                return;
            }

            // 需要隐藏时，如果mLoadingDialog不存在或没有在显示，则return
            if (!show && (mLoadingDialog == null || !mLoadingDialog.isShowing())) {
                return;
            }

            // 如果mLoadingDialog不存在，则创建
            if (mLoadingDialog == null) {
                mLoadingDialog = TCLoadingDialog.createDialog(mContext);
                mLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (mRequestAdapter != null) {
                            mRequestAdapter.cancelAll();
                        }
                    }
                });
            }

            // 显示/隐藏
            if (show) {
                mLoadingDialog.show();
            } else {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    /**
     * 更新信息
     */
    public void updateLoading(String message) {
        LogUtils.printLifeRecycle(TAG, "updateLoading | message -> " + message);
        if (null != mLoadingDialog && mLoadingDialog.isShowing() && !TextUtils.isEmpty(message)) {
            if (mLoadingDialog instanceof TCLoadingDialog) {
                ((TCLoadingDialog) mLoadingDialog).updateMessage(message);
            }
        }
        LogUtils.i(TAG, "updateLoading -> " + message);
    }

    // ******************************** getTitleBar ********************************

    /**
     * 返回 TitleBar 对象
     */
    public TitleBar getTitleBar() {
        LogUtils.printLifeRecycle(TAG, "getTitleBar");
        return mTitleBar;
    }

    // ******************************** getStatusViewManager ********************************

    /**
     * 返回 StatusViewManager 对象
     */
    public StatusViewManager getStatusViewManager() {
        LogUtils.printLifeRecycle(TAG, "getStatusViewManager");
        if (mStatusViewManager == null) {
            mStatusViewManager = new StatusViewManager(mLayoutContainer, getLayoutInflater());
        }
        return mStatusViewManager;
    }

    // ******************************** getRequestAdapter ********************************

    /**
     * 返回 RequestAdapter 对象
     */
    public RequestAdapter getRequestAdapter() {
        LogUtils.printLifeRecycle(TAG, "getRequestAdapter");
        if (mRequestAdapter == null) {
            mRequestAdapter = new RequestAdapter(mContext, this);
        }
        return mRequestAdapter;
    }

    // ******************************** project ********************************
}