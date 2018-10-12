package com.mjiayou.trecore.util;

import android.content.Context;

import com.mjiayou.trecore.BuildConfig;
import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.common.Configs;
import com.mjiayou.trecorelib.util.AppUtils;
import com.mjiayou.trecorelib.util.ConvertUtils;

/**
 * Created by treason on 2016/12/16.
 */

public class DebugUtils {

    public static String getBuildConfigInfo(Context context) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("**************** getBuildConfigInfo ****************").append("\n");

        builder.append("\n");
        builder.append("BuildConfig = ").append(BuildConfig.class.getName()).append("\n");
        builder.append("BuildConfig.DEBUG = ").append(BuildConfig.DEBUG).append("\n");
        builder.append("BuildConfig.APPLICATION_ID = ").append(BuildConfig.APPLICATION_ID).append("\n");
        builder.append("BuildConfig.BUILD_TYPE = ").append(BuildConfig.BUILD_TYPE).append("\n");
        builder.append("BuildConfig.FLAVOR = ").append(BuildConfig.FLAVOR).append("\n");
        builder.append("BuildConfig.VERSION_CODE = ").append(BuildConfig.VERSION_CODE).append("\n");
        builder.append("BuildConfig.VERSION_NAME = ").append(BuildConfig.VERSION_NAME).append("\n");

        builder.append("\n");
        builder.append("gradle_app_name = ").append(context.getString(R.string.gradle_app_name)).append("\n");
        builder.append("gradle_build_types_name = ").append(context.getString(R.string.gradle_build_types_name)).append("\n");
        builder.append("gradle_build_types_value = ").append(context.getString(R.string.gradle_build_types_value)).append("\n");
        builder.append("BuildConfig.GRADLE_APP_DEBUG = ").append(BuildConfig.GRADLE_APP_DEBUG).append("\n");
        builder.append("BuildConfig.GRADLE_BUILD_TYPES_NAME = ").append(BuildConfig.GRADLE_BUILD_TYPES_NAME).append("\n");
        builder.append("BuildConfig.GRADLE_BUILD_TYPES_VALUE = ").append(BuildConfig.GRADLE_BUILD_TYPES_VALUE).append("\n");

        builder.append("\n");
        builder.append("gradle_product_flavors_name = ").append(context.getString(R.string.gradle_product_flavors_name)).append("\n");
        builder.append("gradle_product_flavors_value = ").append(context.getString(R.string.gradle_product_flavors_value)).append("\n");

        builder.append("\n");
        builder.append("gradle_tc_id = ").append(AppUtils.getMetaValue(context, Configs.META_TC_ID)).append("\n");
        builder.append("gradle_tc_key = ").append(AppUtils.getMetaValue(context, Configs.META_TC_KEY)).append("\n");
        builder.append("gradle_tc_channel = ").append(AppUtils.getMetaValue(context, Configs.META_TC_CHANNEL)).append("\n");

        builder.append("\n");
        builder.append("**** BuildConfig.class ****").append("\n");
        builder.append(ConvertUtils.parseString(BuildConfig.class));

        return builder.toString();
    }
}
