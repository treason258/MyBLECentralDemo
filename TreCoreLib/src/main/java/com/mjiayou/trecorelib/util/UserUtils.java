package com.mjiayou.trecorelib.util;

import android.content.Context;
import android.text.TextUtils;

import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.bean.entity.TCUser;
import com.mjiayou.trecorelib.event.UserLoginStatusEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by treason on 16/5/14.
 */
public class UserUtils {

    public static final String TAG = "UserUtil";

    /**
     * 用户登陆之后的操作
     */
    public static void doLogin(String token) {
        UserUtils.setToken(token);

        EventBus.getDefault().post(new UserLoginStatusEvent(true));
    }

    /**
     * 获取用户详细信息之后的操作
     */
    public static void doGetUserInfo(TCUser user) {
        UserUtils.setUserID(user.getId());
        UserUtils.setUserInfo(user);
    }

    /**
     * 用户注销登陆之后的操作
     */
    public static void doLogout() {
        UserUtils.setToken("");

        EventBus.getDefault().post(new UserLoginStatusEvent(false));
    }

    // ******************************** 判断用户登录状态 ********************************

    /**
     * 判断当前用户是否登录（约定以SharedUtil中getAccountToken值是否有效为准）
     * <p/>
     * true：已登录状态；
     * <br/>
     * false：未登录状态；
     */
    public static boolean checkLoginStatus(Context context) {
        boolean isLogin = false;

        String token = UserUtils.getToken();
        if (!TextUtils.isEmpty(token)) {
            isLogin = true;
        }

        return isLogin;
    }

    /**
     * 判断当前用户是否登录，如果未登录则跳转到登录页面
     * <p/>
     * true：需要登录，则自动跳转到登陆页面，当前页面可以return；
     * <br/>
     * false：不需要登录，继续执行；
     */
    public static boolean checkNeedLogin(Context context) {
        boolean isNeedLogin = false;

        boolean isLogin = checkLoginStatus(context);
        if (!isLogin) {
            isNeedLogin = true;
            // Router.openTestUserLoginActivity(context);
        }

        return isNeedLogin;
    }

    // ******************************** 缓存用户信息 ********************************

    /**
     * Token
     */
    public static void setToken(String token) {
        SharedUtils.get(TCApp.get()).setAccountToken(token);
    }

    public static String getToken() {
        return SharedUtils.get(TCApp.get()).getAccountToken();
    }

    /**
     * UserID
     */
    public static void setUserID(String userID) {
        SharedUtils.get(TCApp.get()).setAccountUserID(userID);
    }

    public static String getUserID() {
        return SharedUtils.get(TCApp.get()).getAccountUserID();
    }

    /**
     * 用户信息
     */
    public static void setUserInfo(TCUser user) {
        SharedUtils.get(TCApp.get()).setAccountUserInfo(user);
    }

    public static TCUser getUserInfo() {
        return SharedUtils.get(TCApp.get()).getAccountUserInfo();
    }

    /**
     * UUID
     */
    public static void setUUID(String uuid) {
        SharedUtils.get(TCApp.get()).setAccountUUID(uuid);
    }

    public static String getUUID() {
        return SharedUtils.get(TCApp.get()).getAccountUUID();
    }
}
