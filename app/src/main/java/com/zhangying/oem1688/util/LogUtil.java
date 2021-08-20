package com.zhangying.oem1688.util;

import android.util.Log;

import com.zhangying.oem1688.constant.BuildConfig;


public class LogUtil {

    public static void i(String tag , String msg){
        if(BuildConfig.debug){
            Log.i(tag ,msg);
        }
    }

    public static void w(String tag , String msg){
        if(BuildConfig.debug){
            Log.w(tag ,msg);
        }
    }

    public static void e(String tag , String msg){
        Log.e(tag ,msg);
    }
}
