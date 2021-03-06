package com.zhangying.oem1688.view.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.internal.LinkedTreeMap;

import androidx.core.widget.NestedScrollView;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationOption;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FactoryDetailCatesAdpter;
import com.zhangying.oem1688.adpter.FactoryDetailTuijianAdpter;
import com.zhangying.oem1688.adpter.GoodsDetailOemAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.custom.MoreLineTextView;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseMessageListener;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.GlobalEntitySingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.PreviewImageView;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.ImageViewInfo;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;
import com.zhangying.oem1688.widget.RadiusImageBanner;

import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;

import androidx.annotation.NonNull;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FactoryDetailFragment extends BaseFragment implements VideoAllCallBack {
    @BindView(R.id.company_loge_iv)
    RadiusImageView companyLogeIv;
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
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.description_tv)
    MoreLineTextView description_tv;
    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer detailPlayer;

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
                    try {
                        nestedscrollview.fling(0);
                        nestedscrollview.smoothScrollTo(0, 0);
                    } catch (NullPointerException e) {
                        System.out.println("nestedscrollview not null ....");
                    }

                    break;
            }
        }
    };

    private static String mcid;
    private FactoryDetailBean.RetvalBean retval;
    FactoryDetailCatesAdpter factoryDetailCatesAdpter;
    private GoodsDetailPopu msgPop;
    private String videoUrl;
    private boolean hasVideo;
    private GoodsDetailOemAdpter goodsDetailOemAdpter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_factory_detail;
    }

    @Override
    public void initView() {
        companynameTv.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        companynameTv.getPaint().setStrokeWidth(0.5f);

        //??????title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);

        //???????????????????????????
        factoryDetailCatesAdpter = new FactoryDetailCatesAdpter(getActivity());
        WidgetUtils.initRecyclerView(myRecycleView_gcates);
        myRecycleView_gcates.setAdapter(factoryDetailCatesAdpter);

        mcid = getArguments().getString("mcid");
        retval = GlobalEntitySingleton.getInstance().getFactoryDetail();
        if (retval == null) {//??????????????????????????????
            loadFactoryInfo();
        } else {
            renderFactoryInfo();
        }
    }

    /**
     * ???????????????
     */
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //?????????????????????SampleCoverVideo
        ImageView imageView = new ImageView(getContext());
        loadCover(imageView, retval.getVideo_cover());
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setCacheWithPlay(true)
                .setVideoTitle(retval.getStore_name())
                .setIsTouchWiget(true)
                //.setAutoFullWithSize(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//????????????
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.goods_default);
        Glide.with(getActivity().getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.drawable.goods_default)
                                .placeholder(R.drawable.goods_default))
                .load(url)
                .into(imageView);
    }

    /**
     * ???????????????
     */
    public void clickForFullScreen() {

    }

    /**
     * ???????????????????????????true????????????
     */
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    //????????????????????????
    private void loadFactoryInfo() {
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("cid", mcid);
        RemoteRepository.getInstance()
                .get_store(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryDetailBean>() {
                    @Override
                    protected void success(FactoryDetailBean data) {
                        dissmissLoading();
                        retval = data.getRetval();
                        renderFactoryInfo();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    //??????????????????
    private void renderFactoryInfo() {
        //???????????????????????????
        initCompanyBase(retval);

        //????????????
        if (retval.getGcates().size() > 0) {
            factoryDetailCatesAdpter.refresh(retval.getGcates());
            Message message = new Message();
            message.what = 1;
            handler.sendMessageDelayed(message, 1500);
        } else {
            myRecycleView_gcates.setVisibility(View.GONE);
        }
        //??????????????????
        initCompanySpage(retval);
    }

    //???????????????????????????
    private void initCompanySpage(FactoryDetailBean.RetvalBean retval) {
        List<FactoryDetailBean.RetvalBean.SpageBean> spage = retval.getSpage();
        imageViewGroupLL.removeAllViews();
        int ww = ScreenTools.instance(getActivity()).getScreenWidth() - ScreenTools.instance(getActivity()).dip2px(30);
        if (spage == null || spage.size() == 0) {
            return;
        }

        for (int i = 0; i < spage.size(); i++) {
            FactoryDetailBean.RetvalBean.SpageBean spageBean1 = spage.get(i);
            int ctype = spageBean1.getCtype();
            if (ctype == 1) {
                List<Object> content = (List<Object>) spageBean1.getContent();
                if (content.size() == 0) {
                    continue;
                }
                ArrayList<ImageViewInfo> list = new ArrayList<>();
                int baseY = 0;
                for (int i1 = 0; i1 < content.size(); i1++) {
                    LinkedTreeMap<String, Object> hashMap = (LinkedTreeMap<String, Object>) content.get(i1);
                    ImageView imageView = new ImageView(getActivity());
                    imageViewGroupLL.addView(imageView);
                    double w = Double.parseDouble(String.valueOf(hashMap.get("w")));
                    double h = Double.parseDouble(String.valueOf(hashMap.get("h")));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.width = (int) ww;
                    layoutParams.height = (int) (h * layoutParams.width / w);
                    imageView.setLayoutParams(layoutParams);
                    if (getActivity() != null) {
                        GlideUtil.loadImage(getActivity(), (String) hashMap.get("url"), imageView);
                    }

                    ImageViewInfo imageViewInfo = new ImageViewInfo((String) hashMap.get("url"));
                    imageViewInfo.setBounds(new Rect(0,baseY,layoutParams.width,layoutParams.height));
                    baseY += layoutParams.height;
                    list.add(imageViewInfo);
                    int finalI = i1;
                    imageView.setOnClickListener(new OnMultiClickListener() {
                        @Override
                        public void onMultiClick(View v) {
                            PreviewImageView.save(imageView, finalI, list);
                        }
                    });

                }
            } else if (ctype == 8) {

            } else if (ctype == 5) {

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

        //???vip????????????
        String sColor = retval.getScolor();
        GradientDrawable drawable = (GradientDrawable) dian.getBackground();
        if (sColor.length() > 0) {
            drawable.setStroke(2, Color.parseColor(sColor));//??????????????????????????????
            companynameAuthtagTv.setBackgroundColor(Color.parseColor(sColor));
        } else {
            drawable.setStroke(2, getResources().getColor(R.color.redf04142));
        }

        //logo???vip??????
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

        //??????????????????
        videoUrl = retval.getStore_video();
        hasVideo = !StringUtils.isEmity(videoUrl);
        if (hasVideo){
            initVideoBuilderMode();
        }else {
            detailPlayer.setVisibility(View.GONE);
        }

        //??????
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

        //???????????????????????????????????????
        goodsDetailOemAdpter = new GoodsDetailOemAdpter();
        goodsDetailOemAdpter.refresh(retval.getStore_gcates());
        WidgetUtils.initGridRecyclerView(oemRecycleView, 3, DensityUtils.dp2px(5));
        oemRecycleView.setAdapter(goodsDetailOemAdpter);

        //????????????
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

    @OnClick({R.id.message_LL, R.id.submit_tv})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.submit_tv://??????????????????
                ArrayList<Integer> cateSelected = new ArrayList<Integer>();
                List<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> catesList = retval.getStore_gcates();
                for (GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean item : catesList) {
                    if (item.isaBoolean()) cateSelected.add(item.getId());
                }

                doSubmitMessage(nameEt.getText().toString(), phoneEt.getText().toString(), TextUtils.join(",", cateSelected), true);
                break;
            case R.id.message_LL://?????????????????????
                if (msgPop == null) {
                    msgPop = new GoodsDetailPopu(getActivity());
                    msgPop.setMessageLister(new BaseMessageListener() {
                        @Override
                        public boolean submit(String name, String phone) {
                            return doSubmitMessage(name, phone, "", false);
                        }
                    });
                }
                BasePopupView popView = new XPopup.Builder(getActivity())
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
                        .asCustom(msgPop);
                popView.popupInfo.popupAnimation = PopupAnimation.ScaleAlphaFromCenter;
                popView.show();
                break;
        }
    }

    /**
     * ????????????????????????
     *
     * @param name    ??????
     * @param phone   ??????
     * @param cates   ???????????????????????????
     * @param chkCate ??????????????????????????????
     */
    private boolean doSubmitMessage(String name, String phone, String cates, boolean chkCate) {
        if (StringUtils.isEmity(name)) {
            ToastUtil.showToast("???????????????");
            return false;
        }

        if (StringUtils.isEmity(phone)) {
            ToastUtil.showToast("???????????????");
            return false;
        }

        if (phone.length() != 11){
            ToastUtil.showToast("????????????????????????????????????");
            return false;
        }

        if (chkCate && StringUtils.isEmity(cates)) {
            ToastUtil.showToast("?????????????????????");
            return false;
        }

        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("uname", name);
        HashMapSingleton.getInstance().put("utel", phone);
        HashMapSingleton.getInstance().put("lycomId", retval.getStore_id());
        HashMapSingleton.getInstance().put("lylm", "store");
        HashMapSingleton.getInstance().put("id", retval.getStore_id());
        HashMapSingleton.getInstance().put("lyagent", "7");
        HashMapSingleton.getInstance().put("lycate", cates);

        //???????????????
        RemoteRepository.getInstance()
                .add_message(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            ToastUtil.showToast("????????????");
                            if (chkCate) {
                                //?????????????????????????????????????????????
                                List<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> catesList = retval.getStore_gcates();
                                for (int i = 0; i < catesList.size(); i++) {
                                    GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean storeGcatesBean = catesList.get(i);
                                    if (storeGcatesBean.isaBoolean()) {
                                        storeGcatesBean.setaBoolean(false);
                                        goodsDetailOemAdpter.notifyItemChanged(i);
                                    }
                                }

                                nameEt.setText("");
                                phoneEt.setText("");
                            }
                        } else {
                            ToastUtil.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
        return true;
    }

    public static void simpleActivity(Context context, String cid) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        context.startActivity(intent);
    }

    protected boolean isPlay;
    protected boolean isPause;
    protected OrientationUtils orientationUtils;

    /**
     * ??????????????????
     */
    public void initVideo() {
        //????????????????????????????????????
        orientationUtils = new OrientationUtils(getActivity(), getGSYVideoPlayer(), getOrientationOption());
        //?????????????????????????????????
        orientationUtils.setEnable(false);
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFull();
                    clickForFullScreen();
                }
            });
        }
    }

    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    /**
     * ??????builder??????
     */
    public void initVideoBuilderMode() {
        initVideo();
        getGSYVideoOptionBuilder().
                setVideoAllCallBack(this)
                .build(getGSYVideoPlayer());
    }

    public void showFull() {
        if (orientationUtils.getIsLand() != 1) {
            //????????????
            // ------- ???????????????????????????????????????????????????????????????-------
            // ??????????????????????????????????????? setNeedOrientationUtils(false)
            orientationUtils.resolveByClick();
        }
        //?????????true??????????????????actionbar????????????true??????????????????statusbar
        getGSYVideoPlayer().startWindowFullscreen(getActivity(), hideActionBarWhenFull(), hideStatusBarWhenFull());
    }


    public void onBackPressed() {
        // ------- ???????????????????????????????????????????????????????????????-------
        // ??????????????????????????????????????? setNeedOrientationUtils(false)
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (hasVideo){
            getGSYVideoPlayer().getCurrentPlayer().onVideoPause();
            if (orientationUtils != null) {
                orientationUtils.setIsPause(true);
            }
            isPause = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasVideo) {
            getGSYVideoPlayer().getCurrentPlayer().onVideoResume();
            if (orientationUtils != null) {
                orientationUtils.setIsPause(false);
            }
            isPause = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasVideo) {
            if (isPlay) {
                getGSYVideoPlayer().getCurrentPlayer().release();
            }
            if (orientationUtils != null)
                orientationUtils.releaseListener();
        }
    }

    /**
     * orientationUtils ???  detailPlayer.onConfigurationChanged ????????????????????????????????????
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (hasVideo) {
            //????????????????????????
            if (isPlay && !isPause) {
                getGSYVideoPlayer().onConfigurationChanged(getActivity(), newConfig, orientationUtils, hideActionBarWhenFull(), hideStatusBarWhenFull());
            }
        }
    }

    @Override
    public void onStartPrepared(String url, Object... objects) {

    }

    @Override
    public void onPrepared(String url, Object... objects) {
        if (hasVideo) {
            if (orientationUtils == null) {
                throw new NullPointerException("initVideo() or initVideoBuilderMode() first");
            }
            //????????????????????????????????????
            orientationUtils.setEnable(getDetailOrientationRotateAuto() && !isAutoFullWithSize());
            isPlay = true;
        }
    }

    @Override
    public void onClickStartIcon(String url, Object... objects) {

    }

    @Override
    public void onClickStartError(String url, Object... objects) {

    }

    @Override
    public void onClickStop(String url, Object... objects) {

    }

    @Override
    public void onClickStopFullscreen(String url, Object... objects) {

    }

    @Override
    public void onClickResume(String url, Object... objects) {

    }

    @Override
    public void onClickResumeFullscreen(String url, Object... objects) {

    }

    @Override
    public void onClickSeekbar(String url, Object... objects) {

    }

    @Override
    public void onClickSeekbarFullscreen(String url, Object... objects) {

    }

    @Override
    public void onAutoComplete(String url, Object... objects) {

    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {

    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        // ------- ???????????????????????????????????????????????????????????????-------
        // ??????????????????????????????????????? setNeedOrientationUtils(false)
        if (hasVideo) {
            if (orientationUtils != null) {
                orientationUtils.backToProtVideo();
            }
        }
    }

    @Override
    public void onQuitSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onEnterSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekVolume(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekPosition(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekLight(String url, Object... objects) {

    }

    @Override
    public void onPlayError(String url, Object... objects) {

    }

    @Override
    public void onClickStartThumb(String url, Object... objects) {

    }

    @Override
    public void onClickBlank(String url, Object... objects) {

    }

    @Override
    public void onClickBlankFullscreen(String url, Object... objects) {

    }

    @Override
    public void onComplete(String url, Object... objects) {

    }

    public boolean hideActionBarWhenFull() {
        return true;
    }

    public boolean hideStatusBarWhenFull() {
        return true;
    }

    /**
     * ??????????????? OrientationUtils
     */
    public OrientationOption getOrientationOption() {
        return null;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    public boolean isAutoFullWithSize() {
        return false;
    }
}