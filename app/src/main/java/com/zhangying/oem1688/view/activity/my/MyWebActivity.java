package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.AboutBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class MyWebActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.bacK_RL)
    RelativeLayout bacKRL;
    @BindView(R.id.title_TV)
    TextView titleTV;

    private static String pageUrl;
    private static String pageTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.loadUrl(pageUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                titleTV.setText(view.getTitle());
            }
        });

        titleTV.setText(pageTitle);
        bacKRL.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                finish();
            }
        });
    }

    public static void simpleActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, MyWebActivity.class);
        pageUrl = url;
        pageTitle = title;
        context.startActivity(intent);
    }
}