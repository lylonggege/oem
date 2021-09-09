package com.zhangying.oem1688.view.activity.entry;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.SettingSPUtils;
import com.zhangying.oem1688.view.activity.MainActivity;

import java.util.HashMap;

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
            ScreenTools screenTools = ScreenTools.instance(this);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(screenTools.getScreenWidth(), screenTools.getScreenHeight()));  //设置图片宽高
            imageView.setImageResource(R.drawable.configl_bg_spash); //图片资源
            mWelcomeLayout.addView(imageView); //动态添加图片

            if (enableAlphaAnim) {
                //initSplashView(R.drawable.configl_bg_spash);
            } else {
                //initSplashView(R.drawable.configl_bg_spash);
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