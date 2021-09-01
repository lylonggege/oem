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
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseMessageListener;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.GlobalEntitySingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
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
 * 店铺详情
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
    private Fragment factoryDetailClassFragment, factoryDetailFragment;

    private static String mcid;
    private FactoryDetailBean.RetvalBean retval;
    private boolean ishomne = true;
    private BaseValidateCredentials fenLeiRealization;
    private static int tabIndex;
    private GoodsDetailPopu msgPop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_factory_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHomeCateSate(ishomne);
        EventBus.getDefault().register(this);
        AppManagerUtil.getInstance().addHomeActivity(this);
        fenLeiRealization = new FenLeiRealization(this, this);

        LoadingDialog loading = new LoadingDialog(this);
        loading.show();

        if (tabIndex == 1) {
            setHomeCateSate(false);
        }
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("cid", mcid);
        RemoteRepository.getInstance()
                .get_store(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryDetailBean>() {

                    @Override
                    protected void success(FactoryDetailBean data) {
                        loading.dismiss();
                        retval = data.getRetval();

                        //切换到首页
                        selectTab(tabIndex);

                        //已收藏
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
            R.id.rootView_shop_b_sc_ll, R.id.rootView_phone,
            R.id.rootView_line, R.id.rootView_shop_b_dp_ll, R.id.bacK_RL, R.id.imageView2, R.id.textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL://返回
                finish();
                break;
            case R.id.rootView_shop_b_dp_ll: //首頁
                setHomeCateSate(true);
                selectTab(0);
                break;
            case R.id.rootView_shop_b_sp_ll: //分類
                if (retval.getGcates() != null && retval.getGcates().size() > 0) {
                    setHomeCateSate(false);
                    selectTab(1);
                } else {
                    ToastUtil.showToast("暂无分类数据");
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
            case R.id.rootView_phone://拨打电话
                canCallPhone();
                break;
            case R.id.rootView_line:
                WeiXinActivity.init(this);
                break;
            case R.id.message_LL://打开留言弹窗
                doShowMessagePop();
                break;
            case R.id.imageView2://顶部导航右侧显示全部品类
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView://打开搜索框
                SearchActivity.simpleActivity(this);
                break;
        }
    }

    public static void simpleActivity(Context context, String cid) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        context.startActivity(intent);
    }

    public static void simpleActivity(Context context, String cid, int tIndex) {
        Intent intent = new Intent(context, FactoryDetailActivity.class);
        mcid = cid;
        tabIndex = tIndex;
        context.startActivity(intent);
    }

    //显示留言弹窗
    private void doShowMessagePop(){
        if (msgPop == null){
            msgPop = new GoodsDetailPopu(this);
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
     * 提交留言到服务器
     * @param name 姓名
     * @param phone 电话
     */
    private boolean doSubmitMessage(String name, String phone){
        if (StringUtils.isEmity(name)){
            ToastUtil.showToast("请填写姓名");
            return false;
        }

        if (StringUtils.isEmity(phone)){
            ToastUtil.showToast("请填写电话");
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

        //提交服务器
        RemoteRepository.getInstance()
                .add_message(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()){
                            ToastUtil.showToast("留言成功");

                            //非vip留言成功后拨打电话
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

    //vip直接拨打，非vip打开留言弹窗，留言后拨打
    private void canCallPhone(){
        if (retval.getIsvip() == 1){//是vip
            doCallPhone();
        }else {//非vip打开留言弹窗
            String callTip = retval.getCalltip();
            if (!StringUtils.isEmity(callTip)) ToastUtil.showToast(callTip);
            doShowMessagePop();
        }
    }

    //拨打电话
    private void doCallPhone(){
        String tel = retval.getTel();
        if (StringUtils.isEmity(tel)){
            ToastUtil.showToast("哎呦!工厂还未设置电话");
            return;
        }

        new XPopup.Builder(this)
                .hasShadowBg(true)
                .asConfirm("提示", retval.getEndbtn3() + "    " + tel,
                        "取消", "拨打",
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
                                        ToastUtil.showToast("拨打电话权限被拒绝，请手动拨打！");
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

    //添加收藏
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
                            ToastUtils.toast("收藏成功");
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

    //取消收藏
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
                            ToastUtils.toast("取消收藏成功");
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
         * b true  点击首页
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

    //登录成功后路由返回数据
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