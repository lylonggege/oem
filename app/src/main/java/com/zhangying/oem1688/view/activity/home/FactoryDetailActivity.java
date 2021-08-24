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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.util.WeiXinActivity;
import com.zhangying.oem1688.view.fragment.home.FactoryDetailClassFragment;
import com.zhangying.oem1688.view.fragment.home.FactoryDetailFragment;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 店铺详情
 */

public class FactoryDetailActivity extends BaseActivity {

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
    private Fragment factoryDetailClassFragment,factoryDetailFragment;


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

        selectTab(0);
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
                        navTitle.setText(retval.getStore_name());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }


    @OnClick({R.id.rootView_shop_b_sp_ll, R.id.rootview_shoucang_tv,
            R.id.rootView_shop_b_sc_ll, R.id.rootView_phone,
            R.id.rootView_line, R.id.rootView_shop_b_dp_ll,R.id.bacK_RL})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.textView:
//                SearchActivity.simpleActivity(this);
//                break;
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
                            rootView_shop_b_sp_ll.setSelected(true);
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
                            rootView_shop_b_sp_ll.setSelected(false);
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
                    bundle.putString("mcid",mcid);
                    factoryDetailFragment.setArguments(bundle);
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
                    bundle.putString("mcid",mcid);
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
}