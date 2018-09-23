package com.mjiayou.trecorelib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mjiayou.trecorelib.R;
import com.mjiayou.trecorelib.base.TCDialog;
import com.mjiayou.trecorelib.common.Caches;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.ToastUtils;
import com.mjiayou.trecorelib.util.ViewUtils;

/**
 * Created by treason on 16/6/2.
 */
public class TCUserInfoDialog extends TCDialog {

    protected static final String TAG = "TCUserInfoDialog";
    private static TCUserInfoDialog mTCUserInfoDialog;

    // root
    private ViewGroup mViewRoot;
    // dialog
    private LinearLayout mLayoutDialog;
    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvOk;

    private static String mUserName = "";

    public TCUserInfoDialog(Context context, int themeResId) {
        super(context, themeResId);

        // 可返回按钮取消
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public TCUserInfoDialog(Context context) {
        this(context, R.style.tc_dialog_theme_default);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc_dialog_user_info);

        // findViewById
        // root
        mViewRoot = (ViewGroup) findViewById(R.id.view_root);
        // dialog
        mLayoutDialog = (LinearLayout) findViewById(R.id.layout_dialog);
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvOk = (TextView) findViewById(R.id.tv_ok);

        // 窗口宽度
        if (null != mViewRoot) {
            int width = Caches.get().getScreenWidth(WIDTH_RATIO_DEFAULT);
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            ViewUtils.setWidthAndHeight(mViewRoot, width, height);
        }

        try {
            mTvName.setText(mUserName);
            mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show("mTvOk");
                    dismissDialog();
                }
            });
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    /**
     * 设置账号
     */
    public static void setUserName(String userName) {
        mUserName = userName;
    }

    // ******************************** TCUserInfoDialog ********************************

    /**
     * 创建 TCUserInfoDialog
     */
    public static TCUserInfoDialog createDialog(Context context, String userName) {
        setUserName(userName);

        boolean needCreate = false;

        // 当 mTCUserInfoDialog 为 null 时，需要重新创建
        if (null == mTCUserInfoDialog) {
            needCreate = true;
        }

        // 当 mTCUserInfoDialog 不为 null，但是 getContext 不是 context 时
        if (null != mTCUserInfoDialog && context != mTCUserInfoDialog.getContext()) {
            LogUtils.w(TAG, "此处引发窗体泄露 | origin context -> " + mTCUserInfoDialog.getContext() + " | now context -> " + context);
            dismissDialog();
            needCreate = true;
        }

        // 根据需要重新创建
        if (needCreate) {
            mTCUserInfoDialog = new TCUserInfoDialog(context);
        }

        return mTCUserInfoDialog;
    }

    /**
     * 释放 UserCenterDialog
     */
    public static void dismissDialog() {
        if (null == mTCUserInfoDialog) {
            return;
        }

        if (mTCUserInfoDialog.isShowing()) {
            try {
                mTCUserInfoDialog.dismiss();
                mTCUserInfoDialog = null;
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }
        }
    }
}
