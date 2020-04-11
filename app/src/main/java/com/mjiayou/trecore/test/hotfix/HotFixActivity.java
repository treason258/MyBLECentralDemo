package com.mjiayou.trecore.test.hotfix;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HotFixActivity extends TCActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        getTitleBar().setTitle("HotFixActivity");
    }

    public void crash(View view) {
        // 崩溃代码
        CrashClass.crash();
    }

    public void fix(View view) {
        // 将修复包复制到当前私有目录
        File odex = getDir("odex", MODE_PRIVATE);
        // 获取到补丁的名字
        String name = "out.dex";
        // 获取到修复包的完整名字
        String filePath = new File(odex, name).getAbsolutePath();
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        // 创建文件输入输出流
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 起点
            fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory(), name));
            // 终点
            fileOutputStream = new FileOutputStream(filePath);
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
            File f = new File(filePath);
            // 去加载dex文件 然后不定的dex文件与当前App的dex文件进行合并
            HotFixUtils.loadDex(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
