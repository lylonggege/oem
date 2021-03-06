package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.AboutBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAboutDGBActivity extends BaseActivity {

    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.bacK_RL)
    RelativeLayout bacKRL;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.btn_agree)
    TextView btnAgree;
    @BindView(R.id.btn_privacy)
    TextView btnPrivacy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_other;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        bacKRL.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                finish();
            }
        });
        showLoading();
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .aboutoem(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<AboutBean>() {

                    @Override
                    protected void success(AboutBean data) {
                        dissmissLoading();
                        AboutBean.RetvalBean retval = data.getRetval();
                        titleTV.setText(retval.getPageinfo().getHeadtitle());
                        content_tv.setText("???????????????");
                        webView.setBackgroundColor(0);
                        String s = MyUtilsWebView.setWebViewText(retval.getAboutoem());
                        WebViewSeting.setting(webView, MyAboutDGBActivity.this, s);
                        try {
                            /**
                             * ???????????????????????????
                             */
                            banner.setPages(retval.getSbanner(), new MZHolderCreator<BannerViewHolder>() {
                                @Override
                                public BannerViewHolder createViewHolder() {
                                    return new BannerViewHolder();
                                }
                            });
                        } catch (Exception e) {

                        }

                    }
                });
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, MyAboutDGBActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_privacy,R.id.btn_agree})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.btn_agree:
                MyWebActivity.simpleActivity(this, BuildConfig.URL_AGREEMENT,  "????????????");
                break;
            case R.id.btn_privacy:
                MyWebActivity.simpleActivity(this, BuildConfig.URL_PRIVACY, "????????????");
                break;
        }
    }

    public static class BannerViewHolder implements MZViewHolder<AboutBean.RetvalBean.SbannerBean> {
        private RadiusImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RadiusImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, AboutBean.RetvalBean.SbannerBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //?????????
                    .error(R.drawable.ic_launcher_background)      //?????????
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(data.getAd_logo()).apply(options).into(mImageView);

        }
    }
}