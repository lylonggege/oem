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
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//竖直居中
                View view = toast.getView();
                //获取view的背景的shap资源
                GradientDrawable myGrad = (GradientDrawable) view.getBackground();
                //动态设置shap资源的填充颜色
                myGrad.setColor(Color.parseColor("#7f000000"));
                //动态设置shap的圆角属性
                myGrad.setCornerRadius(20);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#ffffff"));
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast toast= Toast.makeText(MyApplication.myApplicationContext, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);//竖直居中
            View view = toast.getView();
            //获取view的背景的shap资源
            GradientDrawable myGrad = (GradientDrawable) view.getBackground();
            //动态设置shap资源的填充颜色
            myGrad.setColor(Color.parseColor("#7f000000"));
            //动态设置shap的圆角属性
            myGrad.setCornerRadius(20);
            TextView text = (TextView) view.findViewById(android.R.id.message);
            text.setTextColor(Color.parseColor("#ffffff"));
            toast.show();
            Looper.loop();
        }
    }
}
