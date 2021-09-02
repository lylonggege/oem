package com.zhangying.oem1688.view.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FragmnetPagerAdapter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.ScinfoTopBean;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MySlidingTabLayout;
import com.zhangying.oem1688.custom.WrapContentHeightViewPager;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.view.fragment.daigongchang.ChengJieDaiGongFragment;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 承接代加工
 */
public class FindProductActivity extends BaseActivity implements BaseView {
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.parent_tab_indictor)
    MySlidingTabLayout parentTabIndictor;
    @BindView(R.id.ViewPagerSlide)
    WrapContentHeightViewPager ViewPagerSlide;
    @BindView(R.id.release_LL)
    LinearLayout release_LL;
	@BindView(R.id.title_TV)
    TextView title_TV;
    private BaseValidateCredentials fenLeiRealization;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private FragmnetPagerAdapter fragmnetPagerAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManagerUtil.getInstance().addHomeActivity(this);
        fenLeiRealization = new FenLeiRealization(this, this);
		title_TV.setText("工厂承接代加工-代工帮");
        //获取数据
        scinfo_top();

        release_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReleaseActivity.simpleActivity(FindProductActivity.this);
            }
        });

    }

    private void scinfo_top() {
        RemoteRepository.getInstance()
                .scinfo_top(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<ScinfoTopBean>() {

                    @Override
                    protected void success(ScinfoTopBean data) {
                        ScinfoTopBean.RetvalBean retval = data.getRetval();
                        List<ScinfoTopBean.RetvalBean.BannersBean> banners = retval.getBanners();
                        initbanner(banners);
                        List<ScinfoTopBean.RetvalBean.CatesBean> cates = retval.getCates();
                        for (int i = 0; i < cates.size(); i++) {
                            ScinfoTopBean.RetvalBean.CatesBean catesBean = cates.get(i);
                            ChengJieDaiGongFragment chengJieDaiGongFragment = new ChengJieDaiGongFragment(catesBean.getCateid());
                            fragmentList.add(chengJieDaiGongFragment);
                            tabList.add(catesBean.getCatename());
                        }

                        if (fragmentList != null && fragmentList.size() > 0) {
                            FragmentManager manager = getSupportFragmentManager();
                            fragmnetPagerAdapter = new FragmnetPagerAdapter(manager, fragmentList, tabList);
                            ViewPagerSlide.setOffscreenPageLimit(fragmentList.size());
                            ViewPagerSlide.setAdapter(fragmnetPagerAdapter);
                            parentTabIndictor.setViewPager(ViewPagerSlide);
                            parentTabIndictor.setCurrentTab(0);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    @OnClick({R.id.imageView2, R.id.textView, R.id.bacK_RL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(this);
                break;
			case R.id.bacK_RL:
                finish();
                break;
        }
    }

    @Override
    public void showloading() {
        showLoading();
    }

    @Override
    public void hidenloading() {
        dissmissLoading();
    }

    @Override
    public void success(Object o) {
        finish();
    }


    private void initbanner(List<ScinfoTopBean.RetvalBean.BannersBean> sbanner) {

        try {
            /**
             * 图片轮播的简单使用
             */
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) banner.getLayoutParams();
            layoutParams.width = ScreenTools.instance(this).getScreenWidth() - ScreenTools.instance(this).dip2px(20);
            layoutParams.height = layoutParams.width * 260 / 720;
            banner.setLayoutParams(layoutParams);

            banner.setPages(sbanner, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
        } catch (Exception e) {

        }
    }

    public static class BannerViewHolder implements MZViewHolder<ScinfoTopBean.RetvalBean.BannersBean> {
        private RadiusImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RadiusImageView) view.findViewById(R.id.banner_image);
            mImageView.setCornerRadius(20);
            return view;
        }

        @Override
        public void onBind(Context context, int position, ScinfoTopBean.RetvalBean.BannersBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //占位图
                    .error(R.drawable.ic_launcher_background)      //错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(data.getAd_logo()).apply(options).into(mImageView);

        }
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, FindProductActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManagerUtil.getInstance().finishhomeActivity(this);
    }


}