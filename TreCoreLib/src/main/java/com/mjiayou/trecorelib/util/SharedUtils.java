package com.mjiayou.trecorelib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.bean.entity.TCUser;
import com.mjiayou.trecorelib.json.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SharedPreferences操作封装--OK
 */
public class SharedUtils {

    private static final String TAG = SharedUtils.class.getSimpleName();

    // APP配置
    private SharedPreferences mSharedConfig;
    private final String PREFERENCES_CONFIG = "preferences_config";
    private final String KEY_CONFIG_IS_FIRST = "key_config_is_first"; // 是否第一次使用APP
    private final String KEY_CONFIG_VERSION_CODE = "key_config_version_code"; // 本地记录版本号
    private final String KEY_CONFIG_THEME_ID = "key_config_theme_id"; // 当前主题ID
    // 个人账户信息
    private SharedPreferences mSharedAccount;
    private final String PREFERENCES_ACCOUNT = "preferences_account";
    private final String KEY_ACCOUNT_USERNAME = "key_account_username"; // 用户名
    private final String KEY_ACCOUNT_PASSWORD = "key_account_password"; // 密码
    private final String KEY_ACCOUNT_TOKEN = "key_account_token"; // Token
    private final String KEY_ACCOUNT_USER_ID = "key_account_user_id"; // UserID
    private final String KEY_ACCOUNT_USER_INFO = "key_account_user_info"; // 用户个人信息
    // 本地缓存数据
    private SharedPreferences mSharedCache;
    private final String PREFERENCE_CACHE = "preference_cache";
    private final String KEY_CACHE_SEARCH_HISTORY = "key_cache_search_history";
    private final String KEY_CACHE_USER_LIST = "key_cache_user_list";
    // 通用
    private SharedPreferences mSharedCommon;
    private final String PREFERENCE_COMMON = "preference_common";
    private final String KEY_COMMON_TEST = "key_common_test";
    private final String kEY_COMMON_USER = "key_common_user";

    // var
    private static SharedUtils mInstance;

