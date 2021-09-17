package com.zhangying.oem1688.view.activity.entry;

import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.GlobalEntitySingleton;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.SettingSPUtils;
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

        setTheme(R.style.AppTheme_Launcher);

        if (spUtil.isFirstOpen()) {
            spUtil.setIsFirstOpen(false);
            ActivityUtils.startActivity(UserGuideActivity.class);
            finish();
        } else {
            if (enableAlphaAnim) {
                //initSplashView(R.drawable.configl_bg_spash);
            } else {
                //initSplashView(R.drawable.configl_bg_spash);
            }

            ScreenTools screenTools = ScreenTools.instance(this);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.configl_bg_spash); //图片资源
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.CENTER_VERTICAL;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面

            mWelcomeLayout.setGravity(Gravity.CENTER);
            mWelcomeLayout.addView(imageView); //动态添加图片
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