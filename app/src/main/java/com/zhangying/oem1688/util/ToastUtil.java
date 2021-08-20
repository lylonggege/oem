package com.zhangying.oem1688.util;

import android.os.Looper;
import android.widget.Toast;

import com.zhangying.oem1688.MyApplication;

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
