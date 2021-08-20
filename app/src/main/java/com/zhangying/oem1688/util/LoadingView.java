package com.zhangying.oem1688.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangying.oem1688.R;

public class LoadingView extends ProgressDialog {

    private TextView loadText;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        setCancelable(true);//点击返回键消失
        setCanceledOnTouchOutside(false);//点击外部不消失

        setContentView(R.layout.loading);//loading的xml文件pb_load
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.dimAmount = 0f;//不要背景变暗
        getWindow().setAttributes(params);
        loadText = findViewById(R.id.tv_load_dialog);
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }

    public void setLoadMesg(String msg){
        if(!TextUtils.isEmpty(msg)) {
            loadText.setText(msg);
            loadText.setVisibility(View.VISIBLE);
        }
    }
}
