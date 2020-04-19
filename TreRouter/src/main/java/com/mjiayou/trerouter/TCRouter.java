package com.mjiayou.trerouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class TCRouter {

    private Context mContext;
    private Map<String, Class<? extends Activity>> mActivityMap; // 所有Activity的容器

    private static TCRouter mTCRouter = new TCRouter();

    private TCRouter() {
        mActivityMap = new HashMap<>();
    }

    public static TCRouter get() {
        return mTCRouter;
    }

    public void init(Context context) {
        mContext = context;
        List<String> className = getClassName(RouterName.TC_ROUTER_UTILS_PACKAGE_NAME);
        for (String s : className) {
            try {
                Class<?> aClass = Class.forName(s);
                // 如果aClass是IRouter的子类
                if (IRouter.class.isAssignableFrom(aClass)) {
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 打印
        Log.e("matengfei", "mActivityMap = ");
        for (Map.Entry<String, Class<? extends Activity>> entry : mActivityMap.entrySet()) {
            Log.e("matengfei", entry.getKey() + " | " + entry.getValue().getName());
        }
    }

    public Class<? extends Activity> getActivity(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return mActivityMap.get(key);
    }

    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (TextUtils.isEmpty(key) || clazz == null) {
            return;
        }
        if (!mActivityMap.containsKey(key)) {
            mActivityMap.put(key, clazz);
        }
    }

    public void startActivity(Context context, String key, Bundle bundle) {
        Class<? extends Activity> clazz = getActivity(key);
        if (clazz != null) {
            Intent intent = new Intent(context, clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }

    private List<String> getClassName(String packageName) {
        // 创建一个class对象的集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            // 通过包管理器，获取到应用信息类，然后获取到APK的完整路径
            path = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), 0).sourceDir;
            // 根据APK的完整路径获取到编译后的dex文件目录
            DexFile dexFile = new DexFile(path);
            // 获取编译后的dex文件中的所有class
            Enumeration enumeration = dexFile.entries();
            // 然后进行遍历
            while (enumeration.hasMoreElements()) {
                // 通过遍历所有class的包名
                String name = (String) enumeration.nextElement();
                // 判断类的包名是否符合要求
                if (name.contains(packageName)) {
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }
}
