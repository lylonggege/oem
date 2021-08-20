package com.zhangying.oem1688.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.LoadingView;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        AppManagerUtil.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManagerUtil.getInstance().finishActivity(this);
        if (loadingView != null) {
            if (loadingView.isShowing()) {
                loadingView.dismiss();
            }
            loadingView = null;
        }
    }

    protected void showLoading() {
        if (loadingView == null) {
            loadingView = new LoadingView(this, R.style.custom_dialog2);
        }
        loadingView.show();
    }

    protected void dissmissLoading() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }



}
