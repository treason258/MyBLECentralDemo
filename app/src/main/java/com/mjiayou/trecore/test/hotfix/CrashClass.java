package com.mjiayou.trecore.test.hotfix;

import com.mjiayou.trecorelib.util.ToastUtils;

public class CrashClass {

    public static void crash() {
        int a = 10;
        int b = 0;
        ToastUtils.show("a/b=" + a / b);
    }
}
