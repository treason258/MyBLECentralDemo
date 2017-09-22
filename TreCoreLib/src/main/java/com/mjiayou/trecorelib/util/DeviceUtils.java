package com.mjiayou.trecorelib.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * DeviceUtils
 */
public class DeviceUtils {

    private final static String TAG = DeviceUtils.class.getSimpleName();

    /**
     * 获取设备信息
     */
    public static String getDeviceInfoStr(Context context) {
        try {
            StringBuilder builder = new StringBuilder();

            // DisplayMetrics
            builder.append("\n");
            builder.append("**** DisplayMetrics ****").append("\n");
            builder.append("Screen = ").append(getScreenInfo(context)).append("\n");
            builder.append("Width = ").append(getScreenWidth(context)).append("\n");
            builder.append("Height = ").append(getScreenHeight(context)).append("\n");
            builder.append("DensityDpi = ").append(getDensityDpi(context)).append("\n");

            // TelephonyManager
            builder.append("\n");
            builder.append("**** TelephonyManager ****").append("\n");
            builder.append("DeviceId（IMEI国际移动台设备识别码） = ").append(getIMEI(context)).append("\n");
            builder.append("SubscriberId（IMSI国际移动客户识别码） = ").append(getIMSI(context)).append("\n");
            builder.append("PhoneNumber（手机号码） = ").append(getPhoneNumber(context)).append("\n");
            builder.append("SimSerialNumber（SIM卡序列号） = ").append(getSimSerialNumber(context)).append("\n");
            builder.append("SimOperatorName（SIM卡运营商） = ").append(getSimOperatorName(context)).append("\n");
            builder.append("NetworkOperatorName（网络运营商） = ").append(getNetworkOperatorName(context)).append("\n");

            // ConnectivityManager
            builder.append("\n");
            builder.append("**** ConnectivityManager ****").append("\n");
            builder.append("网络是否连接 = ").append(isNetConnected(context)).append("\n");
            builder.append("网络是否是wifi连接 = ").append(isWifiConnected(context)).append("\n");
            builder.append("MAC地址 = ").append(getMacAddress(context)).append("\n");
            builder.append("IP地址 = ").append(getIpAddress(context)).append("\n");

            // WifiManager
            builder.append("\n");
            builder.append("**** WifiManager ****").append("\n");
            builder.append("SSID（网络名称） = ").append(getSSID(context)).append("\n");

            // LocationManager
            builder.append("\n");
            builder.append("**** LocationManager ****").append("\n");
            builder.append("Longitude（经度） = ").append(getLongitude(context)).append("\n");
            builder.append("Latitude（纬度） = ").append(getLatitude(context)).append("\n");
            builder.append("Altitude（高度） = ").append(getAltitude(context)).append("\n");
            builder.append("Provider（获取提供者） = ").append(getProvider(context)).append("\n");
            builder.append("Accuracy（获取精确度） = ").append(getAccuracy(context)).append("\n");
            builder.append("** getExtras **").append("\n");
            builder.append(ConvertUtils.parseString(getExtras(context)));

            // java.util.Locale
            builder.append("\n");
            builder.append("**** java.util.Locale ****").append("\n");
            builder.append("Language = ").append(getLanguage()).append("\n");
            builder.append("Country = ").append(java.util.Locale.getDefault().getCountry()).append("\n");
            builder.append("DisplayLanguage = ").append(java.util.Locale.getDefault().getDisplayLanguage()).append("\n");
            builder.append("DisplayCountry = ").append(java.util.Locale.getDefault().getDisplayCountry()).append("\n");
            builder.append("DisplayName = ").append(java.util.Locale.getDefault().getDisplayName()).append("\n");
            builder.append("ISO3Language = ").append(java.util.Locale.getDefault().getISO3Language()).append("\n");
            builder.append("ISO3Country = ").append(java.util.Locale.getDefault().getISO3Country()).append("\n");

            // StatFs
            builder.append("\n");
            builder.append("**** StatFs ****").append("\n");
            builder.append("手机存储的总空间大小 = ").append(getGBStr(getInternalTotalMemorySize())).append("\n");
            builder.append("手机存储的可用空间大小 = ").append(getGBStr(getInternalAvailableMemorySize())).append("\n");
            builder.append("手机存储的已用空间大小 = ").append(getGBStr(getInternalUsedMemorySize())).append("\n");
            builder.append("是否存在SD卡 = ").append(existExternalCard()).append("\n");
            builder.append("SD卡存储的总空间大小 = ").append(getGBStr(getExternalTotalMemorySize())).append("\n");
            builder.append("SD卡存储的可用空间大小 = ").append(getGBStr(getExternalAvailableMemorySize())).append("\n");
            builder.append("SD卡存储的已空间大小 = ").append(getGBStr(getExternalUsedMemorySize())).append("\n");

            // Settings.Secure.getString
            builder.append("\n");
            builder.append("**** Settings.Secure.getString ****").append("\n");
            builder.append("Settings.Secure.ANDROID_ID = ").append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)).append("\n");
            builder.append("Settings.Secure.BLUETOOTH_ON = ").append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.BLUETOOTH_ON)).append("\n");
            builder.append("Settings.Secure.BLUETOOTH_ON = ").append(Settings.Secure.getString(context.getContentResolver(), Settings.Global.BLUETOOTH_ON)).append("\n");
            builder.append("Settings.Secure.ADB_ENABLED = ").append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ADB_ENABLED)).append("\n");

            // other
            builder.append("\n");
            builder.append("**** other ****").append("\n");
            builder.append("UDID = ").append(getUDID(context)).append("\n");
            builder.append("UUID = ").append(getUUID()).append("\n");
            builder.append("ActionBar高度 = ").append(getActionBarHeight(context)).append("\n");
            builder.append("StatusBar高度 = ").append(getStatusBarHeight(context)).append("\n");
            builder.append("getDeviceType = ").append(getDeviceType(context)).append("\n");
            builder.append("getConnectionType = ").append(getConnectionType(context)).append("\n");
            builder.append("getCarrier = ").append(getCarrier(context)).append("\n");
            builder.append("getScreenOrientation = ").append(getScreenOrientation(context)).append("\n");
            builder.append("getAndroidId = ").append(getAndroidId(context)).append("\n");
            builder.append("getOSV = ").append(getOSV()).append("\n");
            builder.append("getSystemTime = ").append(getSystemTime()).append("\n");

            // getMemoryInfoStr
            builder.append("\n");
            builder.append("**** getMemoryInfoStr ****").append("\n");
            builder.append(getMemoryInfoStr(context));

            // getBuildInfoStr
            builder.append("\n");
            builder.append("**** getBuildInfoStr ****").append("\n");
            builder.append(getBuildInfoStr());

            // build.prop
            builder.append("\n");
            builder.append("**** build.prop ****").append("\n");
            builder.append(ConvertUtils.parseString(RomUtils.getBuildProperties()));

            return builder.toString();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** DisplayMetrics ********************************

    /**
     * 获取DisplayMetrics对象
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        try {
            if (context instanceof Activity) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                return displayMetrics;
            } else {
                return context.getResources().getDisplayMetrics();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取屏幕尺寸
     */
    public static String getScreenInfo(Context context) {
        try {
            DisplayMetrics displayMetrics = getDisplayMetrics(context);
            if (displayMetrics != null) {
                return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取屏幕宽度 - widthPixels
     */
    public static int getScreenWidth(Context context) {
        try {
            DisplayMetrics displayMetrics = getDisplayMetrics(context);
            if (displayMetrics != null) {
                return displayMetrics.widthPixels;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获取屏幕高度 - heightPixels
     */
    public static int getScreenHeight(Context context) {
        try {
            DisplayMetrics displayMetrics = getDisplayMetrics(context);
            if (displayMetrics != null) {
                return displayMetrics.heightPixels;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获取屏幕高度 - heightPixels
     */
    public static int getDensityDpi(Context context) {
        try {
            DisplayMetrics displayMetrics = getDisplayMetrics(context);
            if (displayMetrics != null) {
                return displayMetrics.densityDpi;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获取Display对象
     */
    private static Display getDisplay(Context context) {
        try {
            if (context instanceof Activity) {
                return ((Activity) context).getWindowManager().getDefaultDisplay();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取屏幕尺寸
     */
    @Deprecated
    public static String getScreenInfoByDisplay(Context context) {
        try {
            Display display = getDisplay(context);
            if (display != null) {
                return display.getWidth() + "*" + display.getHeight();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** TelephonyManager ********************************

    /**
     * 获取TelephonyManager对象
     */
    private static TelephonyManager getTelephonyManager(Context context) {
        try {
            return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取IMEI - telephonyManager.getDeviceId
     */
    public static String getIMEI(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                return "";
            }
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取IMSI - telephonyManager.getSubscriberId
     */
    public static String getIMSI(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                return "";
            }
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getSubscriberId();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取手机号 - telephonyManager.getLine1Number
     */
    public static String getPhoneNumber(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                return "";
            }
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getLine1Number();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取SIM卡序列号 - telephonyManager.getSimSerialNumber
     */
    public static String getSimSerialNumber(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                return "";
            }
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getSimSerialNumber();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取SIM卡运营商名称 - telephonyManager.getSimOperatorName
     */
    public static String getSimOperatorName(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getSimOperatorName();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取注册的网络运营商的名字 - telephonyManager.getNetworkOperatorName
     */
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager telephonyManager = getTelephonyManager(context);
            if (telephonyManager != null) {
                return telephonyManager.getNetworkOperatorName();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** ConnectivityManager ********************************

    /**
     * 获取ConnectivityManager对象
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        try {
            return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取当前连接NetworkInfo对象
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                return null;
            }
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            if (connectivityManager != null) {
                return connectivityManager.getActiveNetworkInfo();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取 Wifi NetworkInfo 对象
     */
    public static NetworkInfo getWifiNetworkInfo(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                return null;
            }
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            if (connectivityManager != null) {
                return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取 Mobile NetworkInfo 对象
     */
    public static NetworkInfo getMobileNetworkInfo(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                return null;
            }
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            if (connectivityManager != null) {
                return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        try {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return false;
    }

    /**
     * 判断网络是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        try {
            NetworkInfo networkInfo = getActiveNetworkInfo(context);
            if (isNetConnected(context) && networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return false;
    }

    /**
     * 获取Mac地址
     */
    public static String getMacAddress(Context context) {
        try {
            NetworkInfo wifiNetworkInfo = getWifiNetworkInfo(context);
            if (wifiNetworkInfo != null
                    && wifiNetworkInfo.getState() != null
                    && wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTED) { // 判断当前是否使用wifi连接
                WifiManager wifiManager = getWifiManager(context);
                if (wifiManager != null) {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        String macAddress = wifiInfo.getMacAddress();
                        if (TextUtils.isEmpty(macAddress) || "02:00:00:00:00:00".equals(macAddress)) {
                            macAddress = getMacAddressByFile();
                        }
                        return macAddress;
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    private static String getMacAddressByFile() {
        try {
            String macAddress = loadFileAsString("/sys/class/net/wlan0/address");
            if (TextUtils.isEmpty(macAddress)) {
                macAddress = loadFileAsString("/sys/class/net/eth0/address");
            }
            return macAddress;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    private static String loadFileAsString(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            String text = loadReaderAsString(reader);
            reader.close();
            return text.substring(0, 17);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    private static String loadReaderAsString(Reader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[4096];
            int readLength = reader.read(buffer);
            while (readLength >= 0) {
                builder.append(buffer, 0, readLength);
                readLength = reader.read(buffer);
            }
            return builder.toString();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取IP地址
     */
    public static String getIpAddress(Context context) {
        try {
            ConnectivityManager connectivityManager = getConnectivityManager(context);
            if (connectivityManager != null) {
                NetworkInfo mobileNetworkInfo = getMobileNetworkInfo(context);
                NetworkInfo wifiNetworkInfo = getWifiNetworkInfo(context);
                if (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) {
                    return getIpAddressByLocal();
                } else if (wifiNetworkInfo != null && wifiNetworkInfo.isConnected()) {
                    WifiInfo wifiInfo = getConnectionInfo(context);
                    if (wifiInfo != null) {
                        int ipAddress = wifiInfo.getIpAddress();
                        return intToIp(ipAddress);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getIpAddressByLocal() {
        try {
            ArrayList<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaceList) {
                ArrayList<InetAddress> inetAddressList = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddressList) {
                    if (inetAddress != null && !inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    private static String intToIp(int ipInt) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(ipInt & 0xFF).append(".");
            builder.append((ipInt >> 8) & 0xFF).append(".");
            builder.append((ipInt >> 16) & 0xFF).append(".");
            builder.append((ipInt >> 24) & 0xFF);
            return builder.toString();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** WifiManager ********************************

    /**
     * 获取WifiManager对象
     */
    private static WifiManager getWifiManager(Context context) {
        try {
            return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取WifiInfo对象
     */
    public static WifiInfo getConnectionInfo(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                return null;
            }
            WifiManager wifiManager = getWifiManager(context);
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取SSID - wifiInfo.getSSID()
     */
    public static String getSSID(Context context) {
        try {
            WifiInfo wifiInfo = getConnectionInfo(context);
            if (wifiInfo != null) {
                return wifiInfo.getSSID();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 打开WIFI - setWifiEnabled(true);
     */
    public static void setWifiEnabled(Context context) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.CHANGE_WIFI_STATE)) {
                return;
            }

            WifiManager wifiManager = getWifiManager(context);
            if (wifiManager != null && !wifiManager.isWifiEnabled()) { // 如果当前wifi不可用
                wifiManager.setWifiEnabled(true);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    // ******************************** LocationManager ********************************

    /**
     * 获取LocationManager对象
     */
    private static LocationManager getLocationManager(Context context) throws Exception {
        try {
            return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取Location对象
     */
    public static Location getLocation(Context context) throws Exception {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    && AppUtils.checkMissingPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                return null;
            }
            LocationManager locationManager = getLocationManager(context);
            if (locationManager != null) {
                return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    /**
     * 获取经度（location.getLongitude()）
     */
    public static double getLongitude(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getLongitude();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0d;
    }

    /**
     * 获取纬度（location.getLatitude()）
     */
    public static double getLatitude(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getLatitude();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0d;
    }

    /**
     * 获取高度（location.getAltitude()）
     */
    public static double getAltitude(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getAltitude();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0d;
    }

    /**
     * 获取提供者（location.getProvider()）
     */
    public static String getProvider(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getProvider();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取精确度（location.getAccuracy()）
     */
    public static float getAccuracy(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getAccuracy();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 获取Extras（location.getExtras()）
     */
    public static Bundle getExtras(Context context) {
        try {
            Location location = getLocation(context);
            if (location != null) {
                return location.getExtras();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    // ******************************** java.util.Locale ********************************

    /**
     * 获取当前系统语言
     * <p>
     * 中文 - zh
     * 英文 - en
     * 韩语 - ko
     * 日语 - ja
     * 法语 - fr
     * 西班牙语 - es
     * 德语 - de
     * 俄语 – ru
     */
    public static String getLanguage() {
        try {
            return java.util.Locale.getDefault().getLanguage();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** StatFs ********************************

    /**
     * 手机存储的总空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getInternalTotalMemorySize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            float totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 手机存储的可用空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getInternalAvailableMemorySize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            float availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 手机存储的已用空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getInternalUsedMemorySize() {
        try {
            return getInternalTotalMemorySize() - getInternalAvailableMemorySize();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 是否存在SD卡并可读写操作
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean existExternalCard() {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return false;
    }

    /**
     * SD卡存储的总空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getExternalTotalMemorySize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            float totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * SD卡存储的可用空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getExternalAvailableMemorySize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            float availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * SD卡存储的已用空间大小
     * <p>
     * 单位：字节（B）
     */
    public static float getExternalUsedMemorySize() {
        try {
            return getExternalTotalMemorySize() - getExternalAvailableMemorySize();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 字节B转成KB
     */
    public static float getKB(float B) {
        try {
            return B / 1024;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 字节B转成MB
     */
    public static float getMB(float B) {
        try {
            return B / 1024 / 1024;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 字节B转成GB
     */
    public static float getGB(float B) {
        try {
            return B / 1024 / 1024 / 1024;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0f;
    }

    /**
     * 字节B转成GB
     */
    public static String getGBStr(float B) {
        try {
            return getGB(B) + " GB";
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    // ******************************** other ********************************

    /**
     * 获取手机唯一标识
     */
    public static String getUDID(Context context) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(getIMEI(context));
            builder.append(getIMSI(context));
            if (!TextUtils.isEmpty(getMacAddress(context))) {
                builder.append(getMacAddress(context));
            }
            return MD5Utils.md5(builder.toString());
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取标准的UUID字符串
     */
    public static String getUUID() {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取ActionBar的高度，单位为px
     */
    public static int getActionBarHeight(Context context) {
        try {
            int actionBarHeight = 0;
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
            }
            return actionBarHeight;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        try {
            int statusBarHeight;
            int x;
            Class<?> c;
            Object obj;
            Field field;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
                return statusBarHeight;
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }
            return 0;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0;
    }

    /**
     * 获取设备类型
     */
    private static final int DEVICE_TYPE_UNKNOWN = 0; // 未知
    private static final int DEVICE_TYPE_IPHONE = 1; // iPhone
    private static final int DEVICE_TYPE_ANDROID_PHONE = 2; // Android 手机
    private static final int DEVICE_TYPE_IPAD = 3; // iPad
    private static final int DEVICE_TYPE_WINDOWS_PHONT = 4; // Windows Phone
    private static final int DEVICE_TYPE_ANDROID_PAD = 5; // Android 平板
    private static final int DEVICE_TYPE_TV = 6; // 智能TV

    /**
     * 获取设备类型
     * <p>
     * deviceType
     * 0-未知
     * 1-iPhone
     * 2-Android 手机
     * 3-iPad
     * 4-Windows Phone
     * 5-Android 平板
     * 6-智能TV
     */
    public static int getDeviceType(Context context) {
        try {
            int deviceType = DEVICE_TYPE_ANDROID_PHONE;
            if (isPad(context)) {
                deviceType = DEVICE_TYPE_ANDROID_PAD;
            }
            return deviceType;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return DEVICE_TYPE_UNKNOWN;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        try {
            return (context.getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return false;
    }

    /**
     * 获取网络连接类型
     */
    private static final int CONNECTION_TYPE_UNKNOWN = 0; // 未知
    private static final int CONNECTION_TYPE_ETHERNET = 1; // Ethernet
    private static final int CONNECTION_TYPE_WIFI = 2; // WIFI网络
    private static final int CONNECTION_TYPE_MOBILE_UNKNOWN = 3; // 蜂窝数据网络-未知
    private static final int CONNECTION_TYPE_MOBILE_2G = 4; // 蜂窝数据网络-2G
    private static final int CONNECTION_TYPE_MOBILE_3G = 5; // 蜂窝数据网络-3G
    private static final int CONNECTION_TYPE_MOBILE_4G = 6; // 蜂窝数据网络-4G

    /**
     * 获取网络连接类型
     * <p>
     * connectiontype
     * 0-未知
     * 1-Ethernet
     * 2-WIFI网络
     * 3-蜂窝数据网络-未知
     * 4-蜂窝数据网络-2G
     * 5-蜂窝数据网络-3G
     * 6-蜂窝数据网络-4G
     */
    public static int getConnectionType(Context context) {
        try {
            // 获取当前网络类型，如果为空，返回无网络
            NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
            if (null == activeNetInfo || !activeNetInfo.isAvailable()) {
                return CONNECTION_TYPE_UNKNOWN;
            }

            // 判断连接的是不是wifi
            NetworkInfo wifiNetworkInfo = getWifiNetworkInfo(context);
            if (null != wifiNetworkInfo) {
                NetworkInfo.State state = wifiNetworkInfo.getState();
                if (null != state) {
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        return CONNECTION_TYPE_WIFI;
                    }
                }
            }

            // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
            NetworkInfo mobileNetworkInfo = getMobileNetworkInfo(context);

            if (null != mobileNetworkInfo) {
                NetworkInfo.State state = mobileNetworkInfo.getState();
                String strSubTypeName = mobileNetworkInfo.getSubtypeName();
                if (null != state) {
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        switch (activeNetInfo.getSubtype()) {
                            // 如果是2g类型
                            case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                            case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                            case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                return CONNECTION_TYPE_MOBILE_2G;
                            // 如果是3g类型
                            case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                return CONNECTION_TYPE_MOBILE_3G;
                            // 如果是4g类型
                            case TelephonyManager.NETWORK_TYPE_LTE:
                                return CONNECTION_TYPE_MOBILE_4G;
                            default:
                                // 中国移动 联通 电信 三种3G制式
                                if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                    return CONNECTION_TYPE_MOBILE_3G;
                                } else {
                                    return CONNECTION_TYPE_MOBILE_UNKNOWN;
                                }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return CONNECTION_TYPE_UNKNOWN;
    }

    /**
     * 获取网络服务提供商
     */
    private static final String CARRIER_UNKNOWN = "未知"; // 未知
    private static final String CARRIER_CHINA_MOBILE = "中国移动"; // 中国移动
    private static final String CARRIER_CHINA_UNICOM = "中国联通"; // 中国联通
    private static final String CARRIER_CHINA_TELECOM = "中国电信"; // 中国电信

    /**
     * 获取网络服务提供商
     * <p>
     * 需要加入权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"/> <BR>
     *
     * @return 1, 代表中国移动，2，代表中国联通，3，代表中国电信，0，代表未知
     */
    public static String getCarrier(Context context) {
        try {
            String imsi = getIMSI(context);
            if (TextUtils.isEmpty(imsi)) {
                return CARRIER_UNKNOWN;
            } else {
                // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）
                // （也称为“MCC/MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC，获取手机服务商信息
                // IMSI号前面3位460是国家，紧接着后面2位00运营商代码
                if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) { // 中国移动
                    return CARRIER_CHINA_MOBILE;
                } else if (imsi.startsWith("46001") || imsi.startsWith("46006")) { // 中国联通
                    return CARRIER_CHINA_UNICOM;
                } else if (imsi.startsWith("46003") || imsi.startsWith("46005")) { // 中国电信
                    return CARRIER_CHINA_TELECOM;
                } else {
                    return CARRIER_UNKNOWN;
                }
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return CARRIER_UNKNOWN;
    }

    /**
     * 获取屏幕方向
     */
    private static final int ORIENTATION_VERTICAL = 0; // 竖屏
    private static final int ORIENTATION_HORIZONTAL = 1; // 横屏

    /**
     * 获取屏幕方向
     * <p>
     * orientation
     * 0–竖屏
     * 1–横屏
     */
    public static int getScreenOrientation(Context context) {
        try {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
                return ORIENTATION_VERTICAL;
            } else { // 横屏
                return ORIENTATION_HORIZONTAL;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return ORIENTATION_VERTICAL;
    }

    /**
     * 获取AndroidId
     */
    public static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取手机设备厂商
     */
    public static String getManufacturer() {
        try {
            return android.os.Build.MANUFACTURER;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取手机设备型号
     */
    public static String getModel() {
        try {
            return android.os.Build.MODEL;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取手机操作系统版本
     */
    public static String getOSV() {
        try {
            return android.os.Build.VERSION.RELEASE;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return "";
    }

    /**
     * 获取当前系统时间，默认13位
     */
    public static long getSystemTime() {
        try {
            return System.currentTimeMillis();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return 0L;
    }

    /**
     * 手机震动
     */
    public static void vibrate(final Context context, long milliseconds) {
        try {
            if (AppUtils.checkMissingPermission(context, Manifest.permission.VIBRATE)) {
                return;
            }
            Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(milliseconds);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    /**
     * 手机截屏
     */
    public static Bitmap takeScreenShot(Activity activity) {
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            int statusBarHeight = getStatusBarHeight(activity);
            int width = activity.getResources().getDisplayMetrics().widthPixels;
            int height = activity.getResources().getDisplayMetrics().heightPixels;
            return Bitmap.createBitmap(bitmap, 0, statusBarHeight, width, height - statusBarHeight);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
        return null;
    }

    // ******************************** getMemoryInfoStr ********************************

    /**
     * getMemoryInfoStr
     */
    public static String getMemoryInfoStr(Context context) {
        StringBuilder builder = new StringBuilder();

        // Runtime.getRuntime().maxMemory()
        long maxMemory = Runtime.getRuntime().maxMemory();
        builder.append("maxMemory -> ").append(maxMemory).append("B").append("\n");
        builder.append("maxMemory -> ").append(maxMemory / 1024 / 1024).append("MB").append("\n");
        builder.append("maxMemory -> ").append(maxMemory >> 10 >> 10).append("MB").append("\n");
        builder.append("maxMemory -> ").append(maxMemory >> 20).append("MB").append("\n");

        // android.os.Process
        int myPid = android.os.Process.myPid();
        int myUid = android.os.Process.myUid();
        int myTid = android.os.Process.myTid();
        builder.append("myPid -> ").append(myPid).append("\n");
        builder.append("myUid -> ").append(myUid).append("\n");
        builder.append("myTid -> ").append(myTid).append("\n");

        // ActivityManager
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        builder.append("memoryClass -> ").append(memoryClass).append("MB").append("\n");
        builder.append("largeMemoryClass -> ").append(largeMemoryClass).append("MB").append("\n");

        // activityManager.getMemoryInfo
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMem = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            totalMem = memoryInfo.totalMem;
        }
        long availMem = memoryInfo.availMem;
        long threshold = memoryInfo.threshold;
        boolean lowMemory = memoryInfo.lowMemory;
        builder.append("totalMem-系统总共内存 -> ").append(totalMem >> 20).append("MB").append("\n");
        builder.append("availMem-系统剩余内存 -> ").append(availMem >> 20).append("MB").append("\n");
        builder.append("threshold-当系统剩余内存低于 -> ").append(threshold >> 20).append("MB时就看成低内存运行").append("\n");
        builder.append("lowMemory-系统是否处于低内存运行 -> ").append(lowMemory).append("\n");

        // activityManager.getRunningAppProcesses()
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (int i = 0; i < list.size(); i++) {
            builder.append("pid -> ").append(list.get(i).pid).append("\n");
            builder.append("uid -> ").append(list.get(i).uid).append("\n");
            builder.append("processName -> ").append(list.get(i).processName).append("\n");
            builder.append("pkgList -> ").append(ConvertUtils.parseString(list.get(i).pkgList, ",")).append("\n");
        }

        // Debug
        long nativeHeapSize = Debug.getNativeHeapSize();
        long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
        long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();
        builder.append("nativeHeapSize-当前进程navtive堆中已使用的内存大小 -> ").append(nativeHeapSize >> 10).append("KB").append("\n");
        builder.append("nativeHeapAllocatedSize-当前进程navtive堆中已经剩余的内存大小 -> ").append(nativeHeapAllocatedSize >> 10).append("KB").append("\n");
        builder.append("nativeHeapFreeSize-当前进程navtive堆本身总的内存大小 -> ").append(nativeHeapFreeSize >> 10).append("KB").append("\n");

        // Debug.getMemoryInfo
        Debug.MemoryInfo memoryInfoDebug = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfoDebug);
        int dalvikPrivateDirty = memoryInfoDebug.dalvikPrivateDirty;
        int dalvikPss = memoryInfoDebug.dalvikPss;
        int dalvikSharedDirty = memoryInfoDebug.dalvikSharedDirty;
        int nativePrivateDirty = memoryInfoDebug.nativePrivateDirty;
        int nativePss = memoryInfoDebug.nativePss;
        int nativeSharedDirty = memoryInfoDebug.nativeSharedDirty;
        int otherPrivateDirty = memoryInfoDebug.otherPrivateDirty;
        int otherPss = memoryInfoDebug.otherPss;
        int otherSharedDirty = memoryInfoDebug.otherSharedDirty;
        builder.append("dalvikPrivateDirty -> ").append(dalvikPrivateDirty).append("KB").append("\n");
        builder.append("dalvikPss -> ").append(dalvikPss).append("KB").append("\n");
        builder.append("dalvikSharedDirty -> ").append(dalvikSharedDirty).append("KB").append("\n");
        builder.append("nativePrivateDirty -> ").append(nativePrivateDirty).append("KB").append("\n");
        builder.append("nativePss -> ").append(nativePss).append("KB").append("\n");
        builder.append("nativeSharedDirty -> ").append(nativeSharedDirty).append("KB").append("\n");
        builder.append("otherPrivateDirty -> ").append(otherPrivateDirty).append("KB").append("\n");
        builder.append("otherPss -> ").append(otherPss).append("KB").append("\n");
        builder.append("otherSharedDirty -> ").append(otherSharedDirty).append("KB").append("\n");

        return builder.toString();
    }

    // ******************************** getBuildInfoStr ********************************

    /**
     * getBuildInfoStr()
     */
    public static String getBuildInfoStr() {
        StringBuilder builder = new StringBuilder();

        builder.append("** Build **").append("\n");
        builder.append("getManufacturer = ").append(getManufacturer()).append("\n");
        builder.append("getManufacturer = ").append(getModel()).append("\n");

        builder.append("** Build.class **").append("\n");
        builder.append(ConvertUtils.parseString(android.os.Build.class));

        builder.append("** Build.VERSION.class **").append("\n");
        builder.append(ConvertUtils.parseString(android.os.Build.VERSION.class));

        builder.append("** Build.VERSION_CODES.class **").append("\n");
        builder.append(ConvertUtils.parseString(android.os.Build.VERSION_CODES.class));

//        // android.os.Build
//        builder.append("** android.os.Build.class **").append("\n");
//        builder.append("BOARD（运营商） = ").append(android.os.Build.BOARD).append("\n");
//        builder.append("BOOTLOADER = ").append(android.os.Build.BOOTLOADER).append("\n");
//        builder.append("BRAND = ").append(android.os.Build.BRAND).append("\n");
//        builder.append("CPU_ABI = ").append(android.os.Build.CPU_ABI).append("\n");
//        builder.append("CPU_ABI2 = ").append(android.os.Build.CPU_ABI2).append("\n");
//        builder.append("DEVICE（驱动） = ").append(android.os.Build.DEVICE).append("\n");
//        builder.append("DISPLAY（显示） = ").append(android.os.Build.DISPLAY).append("\n");
//        builder.append("FINGERPRINT（指纹） = ").append(android.os.Build.FINGERPRINT).append("\n");
//        builder.append("HARDWARE（硬件） = ").append(android.os.Build.HARDWARE).append("\n");
//        builder.append("HOST = ").append(android.os.Build.HOST).append("\n");
//        builder.append("ID = ").append(android.os.Build.ID).append("\n");
//        builder.append("MANUFACTURER（生产厂家） = ").append(android.os.Build.MANUFACTURER).append("\n");
//        builder.append("MODEL（机型） = ").append(android.os.Build.MODEL).append("\n");
//        builder.append("PRODUCT = ").append(android.os.Build.PRODUCT).append("\n");
//        builder.append("RADIO = ").append(android.os.Build.RADIO).append("\n");
//        builder.append("SERIAL = ").append(android.os.Build.SERIAL).append("\n");
//        builder.append("TAGS = ").append(android.os.Build.TAGS).append("\n");
//        builder.append("TIME = ").append(android.os.Build.TIME).append("\n");
//        builder.append("TYPE = ").append(android.os.Build.TYPE).append("\n");
//        builder.append("UNKNOWN = ").append(android.os.Build.UNKNOWN).append("\n");
//        builder.append("USER = ").append(android.os.Build.USER).append("\n");
//        builder.append("VERSION.RELEASE（固件版本） = ").append(android.os.Build.VERSION.RELEASE).append("\n");
//        builder.append("VERSION.CODENAME（固件版本） = ").append(android.os.Build.VERSION.CODENAME).append("\n");
//        builder.append("VERSION.INCREMENTAL（基带版本） = ").append(android.os.Build.VERSION.INCREMENTAL).append("\n");
//        builder.append("VERSION.SDK（SDK版本） = ").append(android.os.Build.VERSION.SDK).append("\n");
//        builder.append("VERSION.SDK_INT（SDK版本） = ").append(android.os.Build.VERSION.SDK_INT).append("\n");

        return builder.toString();
    }

}
