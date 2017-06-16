package com.mjiayou.trecorelib.util;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by treason on 15/7/18.
 */
public class AppUtil {

    private static final String TAG = AppUtil.class.getSimpleName();

    // ******************************** getAppInfoDetail ********************************

    /**
     * getAppDetailInfo
     */
    public static String getAppInfoDetail(Context context) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("**************** getAppInfoDetail ****************").append("\n");

        builder.append("\n");
        builder.append("******** getAppInfoStr ********").append("\n");
        builder.append(AppUtil.getAppInfoStr(context));

        builder.append("\n");
        builder.append("******** getDeviceInfoStr ********").append("\n");
        builder.append(DeviceUtil.getDeviceInfoStr(context));

        builder.append("\n");
        builder.append("******** getDirectoryInfoStr ********").append("\n");
        builder.append(DirectoryUtil.get().getDirectoryInfoStr());

        builder.append("\n");
        builder.append("******** getDipInfoStr ********").append("\n");
        builder.append(DipUtil.getDipInfoStr(context));

        builder.append("\n");
        builder.append("******** end ********").append("\n");

        return builder.toString();
    }

    // ******************************** getAppInfoStr ********************************

    /**
     * getAppInfoStr
     */
    public static String getAppInfoStr(Context context, String packageName) {
        StringBuilder builder = new StringBuilder();

        // getVersionInfoStr
        builder.append("\n");
        builder.append("**** getVersionInfoStr ****").append("\n");
        builder.append(getVersionInfoStr(context, packageName));

        // getSignInfoStr
        builder.append("\n");
        builder.append("**** getSignInfoStr ****").append("\n");
        builder.append(getSignInfoStr(context, packageName));

        // getApplicationInfoStr
        builder.append("\n");
        builder.append("**** getApplicationInfoStr ****").append("\n");
        builder.append(getApplicationInfoStr(context, packageName));

        // getBuildConfigInfoStr
        builder.append("\n");
        builder.append("**** getBuildConfigInfoStr ****").append("\n");
        builder.append(getBuildConfigInfoStr(com.mjiayou.trecorelib.BuildConfig.class));

        return builder.toString();
    }

    public static String getAppInfoStr(Context context) {
        return getAppInfoStr(context, context.getPackageName());
    }

    public static String getAppInfoStr(Context context, ApplicationInfo applicationInfo) {
        return getAppInfoStr(context, applicationInfo.packageName);
    }

    /**
     * getVersionInfoStr
     */
    public static String getVersionInfoStr(Context context, String packageName) {
        StringBuilder builder = new StringBuilder();
        builder.append("PackageName = ").append(getPackageName(context, packageName)).append("\n");
        builder.append("VersionName = ").append(getVersionName(context, packageName)).append("\n");
        builder.append("VersionCode = ").append(getVersionCode(context, packageName)).append("\n");
        return builder.toString();
    }

    public static String getVersionInfoStr(Context context) {
        return getVersionInfoStr(context, context.getPackageName());
    }

    /**
     * getSignInfoStr
     */
    public static String getSignInfoStr(Context context, String packageName) {
        StringBuilder builder = new StringBuilder();
        builder.append("PackageName = ").append(packageName).append("\n");
        builder.append("SignMD5 = ").append(getSignMD5(context, packageName)).append("\n");

        builder.append("** More **").append("\n");
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            Signature[] signatures = packageInfo.signatures;

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signatures[0].toByteArray()));

            builder.append("SigAlgName = ").append(certificate.getSigAlgName()).append("\n");
            builder.append("SerialNumber = ").append(certificate.getSerialNumber().toString()).append("\n");
            builder.append("SubjectDN = ").append(certificate.getSubjectDN().toString()).append("\n");
            builder.append("PublicKey = ").append(certificate.getPublicKey().toString()).append("\n");
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getSignInfoStr(Context context) {
        return getSignInfoStr(context, context.getPackageName());
    }

    /**
     * getApplicationInfoStr
     */
    public static String getApplicationInfoStr(Context context, String packageName) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("Label = ").append(getLabel(context, packageName)).append("\n");
            builder.append("DataDir = ").append(getDataDir(context, packageName)).append("\n");
            builder.append("SourceDir = ").append(getSourceDir(context, packageName)).append("\n");
            builder.append("PublicSourceDir = ").append(getPublicSourceDir(context, packageName)).append("\n");

            builder.append("** More **").append("\n");
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            builder.append("ClassName = ").append(applicationInfo.className).append("\n");
            builder.append("Permission = ").append(applicationInfo.permission).append("\n");
            builder.append("TaskAffinity = ").append(applicationInfo.taskAffinity).append("\n");
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getApplicationInfoStr(Context context) {
        return getApplicationInfoStr(context, context.getPackageName());
    }

    /**
     * getBuildConfigInfoStr
     */
    public static String getBuildConfigInfoStr(Class clazz) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("BuildConfig = ").append(clazz.getName()).append("\n");

            builder.append("** BuildConfig.class **").append("\n");
            builder.append(ConvertUtil.parseString(clazz));
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getBuildConfigInfoStr() {
        return getBuildConfigInfoStr(com.mjiayou.trecorelib.BuildConfig.class);
    }

    // ******************************** getPackageManager ********************************

    /**
     * 获取PackageManager
     */
    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    // ******************************** getPackageInfo ********************************

    /**
     * 获得 PackageInfo 对象
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return getPackageManager(context).getPackageInfo(packageName, PackageManager.GET_SIGNATURES); // PackageManager.GET_ACTIVITIES
    }

    public static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        return getPackageInfo(context, context.getPackageName());
    }

    /**
     * 获得包名 - packageInfo.packageName
     */
    public static String getPackageName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.packageName;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getPackageName(Context context) {
        return getPackageName(context, context.getPackageName());
    }

    /**
     * 获得版本名 - packageInfo.versionName
     */
    public static String getVersionName(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.versionName;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获得版本号 - packageInfo.versionCode
     */
    public static int getVersionCode(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            return packageInfo.versionCode;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return 0;
    }

    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    // ******************************** getSignMD5-签名MD5 ********************************

    /**
     * 获取指定包名APP的签名指纹的MD5
     */
    public static String getSignMD5(Context context, String packageName) {
        try {
            PackageInfo packageInfo = getPackageInfo(context, packageName);
            Signature[] signatures = packageInfo.signatures;

            StringBuilder builder = new StringBuilder();
            builder.append(MD5Util.md5(signatures[0].toByteArray()));
            return builder.toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getSignMD5(Context context) {
        return getSignMD5(context, context.getPackageName());
    }

    // ******************************** getApplicationInfo ********************************

    /**
     * 获取 ApplicationInfo 对象
     */
    public static ApplicationInfo getApplicationInfo(Context context, String packageName) throws PackageManager.NameNotFoundException {
        return getPackageManager(context).getApplicationInfo(packageName, PackageManager.GET_META_DATA);
    }

    public static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
        return getApplicationInfo(context, context.getPackageName());
    }

    /**
     * 获得应用名称
     */
    public static String getLabel(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return String.valueOf(applicationInfo.loadLabel(context.getPackageManager()));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getLabel(Context context) {
        return getLabel(context, context.getPackageName());
    }

    /**
     * 获得应用图标
     */
    public static Drawable getIcon(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.loadIcon(context.getPackageManager());
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static Drawable getIcon(Context context) {
        return getIcon(context, context.getPackageName());
    }

    /**
     * 获得 DataDir
     */
    public static String getDataDir(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.dataDir;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getDataDir(Context context) {
        return getDataDir(context, context.getPackageName());
    }

    /**
     * 获得 SourceDir
     */
    public static String getSourceDir(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.sourceDir;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getSourceDir(Context context) {
        return getSourceDir(context, context.getPackageName());
    }

    /**
     * 获得 PublicSourceDir
     */
    public static String getPublicSourceDir(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);
            return applicationInfo.publicSourceDir;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getPublicSourceDir(Context context) {
        return getPublicSourceDir(context, context.getPackageName());
    }

    // ******************************** OTHER ********************************

    // ******************************** checkMissingPermission ********************************

    /**
     * checkMissingPermission
     *
     * @param context
     * @param permission Manifest.permission.CALL_PHONE
     */
    public static boolean checkMissingPermission(Context context, String permission) {

//        if (android.support.v4.content.PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//            Exception e = new Exception("Missing permissions required by Intent.ACTION_CALL: android.permission.CALL_PHONE");
//            LogUtil.printStackTrace(e);
//            return true;
//        } else {
//            return false;
//        }

        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            LogUtil.printStackTrace(new Exception("Missing permissions: " + permission)); // Missing permissions required by Intent.ACTION_CALL: android.permission.CALL_PHONE
            return true;
        } else {
            return false;
        }
    }

    // ******************************** MetaValue管理 ********************************

    /**
     * 获取Manifest内的meta-data的value
     */
    public static String getMetaValue(Context context, String packageName, String metaName) {
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context, packageName);

            Bundle bundle = applicationInfo.metaData;
            return bundle.get(metaName).toString();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    public static String getMetaValue(Context context, String metaName) {
        return getMetaValue(context, context.getPackageName(), metaName);
    }

    // ******************************** getInstalledPackageInfoList ********************************

    /**
     * 获取已安装应用包名列表
     */
    public static List<PackageInfo> getInstalledPackageInfoList(Context context) {
        List<PackageInfo> packageInfoList = getPackageManager(context).getInstalledPackages(PackageManager.GET_SIGNATURES);
        return packageInfoList;
    }

    /**
     * 获取已安装应用列表，按照字母顺序排序
     */
    public static List<ApplicationInfo> getInstalledApplicationInfoList(Context context) {
        List<ApplicationInfo> applicationInfoList = getPackageManager(context).getInstalledApplications(0);
        Collections.sort(applicationInfoList, new ApplicationInfo.DisplayNameComparator(context.getPackageManager()));
        return applicationInfoList;
    }

    /**
     * 获取系统应用信息
     */
    public static List<ApplicationInfo> getSystemApplicationInfoList(Context context) {
        List<ApplicationInfo> allList = getInstalledApplicationInfoList(context);
        List<ApplicationInfo> systemAppList = new ArrayList<>();
        for (ApplicationInfo app : allList) {
            // 系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                systemAppList.add(app);
            }
        }
        return systemAppList;
    }

    /**
     * 获取第三方应用信息
     */
    public static List<ApplicationInfo> getThirdApplicationInfoList(Context context) {
        List<ApplicationInfo> allList = getInstalledApplicationInfoList(context);
        List<ApplicationInfo> thirdAppList = new ArrayList<>();
        for (ApplicationInfo app : allList) {
            // 非系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                thirdAppList.add(app);
            }
            // 本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                thirdAppList.add(app);
            }
        }
        return thirdAppList;
    }

    // ******************************** APP管理 ********************************

    /**
     * 从SD卡目录安装APK文件
     */
    public static void installAPKFromSDCard(Context context, String apkPath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 从asset目录下安装APK文件
     */
    public static void installAPKFromAsset(Context context, String apkDir, String apkName) throws IOException {
        try {
            // asset目录结构
            String apkPathForAsset = apkDir + "/" + apkName;
            // SDCard目录结构
            String apkDirForSDCard = DirectoryUtil.get().APP_CACHE + apkDir + "/";
            String apkPathForSDCard = DirectoryUtil.get().APP_CACHE + apkDir + "/" + apkName;

            // 读取文件数据流
            InputStream inputStream = AssetUtil.getAssetInputStream(context, apkPathForAsset);
            if (inputStream != null) {
                // 递归创建 APP_CACHE + NAME_LIBS 文件夹
                FileUtil.createFolder(apkDirForSDCard);
                // 将资源中的文件重写到sdcard中
                FileUtil.writeToFile(apkPathForSDCard, inputStream);
                // 安装APK
                installAPKFromSDCard(context, apkPathForSDCard);
            } else {
                LogUtil.e(TAG, "asset目录下找不到文件：" + apkPathForAsset);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 安装应用
     */
    public static void installAPP(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_PACKAGE_ADDED, Uri.fromParts("package", path, null));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 判断APP是否被安装
     */
    public static boolean isAPPInstalled(Context context, String packageName) {
        try {
            getPackageManager(context).getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return false;
    }

    /**
     * 卸载应用
     */
    public static void uninstallAPP(Context context, String packageName) {
        try {
            if (!isAPPInstalled(context, packageName)) {
                ToastUtil.show("程序未安装");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", packageName, null));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开应用
     */
    public static void openAPP(Context context, String packageName) {
        try {
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            if (resolveInfoList != null && resolveInfoList.size() > 0) {
                ResolveInfo resolveInfo = resolveInfoList.get(0);
                String activityPackageName = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName componentName = new ComponentName(activityPackageName, className);

                intent.setComponent(componentName);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 卸载应用广播监听
     */
    public static BroadcastReceiver getUninstallAPPReceiver(Context context) {
        try {
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
            filter.addDataScheme("package");

            // 自定义的广播接收类，接收到结果后的操作
            BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ToastUtil.show("deleteReceiver | onReceive");
                }
            };
            context.registerReceiver(deleteReceiver, filter); // 注册广播监听应用
            return deleteReceiver;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return null;
    }

    // ******************************** 系统操作 ********************************

    /**
     * 打开系统设置
     */
    public static void openSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开浏览器
     */
    public static void openWebView(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开地图
     */
    public static void openMap(Context context, String lat, String lon) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon)); // Uri.parse("geo:38.899533,-77.036476")
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 播放多媒体
     */
    public static void openMedia(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("file://" + path));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开拨号键盘
     */
    public static void openCallDialog(Context context, String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接拨号
     */
    public static void openCallDirectly(Context context, String number) {
        try {
            if (checkMissingPermission(context, Manifest.permission.CALL_PHONE)) {
                return;
            }

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发短信
     */
    public static void openSendMessageBoard(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + number));
            intent.putExtra("sms_body", body);
            intent.setType("vnd.android-dir/mms-sms");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接发短信
     */
    public static void openSendMessageDirectly(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto://" + number));
            intent.putExtra("sms_body", body);
            intent.setType("vnd.android-dir/mms-sms");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 直接发彩信
     */
    public static void openSendMessageMedia(Context context, String number, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra("sms_body", body);
            intent.putExtra("sms_body", "shenrenkui");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://media/external/images/media/23"));
            intent.setType("image/png");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发邮件
     */
    public static void openSendEmail(Context context, String emailTo) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailTo));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }

    /**
     * 打开发邮件
     */
    public static void openSendEmail(Context context, String[] emailTo, String[] emailCC, String subject, String body) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, emailTo);
            intent.putExtra(Intent.EXTRA_CC, emailCC);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.setType("message/rfc882");
            context.startActivity(Intent.createChooser(intent, "选择邮件客户端"));
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
    }
}
