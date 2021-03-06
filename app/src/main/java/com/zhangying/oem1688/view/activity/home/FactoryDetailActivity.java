package com.zhangying.oem1688.view.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.widget.dialog.LoadingDialog;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusMessageBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.ShareBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.ShareRealization;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseMessageListener;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.GlobalEntitySingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.WeiXinActivity;
import com.zhangying.oem1688.view.activity.entry.LoginActivity;
import com.zhangying.oem1688.view.fragment.home.FactoryDetailClassFragment;
import com.zhangying.oem1688.view.fragment.home.FactoryDetailFragment;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ????????????
 */

public class FactoryDetailActivity extends BaseActivity implements BaseView {
    @BindView(R.id.rootView_shop_b_dp_ll)
    LinearLayout rootView_shop_b_dp_ll;
    @BindView(R.id.rootView_shop_b_sp_ll)
    LinearLayout rootView_shop_b_sp_ll;
    @BindView(R.id.rootView_shop_b_sc_ll)
    LinearLayout rootView_shop_b_sc_ll;
    @BindView(R.id.shop_b_dp_image)
    ImageView shop_b_dp_image;
    @BindView(R.id.shop_b_sp_db_iv)
    ImageView shop_b_sp_db_iv;
    @BindView(R.id.rootview_shoucang_iv)
    ImageView rootview_shoucang_iv;
    @BindView(R.id.shop_b_dp_tv)
    TextView shop_b_dp_tv;
    @BindView(R.id.shop_b_sp_db_tv)
    TextView shop_b_sp_db_tv;
    @BindView(R.id.rootview_shoucang_tv)
    TextView rootview_shoucang_tv;
    @BindView(R.id.title_TV)
    TextView navTitle;
    @BindView(R.id.share_RL)
    RelativeLayout shareRL;
    private Fragment factoryDetailClassFragment, factoryDetailFragment;

    private static String mcid;
    private FactoryDetailBean.RetvalBean retval;
    private BaseValidateCredentials fenLeiRealization;
    private BaseValidateCredentials shareRealization;
    private static int tabIndex;
    private GoodsDetailPopu msgPop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_factory_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        AppManagerUtil.getInstance().addHomeActivity(this);
        fenLeiRealization = new FenLeiRealization(this, this);
        shareRL.setVisibility(View.VISIBLE);

        LoadingDialog loading = new LoadingDialog(this);
        loading.show();

