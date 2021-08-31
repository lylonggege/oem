package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.MyCustomerServiceAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.OemkefuBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCustomerServiceActivity extends BaseActivity {


    @BindView(R.id.back_im)
    ImageView backIm;
    @BindView(R.id.bacK_RL)
    RelativeLayout bacKRL;
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.MyRecycleView)
    com.zhangying.oem1688.custom.MyRecycleView MyRecycleView;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        showLoading();
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .oemkefu(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<OemkefuBean>() {

                    @Override
                    protected void success(OemkefuBean data) {
                        dissmissLoading();
                        OemkefuBean.RetvalBean retval = data.getRetval();
                        titleTV.setText(retval.getPageinfo().getHeadtitle());
                        webView.setBackgroundColor(0);
                        String s = MyUtilsWebView.setWebViewText(retval.getOemkefucont());
                        WebViewSeting.setting(webView, MyCustomerServiceActivity.this, s);
                        try {
                            /**
                             * 图片轮播的简单使用
                             */
                            banner.setPages(retval.getSbanner(), new MZHolderCreator<BannerViewHolder>() {
                                @Override
                                public BannerViewHolder createViewHolder() {
                                    return new BannerViewHolder();
                                }
                            });
                        } catch (Exception e) {

                        }

                        //客服列表
                        MyCustomerServiceAdpter myCustomerServiceAdpter = new MyCustomerServiceAdpter(MyCustomerServiceActivity.this);
                        myCustomerServiceAdpter.refresh(retval.getOemkefu().getKflist());
                        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
                        MyRecycleView.addItemDecoration(new SpacesItemDecoration(space, space));
                        MyRecycleView.setLayoutManager(new GridLayoutManager(MyCustomerServiceActivity.this, 2));
                        MyRecycleView.setAdapter(myCustomerServiceAdpter);

                    }
                });
    }

    public static class BannerViewHolder implements MZViewHolder<OemkefuBean.RetvalBean.SbannerBean> {
        private RadiusImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RadiusImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, OemkefuBean.RetvalBean.SbannerBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //占位图
                    .error(R.drawable.ic_launcher_background)      //错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(data.getAd_logo()).apply(options).into(mImageView);
        }
    }

    @OnClick(R.id.bacK_RL)
    public void onClick() {
        finish();
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, MyCustomerServiceActivity.class);
        context.startActivity(intent);
    }
}