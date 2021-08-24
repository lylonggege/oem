package com.zhangying.oem1688.view.fragment.home;

import android.content.Context;
import android.content.Intent;
import com.google.gson.internal.LinkedTreeMap;

import androidx.core.widget.NestedScrollView;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FactoryDetailCatesAdpter;
import com.zhangying.oem1688.adpter.FactoryDetailTuijianAdpter;
import com.zhangying.oem1688.adpter.GoodsDetailOemAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.custom.MoreLineTextView;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.PreviewImageView;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.ImageViewInfo;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;
import com.zhangying.oem1688.widget.RadiusImageBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FactoryDetailFragment extends BaseFragment {
    @BindView(R.id.company_loge_iv)
    ImageView companyLogeIv;
    @BindView(R.id.companyname_tv)
    TextView companynameTv;
    @BindView(R.id.companyname_authtag_tv)
    TextView companynameAuthtagTv;
    @BindView(R.id.company_storetime_tv)
    TextView companyStoretimeTv;
    @BindView(R.id.dian)
    LinearLayout dian;
    @BindView(R.id.cate_tv)
    TextView cateTv;
    @BindView(R.id.rootView_bang_tv)
    TextView rootViewBangTv;
    @BindView(R.id.rootView_bang_ll)
    LinearLayout rootViewBangLl;
    @BindView(R.id.tuijian_tv)
    TextView tuijianTv;
    @BindView(R.id.company_verification_tv)
    TextView companyVerificationTv;
    @BindView(R.id.imageView_group_LL)
    LinearLayout imageViewGroupLL;
    @BindView(R.id.oemRecycleView)
    MyRecycleView oemRecycleView;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.description_tv)
    MoreLineTextView description_tv;

    @BindView(R.id.tuijian_RecycleView)
    MyRecycleView tuijianRecycleView;
    @BindView(R.id.myRecycleView_gcates)
    MyRecycleView myRecycleView_gcates;

    @BindView(R.id.im_tip_tv)
    TextView im_tip_tv;
    @BindView(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;
    @BindView(R.id.rib_simple_usage)
    RadiusImageBanner rib_simple_usage;
    private List<BannerItem> bannerItemData = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    nestedscrollview.fling(0);
                    nestedscrollview.smoothScrollTo(0, 0);
                    break;
            }
        }
    };

    private static String mcid;
    private FactoryDetailBean.RetvalBean retval;
    private boolean ishomne = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_factory_detail;
    }

    @Override
    public void initView() {
        //初始化工厂产品列表
        FactoryDetailCatesAdpter factoryDetailCatesAdpter = new FactoryDetailCatesAdpter(getActivity());
        WidgetUtils.initRecyclerView(myRecycleView_gcates);
        myRecycleView_gcates.setAdapter(factoryDetailCatesAdpter);

        mcid = getArguments().getString("mcid");
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
        HashMapSingleton.getInstance().put("cid", mcid);
        RemoteRepository.getInstance()
                .get_store(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryDetailBean>() {

                    @Override
                    protected void success(FactoryDetailBean data) {
                        dissmissLoading();
                        retval = data.getRetval();
                        //初始化公司基本信息
                        initCompanyBase(retval);

                        //产品分类
                        if (retval.getGcates().size() > 0) {
                            factoryDetailCatesAdpter.refresh(retval.getGcates());
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessageDelayed(message, 1500);
                        } else {
                            myRecycleView_gcates.setVisibility(View.GONE);
                        }

                        //图片列表或者
                        initCompanySpage(retval);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    //初始化公司页面模板
    private void initCompanySpage(FactoryDetailBean.RetvalBean retval){
        List<FactoryDetailBean.RetvalBean.SpageBean> spage = retval.getSpage();
        imageViewGroupLL.removeAllViews();
        int ww=ScreenTools.instance(getActivity()).getScreenWidth()-ScreenTools.instance(getActivity()).dip2px(30);
        if (spage == null || spage.size() == 0) { return; }

        for (int i = 0; i < spage.size(); i++) {
            FactoryDetailBean.RetvalBean.SpageBean spageBean1 = spage.get(i);
            int ctype = spageBean1.getCtype();
            if (ctype == 1) {
                List<Object> content = (List<Object>) spageBean1.getContent();
                if (content.size() == 0) { continue; }
                List<ImageViewInfo> list = new ArrayList<>();
                for (int i1 = 0; i1 < content.size(); i1++) {
                    LinkedTreeMap<String, Object> hashMap = (LinkedTreeMap<String, Object>) content.get(i1);
                    ImageView imageView = new ImageView(getActivity());
                    imageViewGroupLL.addView(imageView);
                    double w = Double.parseDouble(String.valueOf(hashMap.get("w")));
                    double h = Double.parseDouble(String.valueOf(hashMap.get("h")));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.width = (int) ww;
                    layoutParams.height = (int)(h  * layoutParams.width / w);
                    imageView.setLayoutParams(layoutParams);
                    if (getActivity() != null) {
                        GlideUtil.loadImage(getActivity(), (String) hashMap.get("url"), imageView);
                    }

                    ImageViewInfo imageViewInfo = new ImageViewInfo((String) hashMap.get("url"));
                    list.add(imageViewInfo);
                    int finalI = i1;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PreviewImageView.save(imageView, finalI, list);
                        }
                    });

                }
            } else if (ctype == 8){

            }else if (ctype == 5) {

            }
        }
    }

    private void initCompanyBase(FactoryDetailBean.RetvalBean retval) {
        List<FactoryDetailBean.RetvalBean.slidesBean> slides = retval.getSlides();
        if (slides != null && slides.size() > 0) {
            for (FactoryDetailBean.RetvalBean.slidesBean slide : slides) {
                BannerItem bannerItembean = new BannerItem();
                bannerItembean.setImgUrl(slide.getUrl());
                bannerItemData.add(bannerItembean);

            }
            initbanner();
        } else {
            rib_simple_usage.setVisibility(View.GONE);
        }

        GlideUtil.loadImage(getActivity(), retval.getStore_logo(), companyLogeIv);
        companynameTv.setText(retval.getStore_name());
        companynameAuthtagTv.setText(retval.getAuthtag());
        String storetime = retval.getStoretime();
        if (storetime.length() > 0) {
            companyStoretimeTv.setText(storetime);
        } else {
            companyStoretimeTv.setVisibility(View.GONE);
        }

        cateTv.setText(retval.getStoretip() + retval.getService());

        List<FactoryDetailBean.RetvalBean.storetagsBean> storetags = retval.getStoretags();
        if (storetags.size() > 0) {
            if (storetags.get(0) != null) {
                tuijianTv.setText(storetags.get(0).getStag());
            } else {
                tuijianTv.setVisibility(View.GONE);
            }

            if (storetags.get(1) != null) {
                companyVerificationTv.setText(storetags.get(1).getStag());
            } else {
                companyVerificationTv.setVisibility(View.GONE);
            }
        } else {
            tuijianTv.setVisibility(View.GONE);
            companyVerificationTv.setVisibility(View.GONE);
        }

        if (retval.getStorebang() != null && retval.getStorebang().length() > 0) {
            rootViewBangTv.setText(retval.getStorebang());
        } else {
            rootViewBangLl.setVisibility(View.GONE);
        }

        description_tv.setText(retval.getDescription());


        GoodsDetailOemAdpter goodsDetailOemAdpter = new GoodsDetailOemAdpter();
        goodsDetailOemAdpter.refresh(retval.getStore_gcates());
        WidgetUtils.initGridRecyclerView(oemRecycleView, 3, DensityUtils.dp2px(5));
        oemRecycleView.setAdapter(goodsDetailOemAdpter);

        //推荐品类
        List<FactoryDetailBean.RetvalBean.OstoresBean> ostores = retval.getOstores();
        if (ostores != null && ostores.size() > 0) {
            FactoryDetailTuijianAdpter factoryDetailTuijianAdpter = new FactoryDetailTuijianAdpter(getActivity());
            factoryDetailTuijianAdpter.refresh(ostores);
            WidgetUtils.initRecyclerView(tuijianRecycleView);
            tuijianRecycleView.setAdapter(factoryDetailTuijianAdpter);
        }

        im_tip_tv.setText(retval.getIm_tip());
    }

    private void initbanner() {
        rib_simple_usage.setSource(bannerItemData).startScroll();
    }

    @OnClick({R.id.message_LL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_LL:
                new XPopup.Builder(getActivity())
                        .setPopupCallback(new XPopupCallback() {
                            @Override
                            public void onCreated() {

                            }

                            @Override
                            public void beforeShow() {

                            }

                            @Override
                            public void onShow() {

                            }

                            @Override
                            public void onDismiss() {
                            }

                            @Override
                            public boolean onBackPressed() {
                                return true;
                            }
                        })
                        .dismissOnTouchOutside(true)
                        .asCustom(new GoodsDetailPopu(getActivity()))
                        .show();

                break;
        }
    }

    public static void simpleActivity(Context context, String cid) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        context.startActivity(intent);
    }
}