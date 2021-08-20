package com.zhangying.oem1688.util;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by xialihao on 2018/11/16.
 */
public class AutoForcePermissionUtils {


    /**
     * 申请权限
     * @param activity
     * @param callback
     * @param permissions
     */
    public static void requestPermissions(FragmentActivity activity, PermissionCallback callback, String... permissions) {

        RxPermissions  rxPermissions = new RxPermissions(activity);
        requestInternal(callback, rxPermissions, permissions);
    }



    private static void requestInternal(PermissionCallback callback, RxPermissions rxPermissions, String[] permissions) {
        rxPermissions.request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        if (callback != null) {
                            callback.onPermissionGranted();
                        }
                    } else {
                        for(int i = 0 ; i < permissions.length ;i++){
                            LogUtil.e("失败---", permissions[i]);
                        }
                        if (callback != null) {
                            callback.onPermissionDenied();
                        }
                    }
                });
    }



    public interface PermissionCallback {

        void onPermissionGranted();

        void onPermissionDenied();
    }
}
