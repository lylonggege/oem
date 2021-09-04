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

        //设备统计信息
        createUniqueID();
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

    private void createUniqueID(){
        //we make this look like a valid IMEI//13 digits
        String m_szDevIDShort = "35" +
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10;

        //统计设备
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("did",m_szDevIDShort);
        hashMap.put("dtype","2");
        hashMap.put("ctype",Build.MODEL);
        hashMap.put("cversion",android.os.Build.VERSION.SDK_INT);

        PackageInfo packInfo = getPackageInfo(this);
        hashMap.put("aversion",packInfo != null ? packInfo.versionCode : "");
        RemoteRepository.getInstance()
                .count_device(hashMap)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean newscontBean) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
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