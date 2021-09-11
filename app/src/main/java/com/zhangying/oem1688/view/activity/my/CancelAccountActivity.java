package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.EventBusStyeSingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class CancelAccountActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.checksign_imageview)
    ImageView imageCheckSign;

    private static String pageUrl;
    private static String pageTitle;
    private boolean isSelect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancel_account;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnCancel.setEnabled(false);
        webView.loadUrl(pageUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                titleTV.setText(view.getTitle());
            }
        });

        titleTV.setText(pageTitle);
    }

    @OnClick({R.id.btn_cancel, R.id.checksign_rl, R.id.bacK_RL})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.checksign_rl://注销协议
                isSelect = !isSelect;
                if (!isSelect) {
                    btnCancel.setEnabled(false);
                    imageCheckSign.setVisibility(View.GONE);
                } else {
                    btnCancel.setEnabled(true);
                    imageCheckSign.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_cancel:
                doExecCancel();
                break;
        }
    }

    //退出登录
    private void doExecCancel() {
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .cancel_account(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        if (data.isDone()) {
                            TokenUtils.clearToken();
                            ToastUtil.showToast("账号注销成功");
                            AppManagerUtil.getInstance().finishAllActivity();
                            //返回主界面
                            EvenBusBean evenBusBean = new EvenBusBean();
                            evenBusBean.setType(0);
                            EventBus.getDefault().post(evenBusBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    public static void simpleActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CancelAccountActivity.class);
        pageUrl = url;
        pageTitle = title;
        context.startActivity(intent);
    }
}