package com.mjiayou.treannotation;

import android.app.Activity;

public class TCKnife {

    public static final String TC_KNIFE_NAME_SUFFIX = "_TCKnife";

    public static void bind(Activity activity) {
        String viewBindingName = activity.getClass().getName() + TC_KNIFE_NAME_SUFFIX;
        try {
            // 获取到activity对应的ViewBinding文件的类对象
            Class<?> aClass = Class.forName(viewBindingName);
            ITCKnife iViewBinder = (ITCKnife) aClass.newInstance();
            iViewBinder.bindView(activity);

            // other
//            Field[] declaredFields = activity.getClass().getDeclaredFields();
//            for (Field declaredField : declaredFields) {
//                TCBindView tcBindView = declaredField.getAnnotation(TCBindView.class);
//                if (tcBindView == null) {
//                    // 不是控件或者控件没有加注解
//                    continue;
//                }
//                String name = declaredField.getName();
//                activity.
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
