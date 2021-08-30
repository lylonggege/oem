package com.zhangying.oem1688.view.activity.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.util.SettingSPUtils;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.view.activity.MainActivity;

public class SplashActivity extends BaseSplashActivity {

    public final static String KEY_IS_DISPLAY = "key_is_display";
    public final static String KEY_ENABLE_ALPHA_ANIM = "key_enable_alpha_anim";
    private boolean isDisplay = false;

    @Override
    protected void onCreateActivity() {
        isDisplay = getIntent().getBooleanExtra(KEY_IS_DISPLAY, isDisplay);
        boolean enableAlphaAnim = getIntent().getBooleanExtra(KEY_ENABLE_ALPHA_ANIM, false);
        SettingSPUtils spUtil = SettingSPUtils.getInstance();
        if (spUtil.isFirstOpen()) {
            spUtil.setIsFirstOpen(false);
            ActivityUtils.startActivity(UserGuideActivity.class);
            finish();

        } else {
            if (enableAlphaAnim) {
                initSplashView(R.drawable.configl_bg_spash);
            } else {
                initSplashView(R.drawable.configl_bg_spash);
            }
            startSplash(enableAlphaAnim);
        }
    }

    @Override
    protected void onSplashFinished() {
//        if (!isDisplay) {
//            if (TokenUtils.hasToken()) {
//                ActivityUtils.startActivity(MainActivity.class);
//            } else {
//                ActivityUtils.startActivity(LoginActivity.class);
//            }
//        }
        ActivityUtils.startActivity(MainActivity.class);
        finish();
        this.overridePendingTransition(0, 0);
    }
}