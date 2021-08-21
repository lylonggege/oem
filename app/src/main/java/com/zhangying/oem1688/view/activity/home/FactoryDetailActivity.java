package com.zhangying.oem1688.view.activity.home;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.internal.LinkedTreeMap;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FactoryDetailCatesAdpter;
import com.zhangying.oem1688.adpter.FactoryDetailTuijianAdpter;
import com.zhangying.oem1688.adpter.GoodsDetailOemAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MoreLineTextView;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.popu.GoodsFactoryCatePopu;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ImageViewInfo;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.PreviewImageView;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.util.WeiXinActivity;
import com.zhangying.oem1688.widget.RadiusImageBanner;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;


/**
 * 店铺详情
 */

public class FactoryDetailActivity extends BaseActivity {


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
    @BindView(R.id.line_sign)
    LinearLayout lineSign;
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
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.submit_tv)
    TextView submitTv;
    @BindView(R.id.tuijian_RecycleView)
    MyRecycleView tuijianRecycleView;
    @BindView(R.id.myRecycleView_gcates)
    MyRecycleView myRecycleView_gcates;
    @BindView(R.id.shop_b_dp_tv)
    TextView shopBDpTv;
    @BindView(R.id.shop_b_dp_image)
    ImageView shop_b_dp_image;
    @BindView(R.id.shop_b_sp_db_iv)
    ImageView shop_b_sp_db_iv;
    @BindView(R.id.shop_b_sp_db_tv)
    TextView shopBSpDbTv;
    @BindView(R.id.rootView_shop_b_sp_ll)
    LinearLayout rootViewShopBSpLl;
    @BindView(R.id.rootview_shoucang_iv)
    ImageView rootviewShoucangIv;
    @BindView(R.id.rootview_shoucang_tv)
    TextView rootviewShoucangTv;
    @BindView(R.id.rootView_shop_b_sc_ll)
    LinearLayout rootViewShopBScLl;
    @BindView(R.id.rootView_phone)
    TextView rootViewPhone;
    @BindView(R.id.rootView_line)
    TextView rootViewLine;
    @BindView(R.id.description_tv)
    MoreLineTextView description_tv;
    @BindView(R.id.message_LL)
    LinearLayout message_LL;
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
        return R.layout.activity_factory_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHomeCateSate(ishomne);
        AppManagerUtil.getInstance().addHomeActivity(this);
        showLoading();
        FactoryDetailCatesAdpter factoryDetailCatesAdpter = new FactoryDetailCatesAdpter(FactoryDetailActivity.this);
        WidgetUtils.initRecyclerView(myRecycleView_gcates);
        myRecycleView_gcates.setAdapter(factoryDetailCatesAdpter);
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

                        //公司信息
                        initCompany(retval);

                        //图片列表或者
                        List<FactoryDetailBean.RetvalBean.SpageBean> spage = retval.getSpage();
                        imageViewGroupLL.removeAllViews();
                        int ww=ScreenTools.instance(FactoryDetailActivity.this).getScreenWidth()-ScreenTools.instance(FactoryDetailActivity.this).dip2px(30);
                        if (spage != null && spage.size() > 0) {
                            for (int i = 0; i < spage.size(); i++) {
                                FactoryDetailBean.RetvalBean.SpageBean spageBean1 = spage.get(i);
                                int ctype = spageBean1.getCtype();
                                if (ctype == 1) {
                                    List<Object> content = (List<Object>) spageBean1.getContent();
                                    if (content.size() > 0) {
                                        List<ImageViewInfo> list = new ArrayList<>();
                                            for (int i1 = 0; i1 < content.size(); i1++) {
                                                LinkedTreeMap<String, Object> hashMap = (LinkedTreeMap<String, Object>) content.get(i1);
                                                ImageView imageView = new ImageView(FactoryDetailActivity.this);
                                                imageViewGroupLL.addView(imageView);
                                                double w = Double.parseDouble(String.valueOf(hashMap.get("w")));
                                                double h = Double.parseDouble(String.valueOf(hashMap.get("h")));
                                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                                layoutParams.width = (int) ww;
                                                layoutParams.height = (int)(h  * layoutParams.width / w);
                                                imageView.setLayoutParams(layoutParams);
                                                if (FactoryDetailActivity.this != null) {
                                                    GlideUtil.loadImage(FactoryDetailActivity.this, (String) hashMap.get("url"), imageView);
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


                                    }
                                } else {
                                    if (ctype == 8) {
//                                        String description = retval.getDescription();
//                                        TextView textView = new TextView(FactoryDetailActivity.this);
//                                        textView.setText(description);
//                                        imageViewGroupLL.addView(textView);
                                    } else if (ctype == 5) {

                                    }
                                }


                            }
                        }

                        //分类
                        if (retval.getGcates().size() > 0) {
                            factoryDetailCatesAdpter.refresh(retval.getGcates());
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessageDelayed(message, 1500);
                        } else {
                            myRecycleView_gcates.setVisibility(View.GONE);
                        }


                        //推荐品类
                        List<FactoryDetailBean.RetvalBean.OstoresBean> ostores = retval.getOstores();
                        if (ostores != null && ostores.size() > 0) {
                            FactoryDetailTuijianAdpter factoryDetailTuijianAdpter = new FactoryDetailTuijianAdpter(FactoryDetailActivity.this);
                            factoryDetailTuijianAdpter.refresh(ostores);
                            WidgetUtils.initRecyclerView(tuijianRecycleView);
                            tuijianRecycleView.setAdapter(factoryDetailTuijianAdpter);
                        }

                        nestedscrollview.scrollTo(0, 0);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    private void initCompany(FactoryDetailBean.RetvalBean retval) {
        GlideUtil.loadImage(FactoryDetailActivity.this, retval.getStore_logo(), companyLogeIv);
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

        shopBDpTv.setText(retval.getEndbtn1());
        shopBSpDbTv.setText(retval.getEndbtn2());
        rootViewPhone.setText(retval.getEndbtn3());
        rootViewLine.setText(retval.getEndbtn4());

        im_tip_tv.setText(retval.getIm_tip());
    }

    private void initbanner() {
        rib_simple_usage.setSource(bannerItemData).startScroll();
    }

    @OnClick({R.id.rootView_shop_b_sp_ll, R.id.rootview_shoucang_tv,
            R.id.rootView_shop_b_sc_ll, R.id.rootView_phone,
            R.id.rootView_line, R.id.message_LL, R.id.imageView2,
            R.id.textView, R.id.rootView_shop_b_dp_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rootView_shop_b_dp_ll: //首頁
                setHomeCateSate(true);
                break;
            case R.id.rootView_shop_b_sp_ll: //分類
                if (retval.getGcates() != null && retval.getGcates().size() > 0) {
                    setHomeCateSate(false);
                    new XPopup.Builder(this)
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
                                    setHomeCateSate(true);
                                }

                                @Override
                                public boolean onBackPressed() {
                                    return false;
                                }
                            })
                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .dismissOnTouchOutside(true)
                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .enableDrag(true)
                            .asCustom(new GoodsFactoryCatePopu(FactoryDetailActivity.this, retval.getGcates())/*.enableDrag(false)*/)
                            .show();
                } else {
                    ToastUtil.showToast("暂无分类数据");
                }
                break;
            case R.id.rootview_shoucang_tv:

                break;
            case R.id.rootView_shop_b_sc_ll:


                int has_collect = retval.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
                break;
            case R.id.rootView_phone:
                new XPopup.Builder(this)
                        .hasShadowBg(true)
                        .asConfirm("提示", retval.getEndbtn3() + "    " + retval.getTel(),
                                "取消", "拨打",
                                new OnConfirmListener() {
                                    @Override

                                    public void onConfirm() {

                                        AutoForcePermissionUtils.requestPermissions(FactoryDetailActivity.this, new AutoForcePermissionUtils.PermissionCallback() {

                                            @Override
                                            public void onPermissionGranted() {
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                Uri data = Uri.parse("tel:" + retval.getTel());
                                                intent.setData(data);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onPermissionDenied() {
                                                ToastUtil.showToast("拨打电话权限被拒绝，请手动拨打！");
                                            }
                                        }, Manifest.permission.CALL_PHONE);

                                    }
                                }, null, false)
                        .show();
                break;
            case R.id.rootView_line:
                WeiXinActivity.init(this);
                break;
            case R.id.message_LL:
                new XPopup.Builder(this)
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
                        .asCustom(new GoodsDetailPopu(this))
                        .show();

                break;
            case R.id.imageView2:
                FenLeiRealization fenLeiRealization = new FenLeiRealization(this, new BaseView() {
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

                    }
                });
                fenLeiRealization.realization();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(this);
                break;
        }
    }

    public static void simpleActivity(Context context, String cid) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        context.startActivity(intent);
    }

    private static class BannerViewHolder implements MZViewHolder<FactoryDetailBean.RetvalBean.slidesBean> {
        private RadiusImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RadiusImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, FactoryDetailBean.RetvalBean.slidesBean data) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background) //占位图
                    .error(R.drawable.ic_launcher_background)      //错误图
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
            layoutParams.width = Integer.parseInt(data.getW());
            layoutParams.height = Integer.parseInt(data.getH());
            mImageView.setLayoutParams(layoutParams);
            Glide.with(context).load(data.getUrl()).apply(options).into(mImageView);

        }
    }


    private void storecollect() {
        showLoading();
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", retval.getStore_id());
        map.put("token", TokenUtils.getToken());
        map.put("timestamp", timestamp);
        String url = timestamp + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
        String md5Str = MD5Util.getMD5Str(url);
        map.put("sign", md5Str);
        RemoteRepository.getInstance()
                .storecollect(map)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            ToastUtil.showToast("收藏成功");
                            retval.setHas_collect(1);
                            rootviewShoucangIv.setSelected(true);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    private void drop_collect() {
        showLoading();
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", retval.getStore_id());
        map.put("timestamp", timestamp);
        map.put("token", TokenUtils.getToken());
        String url = timestamp + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
        String md5Str = MD5Util.getMD5Str(url);
        map.put("sign", md5Str);
        RemoteRepository.getInstance()
                .drop_collect(map)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        ToastUtil.showToast(data.getMsg());
                        if (data.isDone()) {
                            ToastUtil.showToast("取消收藏成功");
                            retval.setHas_collect(0);
                            rootviewShoucangIv.setSelected(false);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManagerUtil.getInstance().finishhomeActivity(this);
    }

    private void setHomeCateSate(Boolean b) {
        /**
         * b true  点击首页
         */
        if (b) {
            shop_b_dp_image.setSelected(true);
            shopBDpTv.setSelected(true);
            shop_b_sp_db_iv.setSelected(false);
            shopBSpDbTv.setSelected(false);
        } else {
            shop_b_dp_image.setSelected(false);
            shopBDpTv.setSelected(false);
            shop_b_sp_db_iv.setSelected(true);
            shopBSpDbTv.setSelected(true);
        }

    }
}