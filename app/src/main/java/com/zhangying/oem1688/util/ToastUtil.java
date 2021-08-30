package com.zhangying.oem1688.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xui.widget.toast.XToast;
import com.zhangying.oem1688.MyApplication;
import com.zhangying.oem1688.R;

public class ToastUtil {
    static Toast toast = null;

    public static void showToast(String msg) {
        try {
            if (toast == null) {
                toast = Toast.makeText(MyApplication.myApplicationContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(MyApplication.myApplicationContext, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
