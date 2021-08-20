package com.zhangying.oem1688.util;

import android.content.Context;

import com.tencent.mmkv.MMKV;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.zhangying.oem1688.view.activity.entry.LoginActivity;

public class TokenUtils {

    private static String sToken;

    private static final String KEY_TOKEN = "com.xuexiang.xuidemo.utils.KEY_TOKEN";

    private TokenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化Token信息
     */
    public static void init(Context context) {
        MMKV.initialize(context);
        sToken = MMKV.defaultMMKV().decodeString(KEY_TOKEN, "");
    }

    public static void setToken(String token) {
        sToken = token;
        MMKV.defaultMMKV().putString(KEY_TOKEN, token);
    }

    public static void clearToken() {
        sToken = null;
        MMKV.defaultMMKV().remove(KEY_TOKEN);
    }

    public static String getToken() {
        return sToken;
    }

    public static boolean hasToken() {
        return MMKV.defaultMMKV().containsKey(KEY_TOKEN);
    }

    /**
     * 处理登录成功的事件
     *
     * @param token 账户信息
     */
    public static boolean handleLoginSuccess(String token) {
        if (!StringUtils.isEmpty(token)) {
            ToastUtil.showToast("登录成功！");
            setToken(token);
            return true;
        } else {
            ToastUtil.showToast("登录失败！");
            return false;
        }
    }

    /**
     * 处理登出的事件
     */
    public static void handleLogoutSuccess() {
        //登出时，清除账号信息
        clearToken();
        ToastUtil.showToast("登出成功！");
        // 登出清除一下隐私政策
        SettingSPUtils.getInstance().setIsAgreePrivacy(false);
        //跳转到登录页
        ActivityUtils.startActivity(LoginActivity.class);
    }

}
