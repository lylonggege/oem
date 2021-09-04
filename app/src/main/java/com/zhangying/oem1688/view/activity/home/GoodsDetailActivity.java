package com.zhangying.oem1688.view.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.GoodsDetailOemAdpter;
import com.zhangying.oem1688.adpter.GoodsDetailTuijianAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusMessageBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.leave.DateBean;
import com.zhangying.oem1688.mvp.leave.LeaveMessagePersenterImpl;
import com.zhangying.oem1688.onterface.BaseMessageListener;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhangying.oem1688.util.WeiXinActivity;
import com.zhangying.oem1688.view.activity.entry.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 产品详情页面
 */
public class GoodsDetailActivity extends BaseActivity implements BaseView {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.imageView_banner)
    ImageView imageView_banner;
    @BindView(R.id.message_LL)
    LinearLayout messageLL;
    @BindView(R.id.company_loge_iv)
    RadiusImageView company_loge_iv;
    @BindView(R.id.companyname_tv)
    TextView companynameTv;
    @BindView(R.id.dian)
    LinearLayout dian;
    @BindView(R.id.cate_tv)
    TextView cateTv;
    @BindView(R.id.line_sign)
    LinearLayout lineSign;
    @BindView(R.id.tuijian_tv)
    TextView tuijianTv;
    @BindView(R.id.webView)
    WebView webView;
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
    @BindView(R.id.companyname_authtag_tv)
    TextView companyname_authtag_tv;
    @BindView(R.id.company_verification_tv)
    TextView company_verification_tv;
    @BindView(R.id.company_storetime_tv)
    TextView company_storetime_tv;
    @BindView(R.id.goods_name)
    TextView goods_name;
    private static String goods_id;
    @BindView(R.id.goods_verification_tv)
    TextView goodsVerificationTv;
    @BindView(R.id.goostuijian_tv)
    TextView goostuijianTv;
    @BindView(R.id.im_tip_tv)
    TextView imTipTv;
    @BindView(R.id.rootView_bang_tv)
    TextView rootViewBangTv;
    @BindView(R.id.rootView_bang_ll)
    LinearLayout rootViewBangLl;
    @BindView(R.id.shop_b_dp_tv)
    TextView shopBDpTv;
    @BindView(R.id.shop_b_sp_db_tv)
    TextView shopBSpDbTv;
    @BindView(R.id.rootview_shoucang_tv)
    TextView rootviewShoucangTv;
    @BindView(R.id.rootView_phone)
    TextView rootViewPhone;
    @BindView(R.id.rootView_line)
    TextView rootViewLine;
    @BindView(R.id.rootview_shoucang_iv)
    ImageView rootviewShoucangIv;
    @BindView(R.id.cata_rl)
    RelativeLayout caterl;
    @BindView(R.id.shop_b_dp_image)
    ImageView shop_b_dp_image;
    @BindView(R.id.shop_b_sp_db_iv)
    ImageView shop_b_sp_db_iv;
    private GoodsdetailBean goodsdetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    private BasePresenter basePresenter;
    private boolean ishomne = true;
    private BaseValidateCredentials fenLeiRealization;
    private GoodsDetailPopu msgPop;
    private boolean isToCall = false;
    private GoodsDetailOemAdpter goodsDetailOemAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        companynameTv.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        companynameTv.getPaint().setStrokeWidth(1.0f);

        goods_name.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        goods_name.getPaint().setStrokeWidth(1.0f);

        fenLeiRealization = new FenLeiRealization(this, this);

        setHomeCateSate(ishomne);
        AppManagerUtil.getInstance().addHomeActivity(this);
        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", goods_id);
        RemoteRepository.getInstance()
                .goodsdetail(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<GoodsdetailBean>() {

                    @Override
                    protected void success(GoodsdetailBean data) {
                        goodsdetailBean = data;
                        dissmissLoading();
                        GoodsdetailBean.RetvalBean retval = data.getRetval();
                        titleTV.setText(retval.getGoods().getGoods_name());

                        int has_collect = retval.getStore_data().getHas_collect();
                        if (has_collect == 0) {
                            rootviewShoucangIv.setSelected(false);
                        } else {
                            rootviewShoucangIv.setSelected(true);
                        }
                        //产品
                        GoodsdetailBean.RetvalBean.GoodsBean goods = retval.getGoods();
                        setViewGoods(goods);
                        //公司类
                        GoodsdetailBean.RetvalBean.StoreDataBean store_data = retval.getStore_data();
                        setViewStore(store_data);
                        //推荐
                        GoodsdetailBean.RetvalBean.PageinfoBean pageinfo = retval.getPageinfo();
                        setViewPageinfo(pageinfo);
                        //品类

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    private void setViewPageinfo(GoodsdetailBean.RetvalBean.PageinfoBean pageinfo) {

    }

    //公司
    private void setViewStore(GoodsdetailBean.RetvalBean.StoreDataBean store_data) {
        GlideUtil.loadImage(this, store_data.getStore_logo(), company_loge_iv);
        companynameTv.setText(store_data.getStore_name());
        companyname_authtag_tv.setText(store_data.getAuthtag());
        cateTv.setText(store_data.getStoretip() + store_data.getService());
        List<GoodsdetailBean.RetvalBean.goods_tagsBean> goods_tags = store_data.getStoretags();
        if (goods_tags.size() > 0) {
            if (goods_tags.get(0) != null) {
                tuijianTv.setText(goods_tags.get(0).getStag());
            } else {
                tuijianTv.setVisibility(View.GONE);
            }

            if (goods_tags.get(1) != null) {
                company_verification_tv.setText(goods_tags.get(1).getStag());
            } else {
                company_verification_tv.setVisibility(View.GONE);
            }
        }else {
            company_verification_tv.setVisibility(View.GONE);
            tuijianTv.setVisibility(View.GONE);
        }

        String storetime = store_data.getStoretime();
        if (storetime.length() > 0) {
            company_storetime_tv.setText(storetime);
        } else {
            company_storetime_tv.setVisibility(View.GONE);
        }

        //非vip设置颜色
        String sColor = store_data.getScolor();
        GradientDrawable drawable = (GradientDrawable) dian.getBackground();
        if (sColor.length() > 0){
            drawable.setStroke(2, Color.parseColor(sColor));//设置边框的宽度和颜色
            companyname_authtag_tv.setBackgroundColor(Color.parseColor(sColor));
        }else {
            drawable.setStroke(2, this.getColor(R.color.redf04142));
        }

        if (store_data.getStorebang() != null && store_data.getStorebang().length() > 0) {
            rootViewBangTv.setText(store_data.getStorebang());
        } else {
            rootViewBangLl.setVisibility(View.GONE);
        }

        goodsDetailOemAdpter = new GoodsDetailOemAdpter();
        goodsDetailOemAdpter.refresh(store_data.getStore_gcates());
        WidgetUtils.initGridRecyclerView(oemRecycleView, 3, DensityUtils.dp2px(10));
        oemRecycleView.setAdapter(goodsDetailOemAdpter);

        shopBDpTv.setText(store_data.getEndbtn1());
        shopBSpDbTv.setText(store_data.getEndbtn2());
        rootViewPhone.setText(store_data.getEndbtn3());
        rootViewLine.setText(store_data.getEndbtn4());
    }

    //产品详情
    private void setViewGoods(GoodsdetailBean.RetvalBean.GoodsBean goods) {
        GlideUtil.loadImage(this, goods.getDefault_image(), imageView_banner);
        goods_name.setText(goods.getGoods_name());
        List<GoodsdetailBean.RetvalBean.goods_tagsBean> goods_tags = goods.getGoods_tags();
        if (goods_tags.size() > 0) {
            if (goods_tags.get(0) != null) {
                goodsVerificationTv.setText(goods_tags.get(0).getStag());
            } else {
                goodsVerificationTv.setVisibility(View.GONE);
            }

            if (goods_tags.get(1) != null) {
                goostuijianTv.setText(goods_tags.get(1).getStag());
            } else {
                goostuijianTv.setVisibility(View.GONE);
            }
        } else {
            caterl.setVisibility(View.GONE);
        }

        imTipTv.setText(goods.getIm_tip());
        String s = MyUtilsWebView.setWebViewText(goods.getDescription());
        WebViewSeting.setting(webView, this, s);

        GoodsDetailTuijianAdpter goodsDetailTuijianAdpter = new GoodsDetailTuijianAdpter(this);
        goodsDetailTuijianAdpter.refresh(goods.getOgoods());
        tuijianRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        tuijianRecycleView.setAdapter(goodsDetailTuijianAdpter);
    }

    /**
     * @param context
     * @param id      产品id
     */
    public static void simpleActivity(Context context, String id) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        goods_id = id;
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL,R.id.submit_tv, R.id.rootView_shop_b_dp_ll,
            R.id.rootView_shop_b_sp_ll, R.id.rootView_shop_b_sc_ll,
            R.id.rootView_phone, R.id.rootView_line, R.id.message_LL
            , R.id.imageView2, R.id.textView,R.id.rootView})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        GoodsdetailBean.RetvalBean.StoreDataBean store_data = goodsdetailBean.getRetval().getStore_data();
        switch (view.getId()) {
            case R.id.bacK_RL://返回
                finish();
                break;
            case R.id.rootView:
                FactoryDetailActivity.simpleActivity(this,goodsdetailBean.getRetval().getGoods().getStore_id());
                break;
            case R.id.message_LL://打开留言层留言
                isToCall = false;
                doShowMessagePop();
                break;
            case R.id.submit_tv://界面提交留言
                doSubmitMessage(nameEt.getText().toString(),phoneEt.getText().toString(),true);
                break;
            case R.id.rootView_shop_b_dp_ll://工厂首页
                FactoryDetailActivity.simpleActivity(this,store_data.getStore_id(),0);
                break;
            case R.id.rootView_shop_b_sp_ll://工厂产品
                FactoryDetailActivity.simpleActivity(this,store_data.getStore_id(), 1);
                break;
            case R.id.rootView_shop_b_sc_ll://收藏或取消收藏
                boolean hasLogin = LoginActivity.simpleActivity(this, BuildConfig.PRODUCT_ENTER_TYPE);
                if (!hasLogin) {
                    break;
                }

                int has_collect = store_data.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
                break;
            case R.id.rootView_phone:
                canCallPhone();
                break;
            case R.id.rootView_line://打开微信客服
                WeiXinActivity.init(this);
                break;
            case R.id.imageView2://顶部导航右侧显示平台全部分类
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView://打开搜索界面
                SearchActivity.simpleActivity(this);
                break;
        }
    }

    //vip直接拨打，非vip打开留言弹窗，留言后拨打
    private void canCallPhone(){
        isToCall = true;
        GoodsdetailBean.RetvalBean.StoreDataBean store = goodsdetailBean.getRetval().getStore_data();
        if (store.getIsvip() == 1){//是vip
            doCallPhone();
        }else {//非vip打开留言弹窗
            String callTip = store.getCalltip();
            if (!StringUtils.isEmity(callTip)) ToastUtil.showToast(callTip);
            doShowMessagePop();
        }
    }

    //拨打电话
    private void doCallPhone(){
        GoodsdetailBean.RetvalBean.StoreDataBean store = goodsdetailBean.getRetval().getStore_data();
        new XPopup.Builder(this)
                .hasShadowBg(true)
                .asConfirm("提示", store.getEndbtn3() + "    " + store.getTel(),
                        "取消", "拨打",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                AutoForcePermissionUtils.requestPermissions(GoodsDetailActivity.this, new AutoForcePermissionUtils.PermissionCallback() {

                                    @Override
                                    public void onPermissionGranted() {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        Uri data = Uri.parse("tel:" + store.getTel());
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

    //显示留言弹窗
    private void doShowMessagePop(){
        if (msgPop == null){
            msgPop = new GoodsDetailPopu(this);
            msgPop.setMessageLister(new BaseMessageListener() {
                @Override
                public boolean submit(String name, String phone) {
                    return doSubmitMessage(name,phone,false);
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
                        return false;
                    }
                })
                .dismissOnTouchOutside(true)
                .asCustom(msgPop);
        popView.popupInfo.popupAnimation = PopupAnimation.ScaleAlphaFromCenter;
        popView.show();
    }

    private boolean doSubmitMessage(String name, String phone, boolean chkCate){
        if (StringUtils.isEmity(name)) {
            ToastUtil.showToast("请输入您的姓名");
            return false;
        }

        if (phone == null || phone.length() == 0) {
            ToastUtil.showToast("请输入您的电话");
            return false;
        }

        GoodsdetailBean.RetvalBean retval = goodsdetailBean.getRetval();
        ArrayList<Integer> stringBuffer = new ArrayList<Integer>();
        if (chkCate){
            List<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> store_gcates = retval.getStore_data().getStore_gcates();
            for (int i = 0; i < store_gcates.size(); i++) {
                GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean storeGcatesBean = store_gcates.get(i);
                if (storeGcatesBean.isaBoolean()) {
                    stringBuffer.add(storeGcatesBean.getId());
                }
            }

            if (stringBuffer.size() == 0){
                ToastUtil.showToast("请选择代工品类");
                return false;
            }
        }

        //提交信息
        DateBean dateBean = new DateBean();
        dateBean.setName(name);
        dateBean.setPhone(phone);
        dateBean.setLycomId(retval.getStore_data().getStore_id());
        dateBean.setLycate(TextUtils.join(",",stringBuffer));
        dateBean.setLylm("goods");
        dateBean.setId(retval.getGoods().getGoods_id());
        dateBean.setLyagent("7");

        basePresenter = new LeaveMessagePersenterImpl(this, dateBean);
        basePresenter.validateCredentials();
        return true;
    }

    private void storecollect() {
        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", goodsdetailBean.getRetval().getStore_data().getStore_id());
        RemoteRepository.getInstance()
                .storecollect(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            ToastUtil.showToast("收藏成功");
                            goodsdetailBean.getRetval().getStore_data().setHas_collect(1);
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
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", goodsdetailBean.getRetval().getStore_data().getStore_id());
        RemoteRepository.getInstance()
                .drop_collect(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        ToastUtil.showToast(data.getMsg());
                        if (data.isDone()) {
                            ToastUtil.showToast("取消收藏成功");
                            goodsdetailBean.getRetval().getStore_data().setHas_collect(0);
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
    public void showloading() {
        showLoading();
    }

    @Override
    public void hidenloading() {
        dissmissLoading();
    }

    @Override
    public void success(Object o) {
        BaseBean bean = (BaseBean) o;
        ToastUtil.showToast(bean.getMsg());

        //清空留言姓名、电话、选择的品类
        GoodsdetailBean.RetvalBean retval = goodsdetailBean.getRetval();
        List<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> store_gcates = retval.getStore_data().getStore_gcates();
        for (int i = 0; i < store_gcates.size(); i++) {
            GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean storeGcatesBean = store_gcates.get(i);
            if (storeGcatesBean.isaBoolean()) {
                storeGcatesBean.setaBoolean(false);
                goodsDetailOemAdpter.notifyItemChanged(i);
            }
        }
        nameEt.setText("");
        phoneEt.setText("");

        //拨打电话调用
        if (isToCall){
            doCallPhone();
        }
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

    //登录成功后路由返回数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventData(EvenBusMessageBean message) {
        if (message != null) {
            if (message.getType() == 1) {
                GoodsdetailBean.RetvalBean.StoreDataBean store_data = goodsdetailBean.getRetval().getStore_data();
                int has_collect = store_data.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
            }
        }
    }
}
