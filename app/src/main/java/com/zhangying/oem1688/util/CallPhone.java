package com.zhangying.oem1688.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class CallPhone {
    public static void call(Context context, List<String> listPhone, List<String> listTel) {
        if (Build.VERSION.SDK_INT >= 23) {
            AutoForcePermissionUtils.requestPermissions((FragmentActivity) context, new AutoForcePermissionUtils.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    String phone = (listPhone != null && listPhone.size() > 0) ? listPhone.get(listPhone.size() - 1) : listTel.get(listTel.size() - 1);
                    intent(context, phone);
                }

                @Override
                public void onPermissionDenied() {
                    ToastUtil.showToast("权限被拒绝，无法拨打电话！");
                }
            }, new String[]{Manifest.permission.CALL_PHONE});
        } else {
            String phone = (listPhone != null && listPhone.size() > 0) ? listPhone.get(listPhone.size() - 1) : listTel.get(listTel.size() - 1);
            intent(context, phone);
        }
    }

    public static void call(Context context, String phone) {
        if (Build.VERSION.SDK_INT >= 23) {
            AutoForcePermissionUtils.requestPermissions((FragmentActivity) context, new AutoForcePermissionUtils.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                    intent(context, phone);
                }

                @Override
                public void onPermissionDenied() {
                    ToastUtil.showToast("权限被拒绝，无法拨打电话！");
                }
            }, new String[]{Manifest.permission.CALL_PHONE});
        } else {
            intent(context, phone);
        }
    }

    private static void intent(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }
}