    /**
     * 构造函数
     */
    private SharedUtils() {
        this.mSharedConfig = TCApp.get().getSharedPreferences(PREFERENCES_CONFIG, Context.MODE_PRIVATE);
        this.mSharedAccount = TCApp.get().getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        this.mSharedCache = TCApp.get().getSharedPreferences(PREFERENCE_CACHE, Context.MODE_PRIVATE);
        this.mSharedCommon = TCApp.get().getSharedPreferences(PREFERENCE_COMMON, Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取实例
     */
    public static SharedUtils get() {
        if (mInstance == null) {
            synchronized (SharedUtils.class) {
                if (mInstance == null) {
                    mInstance = new SharedUtils();
                }
            }
        }
        return mInstance;
    }

    // ******************************** 封装 ********************************

    /**
     * 日志规范
     */
    private void logSet(String key, Object value) {
        LogUtils.i(TAG, "SET " + key + " -> " + value);
    }

    private void logSetFailed() {
        LogUtils.i(TAG, "SET failed");
    }

    private void logGet(String key, Object value) {
        LogUtils.i(TAG, "GET " + key + " -> " + value);
    }

    private void logGetFailed() {
        LogUtils.i(TAG, "GET failed");
    }

    private void logRemovePrepare(String key) {
        LogUtils.i(TAG, "REMOVE prepare " + key);
    }

    private void logRemoveSucceed() {
        LogUtils.i(TAG, "REMOVE succeed");
    }

    private void logRemoveFailed() {
        LogUtils.i(TAG, "REMOVE failed");
    }

    /**
     * 保存数据
     */
    private void setShared(SharedPreferences sharedPreferences, String key, Object value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key) && value != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            }
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    private void setShared(SharedPreferences sharedPreferences, String key, String value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key) && value != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    private void setShared(SharedPreferences sharedPreferences, String key, boolean value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    private void setShared(SharedPreferences sharedPreferences, String key, int value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    private void setShared(SharedPreferences sharedPreferences, String key, float value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(key, value);
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    private void setShared(SharedPreferences sharedPreferences, String key, long value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(key, value);
            editor.apply();
            logSet(key, value);
        } else {
            logSetFailed();
        }
    }

    /**
     * 读取数据
     */
    private Object getShared(SharedPreferences sharedPreferences, String key, Object defValue) {
        try {
            Object value = new Object();
            if (defValue instanceof String) {
                value = sharedPreferences.getString(key, (String) defValue);
            } else if (defValue instanceof Boolean) {
                value = sharedPreferences.getBoolean(key, (Boolean) defValue);
            } else if (defValue instanceof Integer) {
                value = sharedPreferences.getInt(key, (Integer) defValue);
            } else if (defValue instanceof Float) {
                value = sharedPreferences.getFloat(key, (Float) defValue);
            } else if (defValue instanceof Long) {
                value = sharedPreferences.getLong(key, (Long) defValue);
            }
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    private String getShared(SharedPreferences sharedPreferences, String key, String defValue) {
        try {
            String value = sharedPreferences.getString(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    private boolean getShared(SharedPreferences sharedPreferences, String key, boolean defValue) {
        try {
            boolean value = sharedPreferences.getBoolean(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    private int getShared(SharedPreferences sharedPreferences, String key, int defValue) {
        try {
            int value = sharedPreferences.getInt(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    private float getShared(SharedPreferences sharedPreferences, String key, float defValue) {
        try {
            float value = sharedPreferences.getFloat(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    private long getShared(SharedPreferences sharedPreferences, String key, long defValue) {
        try {
            long value = sharedPreferences.getLong(key, defValue);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logGetFailed();
        }
        return defValue;
    }

    /**
     * 清除全部数据
     */
    public void removeShared(SharedPreferences sharedPreferences) {
        try {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> keySet = sharedPreferences.getAll().keySet();
            for (String key : keySet) {
                editor.remove(key);
                logRemovePrepare(key);
            }
            editor.apply();
            logRemoveSucceed();
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
            logRemoveFailed();
        }
    }

    /**
     * 清除全部
     */
    public void clearAll() {
        clearConfig();
        clearAccount();
        clearCache();
        clearCommon();
    }

    // ******************************** shortcut ********************************

    // **************** mSharedConfig start ****************

    /**
     * 是否第一次使用APP
     */
    public void setConfigIsFirst(boolean isFirst) {
        setShared(mSharedConfig, KEY_CONFIG_IS_FIRST, isFirst);
    }

    public boolean getConfigIsFirst() {
        return getShared(mSharedConfig, KEY_CONFIG_IS_FIRST, true);
    }

    /**
     * 记录版本号
     */
    public void setConfigVersionCode(int versionCode) {
        setShared(mSharedConfig, KEY_CONFIG_VERSION_CODE, versionCode);
    }

    public int getConfigVersionCode() {
        return getShared(mSharedConfig, KEY_CONFIG_VERSION_CODE, -1); // 默认返回-1，表示没有旧版本
    }

    /**
     * 主题ID
     */
    public void setConfigThemeId(int themeId) {
        setShared(mSharedConfig, KEY_CONFIG_THEME_ID, themeId);
    }

    public int getConfigThemeId() {
        return getShared(mSharedConfig, KEY_CONFIG_THEME_ID, ThemeUtils.THEME_DEFAULT);
    }

    /**
     * 清除配置信息
     */
    public void clearConfig() {
        removeShared(mSharedConfig);
    }

    // **************** mSharedAccount ****************

    /**
     * 用户名
     */
    public void setAccountUsername(String username) {
        setShared(mSharedAccount, KEY_ACCOUNT_USERNAME, username);
    }

    public String getAccountUsername() {
        return getShared(mSharedAccount, KEY_ACCOUNT_USERNAME, "");
    }

    /**
     * 密码
     */
    public void setAccountPassword(String password) {
        setShared(mSharedAccount, KEY_ACCOUNT_PASSWORD, password);
    }

    public String getAccountPassword() {
        return getShared(mSharedAccount, KEY_ACCOUNT_PASSWORD, "");
    }

    /**
     * Token
     */
    public void setAccountToken(String token) {
        setShared(mSharedAccount, KEY_ACCOUNT_TOKEN, token);
    }

    public String getAccountToken() {
        return getShared(mSharedAccount, KEY_ACCOUNT_TOKEN, "");
    }

//    /**
//     * UserID
//     */
//    public void setAccountUserID(String userID) {
//        setShared(mSharedAccount, KEY_ACCOUNT_USER_ID, userID);
//    }
//
//    public String getAccountUserID() {
//        return getShared(mSharedAccount, KEY_ACCOUNT_USER_ID, "");
//    }
//
//    /**
//     * 用户信息
//     */
//    public void setAccountUserInfo(TCUser user) {
//        String data = GsonHelper.get().toJson(user);
//        setShared(mSharedAccount, KEY_ACCOUNT_USER_INFO, data);
//    }
//
//    public TCUser getAccountUserInfo() {
//        String data = getShared(mSharedAccount, KEY_ACCOUNT_USER_INFO, "");
//        if (!TextUtils.isEmpty(data)) {
//            return GsonHelper.get().toObject(data, TCUser.class);
//        }
//        return null;
//    }

    /**
     * 清除账户信息
     */
    public void clearAccount() {
        removeShared(mSharedAccount);
    }

    // **************** mSharedCache ****************

    /**
     * 搜索关键字历史记录
     */
    public void setCacheSearchHistory(List<String> list) {
        String data = JsonParser.get().toJson(list);
        setShared(mSharedCache, KEY_CACHE_SEARCH_HISTORY, data);
    }

    public List<String> getCacheSearchHistory() {
        String data = getShared(mSharedCache, KEY_CACHE_SEARCH_HISTORY, "");
        if (!TextUtils.isEmpty(data)) {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            return JsonParser.get().toObject(data, type);
        }
        return null;
    }

    /**
     * 缓存用户列表
     */
    public void setCacheUserList(List<TCUser> list) {
        String data = JsonParser.get().toJson(list);
        setShared(mSharedCache, KEY_CACHE_USER_LIST, data);
    }

    public List<TCUser> getCacheUserList() {
        String data = getShared(mSharedCache, KEY_CACHE_USER_LIST, "");
        if (!TextUtils.isEmpty(data)) {
            Type type = new TypeToken<ArrayList<TCUser>>() {
            }.getType();
            return JsonParser.get().toObject(data, type);
        }
        return null;
    }

    /**
     * 清除缓存信息
     */
    public void clearCache() {
        removeShared(mSharedCache);
    }

    // **************** mSharedCommon ****************

    /**
     * CommonTest
     */
    public void setCommonTest(String value) {
        setShared(mSharedCommon, KEY_COMMON_TEST, value);
    }

    public String getCommonTest() {
        return getShared(mSharedCommon, KEY_COMMON_TEST, "");
    }

    /**
     * Common
     */
    public void setCommon(String key, String value) {
        setShared(mSharedCommon, key, value);
    }

    public String getCommon(String key) {
        return getShared(mSharedCommon, key, "");
    }

    /**
     * setTcUser
     */
    public void setTcUser(TCUser tcUser) {
        String data = "";
        if (tcUser != null) {
            data = JsonParser.get().toJson(tcUser);
        }
        SharedUtils.get().setCommon(kEY_COMMON_USER, data);
    }

    public TCUser getTcUser() {
        String data = SharedUtils.get().getCommon(kEY_COMMON_USER);
        if (!TextUtils.isEmpty(data)) {
            return JsonParser.get().toObject(data, TCUser.class);
        } else {
            return null;
        }
    }

    /**
     * 清除信息
     */
    public void clearCommon() {
        removeShared(mSharedCommon);
    }
}
