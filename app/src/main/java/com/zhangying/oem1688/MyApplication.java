package com.zhangying.oem1688;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.xuexiang.xhttp2.XHttpSDK;
import com.xuexiang.xui.XUI;
import com.xuexiang.xutil.XUtil;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.db.StuDBHelper;
import com.zhangying.oem1688.util.SettingSPUtils;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.view.activity.TestActivity;

public class MyApplication extends Application {
    public static Context myApplicationContext = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        myApplicationContext = base;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initXUI();
        initUtils();
    }



    private void initXUI() {
        XUI.init(this); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

        XHttpSDK.init(this);   //初始化网络请求框架，必须首先执行
        XHttpSDK.setBaseUrl(BuildConfig.URL);  //设置网络请求的基础地址
    }

    /**
     * 初始化工具类
     */
    private static void initUtils() {
        TokenUtils.init(myApplicationContext);
    }

}
