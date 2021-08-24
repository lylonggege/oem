package com.zhangying.oem1688.view.activity.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.GoodsDetailOemAdpter;
import com.zhangying.oem1688.adpter.GoodsDetailTuijianAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.leave.DateBean;
import com.zhangying.oem1688.mvp.leave.LeaveMessagePersenterImpl;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.GoodsDetailPopu;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhangying.oem1688.util.WeiXinActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.xutil.tip.ToastUtils.toast;

/**
 * 产品详情页面
 */
public class GoodsDetailActivity extends BaseActivity implements BaseView {

    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView_banner)
    ImageView imageView_banner;
    @BindView(R.id.message_LL)
    LinearLayout messageLL;
    @BindView(R.id.company_loge_iv)
    ImageView company_loge_iv;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHomeCateSate(ishomne);
        AppManagerUtil.getInstance().addHomeActivity(this);
        showLoading();
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
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
        company_storetime_tv.setText(store_data.getStoretime());
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

        GoodsDetailOemAdpter goodsDetailOemAdpter = new GoodsDetailOemAdpter();
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
        WidgetUtils.initGridRecyclerView(tuijianRecycleView, 3, DensityUtils.dp2px(5));
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
            , R.id.imageView2, R.id.textView})
    public void onClick(View view) {
        GoodsdetailBean.RetvalBean.StoreDataBean store_data = goodsdetailBean.getRetval().getStore_data();
        switch (view.getId()) {
            case R.id.bacK_RL://返回
                finish();
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
                                return false;
                            }
                        })
                        .dismissOnTouchOutside(true)
                        .asCustom(new GoodsDetailPopu(this))
                        .show();

                break;
            case R.id.submit_tv:
                String name = nameEt.getText().toString();
                if (name == null || name.length() == 0) {
                    ToastUtil.showToast("请输入您的姓名");
                    return;
                }

                String phone = phoneEt.getText().toString();
                if (phone == null || phone.length() == 0) {
                    ToastUtil.showToast("请输入您的电话");
                    return;
                }
                GoodsdetailBean.RetvalBean retval = goodsdetailBean.getRetval();
                StringBuffer stringBuffer = new StringBuffer();
                List<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> store_gcates = retval.getStore_data().getStore_gcates();
                for (int i = 0; i < store_gcates.size(); i++) {
                    GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean storeGcatesBean = store_gcates.get(i);
                    if (storeGcatesBean.isaBoolean()) {
                        stringBuffer.append(storeGcatesBean.getId() + ",");
                    }
                }

                //提交信息
                DateBean dateBean = new DateBean();
                dateBean.setName(name);
                dateBean.setPhone(phone);
                dateBean.setLycomId(retval.getStore_data().getStore_id());
                dateBean.setLycate(stringBuffer.toString());
                dateBean.setLylm("goods");
                dateBean.setId(retval.getGoods().getGoods_id());
                dateBean.setLyagent("7");
                basePresenter = new LeaveMessagePersenterImpl(this, dateBean);
                basePresenter.validateCredentials();

                break;
            case R.id.rootView_shop_b_dp_ll:

                break;
            case R.id.rootView_shop_b_sp_ll:

                break;
            case R.id.rootView_shop_b_sc_ll:
                int has_collect = store_data.getHas_collect();
                if (has_collect == 0) {
                    storecollect();
                } else {
                    drop_collect();
                }
                break;
            case R.id.rootView_phone:

                new XPopup.Builder(this)
                        .hasShadowBg(true)
                        .asConfirm("提示", store_data.getEndbtn3() + "    " + store_data.getTel(),
                                "取消", "拨打",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {

                                        AutoForcePermissionUtils.requestPermissions(GoodsDetailActivity.this, new AutoForcePermissionUtils.PermissionCallback() {

                                            @Override
                                            public void onPermissionGranted() {
                                                Intent intent = new Intent(Intent.ACTION_CALL);
                                                Uri data = Uri.parse("tel:" + store_data.getTel());
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

    private void storecollect() {
        showLoading();
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", goodsdetailBean.getRetval().getStore_data().getStore_id());
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
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", goodsdetailBean.getRetval().getStore_data().getStore_id());
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
