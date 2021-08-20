package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.button.roundbutton.RoundButton;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xutil.app.ActivityUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.CacheUtil;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.cache_tv)
    TextView cache_tv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("设置");
        try {
            String totalCacheSize = CacheUtil.getTotalCacheSize(this);
            cache_tv.setText(totalCacheSize);
        } catch (Exception e) {
            cache_tv.setText("0KB");
            e.printStackTrace();
        }
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL, R.id.bangdingshouji_rl,
            R.id.re_password_rl, R.id.about_rl, R.id.contact_rl, R.id.clearcache_rl, R.id.logoff_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.bangdingshouji_rl:
                ResetpasswordActivity.simpleActivity(this, 1);
                break;
            case R.id.re_password_rl:
                ResetpasswordActivity.simpleActivity(this, 0);
                break;
            case R.id.about_rl:
                MyAboutDGBActivity.simpleActivity(this);
                break;
            case R.id.contact_rl:
                MyCustomerServiceActivity.simpleActivity(this);
                break;
            case R.id.clearcache_rl:
                CacheUtil.clearAllCache(this);

                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();
                CookieSyncManager.createInstance(this.getApplicationContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.removeSessionCookies(null);
                    cookieManager.removeAllCookie();
                    cookieManager.flush();
                } else {
                    cookieManager.removeSessionCookies(null);
                    cookieManager.removeAllCookie();
                    CookieSyncManager.getInstance().sync();
                }

                WebStorage.getInstance().deleteAllData();

                try {
                    String totalCacheSize = CacheUtil.getTotalCacheSize(this);
                    cache_tv.setText(totalCacheSize);
                } catch (Exception e) {
                    cache_tv.setText("0KB");
                    e.printStackTrace();
                }
                break;
            case R.id.logoff_tv:
                ajax_logout();

                break;
        }
    }

    private void ajax_logout() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        RemoteRepository.getInstance()
                .ajax_logout(map)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        if (data.isDone()) {
                            AppManagerUtil.getInstance().finishAllActivity();
                            ActivityUtils.startActivity(MainActivity.class);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }


}