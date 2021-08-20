package com.zhangying.oem1688.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

public class AppInstallUtil {

    /*根据报名判断是否安装*/
    public static boolean isInstall(Context context , String packageName){
        if(TextUtils.isEmpty(packageName))return false;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*根据安装应用列表判断是否暗转*/
    public static boolean isInstall2(Context context , String packageName){
        if(TextUtils.isEmpty(packageName))return false;
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
        for(ApplicationInfo applicationInfo: installedApplications){
            if(applicationInfo.packageName.toLowerCase().equals(packageName)){
                return true;
            }
        }
        return false;
    }
}
