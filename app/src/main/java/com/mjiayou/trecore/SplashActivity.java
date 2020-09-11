package com.mjiayou.trecore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.mjiayou.trecorelib.util.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.content.ContextCompat;

public class SplashActivity extends BaseActivity {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 300;
    private String[] mStoragePermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
            , Manifest.permission.INTERNET
    };

    @Override
    protected boolean checkHideTitleBar() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        requestStoragePermission();
    }

    /**
     * 检查是否有读取SD卡权限
     */
    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onRequestPermissionsSuccess();
        } else {
            if (checkPermission(mContext, mStoragePermissions)) {
                onRequestPermissionsSuccess();
            } else {
                requestPermissions(mStoragePermissions, REQUEST_CODE_STORAGE_PERMISSION);
            }
        }
    }

    /**
     * 检查是否有权限
     */
    public static boolean checkPermission(Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRequestPermissionsSuccess();
                } else {
                    onRequestPermissionsFailure();
                }
                break;
        }
    }

    /**
     * 授权成功
     */
    private void onRequestPermissionsSuccess() {
        ToastUtils.show("授权成功");
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    /**
     * 授权失败
     */
    private void onRequestPermissionsFailure() {
        ToastUtils.show("授权失败");
        finish();
    }
}