        if (tabIndex == 1) {
            setHomeCateSate(false);
        }else {
            setHomeCateSate(true);
        }
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("cid", mcid);
        RemoteRepository.getInstance()
                .get_store(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryDetailBean>() {

                    @Override
                    protected void success(FactoryDetailBean data) {
                        loading.dismiss();
                        if (!data.isDone()){
                            ToastUtil.showToast(data.getMsg());
                            finish();
                        }

                        retval = data.getRetval();
                        //???????????????
                        selectTab(tabIndex);

                        //?????????
                        if (retval.getHas_collect() == 1) {
                            rootView_shop_b_sc_ll.setSelected(true);
                        }
                        navTitle.setText(retval.getStore_name());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        loading.dismiss();
                    }
                });
    }

    @OnClick({R.id.rootView_shop_b_sp_ll, R.id.rootview_shoucang_tv,
            R.id.rootView_shop_b_sc_ll, R.id.rootView_phone,R.id.share_RL,
            R.id.rootView_line, R.id.rootView_shop_b_dp_ll, R.id.bacK_RL, R.id.imageView2, R.id.textView})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.bacK_RL://??????
                finish();
                break;
            case R.id.share_RL://????????????
                if (shareRealization == null){
                    ShareBean shareBean = new ShareBean();
                    shareBean.setTitle(retval.getPageinfo().getShareTitle());
                    shareBean.setDesc(retval.getPageinfo().getShareCont());
                    shareBean.setUrl(retval.getPageinfo().getShareUrl());
                    shareBean.setImage(retval.getPageinfo().getShareCover());
                    shareRealization = new ShareRealization(this,shareBean);
                }
                shareRealization.validateCredentials();
                break;
            case R.id.rootView_shop_b_dp_ll: //??????
                setHomeCateSate(true);
                selectTab(0);
                break;
            case R.id.rootView_shop_b_sp_ll: //??????
                if (retval.getGcates() != null && retval.getGcates().size() > 0) {
                    setHomeCateSate(false);
                    selectTab(1);
                } else {
                    ToastUtil.showToast("??????????????????");
                }
                break;
            case R.id.rootview_shoucang_tv:
                break;
            case R.id.rootView_shop_b_sc_ll:
                boolean hasLogin = LoginActivity.simpleActivity(this, BuildConfig.FACTORY_ENTER_TYPE);
                if (!hasLogin) {
                    break;
                }

                int has_collect = retval.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
                break;
            case R.id.rootView_phone://????????????
                canCallPhone();
                break;
            case R.id.rootView_line:
                WeiXinActivity.openService(this);
                break;
            case R.id.message_LL://??????????????????
                doShowMessagePop();
                break;
            case R.id.imageView2://????????????????????????????????????
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView://???????????????
                SearchActivity.simpleActivity(this);
                break;
        }
    }

    public static void simpleActivity(Context context, String cid) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        tabIndex = 0;
        context.startActivity(intent);
    }

    public static void simpleActivity(Context context, String cid, int tIndex) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        tabIndex = tIndex;
        context.startActivity(intent);
    }

    //??????????????????
    private void doShowMessagePop(){
        if (msgPop == null){
            msgPop = new GoodsDetailPopu(this);

            String popTitle = retval.getCalltitle();
            if (!StringUtils.isEmity(popTitle)){
                msgPop.setPopTitle(popTitle);
            }

            msgPop.setMessageLister(new BaseMessageListener() {
                @Override
                public boolean submit(String name, String phone) {
                    return doSubmitMessage(name, phone);
                }
            });
        }
        BasePopupView popView = new XPopup.Builder(this)
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
    }

    /**
     * ????????????????????????
     * @param name ??????
     * @param phone ??????
     */
    private boolean doSubmitMessage(String name, String phone){
        if (StringUtils.isEmity(name)){
            ToastUtil.showToast("???????????????");
            return false;
        }

        if (StringUtils.isEmity(phone)){
            ToastUtil.showToast("???????????????");
            return false;
        }

        if (phone.length() != 11){
            ToastUtil.showToast("????????????????????????????????????");
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
        HashMapSingleton.getInstance().put("lycate", "");

        //???????????????
        RemoteRepository.getInstance()
                .add_message(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()){
                            ToastUtil.showToast("????????????");

                            //???vip???????????????????????????
                            doCallPhone();
                        }else {
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

    //vip??????????????????vip????????????????????????????????????
    private void canCallPhone(){
        if (retval.getIsvip() == 1){//???vip
            doCallPhone();
        }else {//???vip??????????????????
            String callTip = retval.getCalltip();
            if (!StringUtils.isEmity(callTip)) ToastUtil.showToast(callTip);
            doShowMessagePop();
        }
    }

    //????????????
    private void doCallPhone(){
        String tel = retval.getTel();
        if (StringUtils.isEmity(tel)){
            ToastUtil.showToast("??????!????????????????????????");
            return;
        }

        new XPopup.Builder(this)
                .hasShadowBg(true)
                .asConfirm("??????", retval.getEndbtn3() + "    " + tel,
                        "??????", "??????",
                        new OnConfirmListener() {
                            @Override

                            public void onConfirm() {
                                AutoForcePermissionUtils.requestPermissions(FactoryDetailActivity.this, new AutoForcePermissionUtils.PermissionCallback() {
                                    @Override
                                    public void onPermissionGranted() {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + tel);
                                        intent.setData(data);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onPermissionDenied() {
                                        ToastUtil.showToast("????????????????????????????????????????????????");
                                    }
                                }, Manifest.permission.CALL_PHONE);

                            }
                        }, null, false)
                .show();
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
                    .placeholder(R.drawable.ic_launcher_background) //?????????
                    .error(R.drawable.ic_launcher_background)      //?????????
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
            layoutParams.width = Integer.parseInt(data.getW());
            layoutParams.height = Integer.parseInt(data.getH());
            mImageView.setLayoutParams(layoutParams);
            Glide.with(context).load(data.getUrl()).apply(options).into(mImageView);
        }
    }

    //????????????
    private void storecollect() {
        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", retval.getStore_id());
        RemoteRepository.getInstance()
                .storecollect(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            ToastUtils.toast("????????????");
                            retval.setHas_collect(1);
                            rootView_shop_b_sc_ll.setSelected(true);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    public FactoryDetailBean.RetvalBean getFactoryBean() {
        return retval;
    }

    //????????????
    private void drop_collect() {
        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", retval.getStore_id());
        RemoteRepository.getInstance()
                .drop_collect(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            ToastUtils.toast("??????????????????");
                            retval.setHas_collect(0);
                            rootView_shop_b_sc_ll.setSelected(false);
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
        EventBus.getDefault().unregister(this);
    }

    private void setHomeCateSate(Boolean b) {
        /**
         * b true  ????????????
         */
        if (b) {
            shop_b_dp_image.setSelected(true);
            shop_b_dp_tv.setSelected(true);
            shop_b_sp_db_iv.setSelected(false);
            shop_b_sp_db_tv.setSelected(false);
        } else {
            shop_b_dp_image.setSelected(false);
            shop_b_dp_tv.setSelected(false);
            shop_b_sp_db_iv.setSelected(true);
            shop_b_sp_db_tv.setSelected(true);
        }
    }

    private void selectTab(int index) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        hintAllFragment(transaction);
        switch (index) {
            case 0:
                setHomeCateSate(true);
                if (factoryDetailFragment == null) {
                    factoryDetailFragment = new FactoryDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("mcid", mcid);
                    factoryDetailFragment.setArguments(bundle);
                    GlobalEntitySingleton.getInstance().setFactoryDetail(retval);
                    transaction.add(R.id.fragment_container, factoryDetailFragment);
                } else {
                    transaction.show(factoryDetailFragment);
                }
                break;
            case 1:
                setHomeCateSate(false);
                if (factoryDetailClassFragment == null) {
                    factoryDetailClassFragment = new FactoryDetailClassFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("mcid", mcid);
                    factoryDetailClassFragment.setArguments(bundle);
                    transaction.add(R.id.fragment_container, factoryDetailClassFragment);
                } else {
                    transaction.show(factoryDetailClassFragment);
                }

                break;

        }

        transaction.commitAllowingStateLoss();
    }

    private void hintAllFragment(FragmentTransaction transaction) {
        if (factoryDetailClassFragment != null) {
            transaction.hide(factoryDetailClassFragment);
        }

        if (factoryDetailFragment != null) {
            transaction.hide(factoryDetailFragment);
        }
    }

    @Override
    public void showloading() {

    }

    @Override
    public void hidenloading() {

    }

    @Override
    public void success(Object o) {

    }

    //?????????????????????????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventData(EvenBusMessageBean message) {
        if (message != null) {
            if (message.getType() == 2) {
                int has_collect = retval.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
            }

        }
    }


}