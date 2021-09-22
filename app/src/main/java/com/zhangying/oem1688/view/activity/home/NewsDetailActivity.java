package com.zhangying.oem1688.view.activity.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.NewscontBean;
import com.zhangying.oem1688.bean.ScinfoDetailBean;
import com.zhangying.oem1688.bean.ShareBean;
import com.zhangying.oem1688.custom.ShareRealization;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.leave.DateBean;
import com.zhangying.oem1688.mvp.leave.LeaveMessagePersenterImpl;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.WebViewSeting;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends BaseActivity implements BaseView {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.title_contont_TV)
    TextView titleContontTV;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.companyname_tv)
    TextView companynameTv;
    @BindView(R.id.companyliaxiren_tv)
    TextView companyliaxirenTv;
    @BindView(R.id.companyphone_tv)
    TextView companyphoneTv;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.submit_tv)
    TextView submitTv;
    @BindView(R.id.company_message_type2)
    LinearLayout company_message_type2;
    @BindView(R.id.company_submit_type2)
    LinearLayout company_submit_type2;
    @BindView(R.id.images_ll)
    LinearLayout images_ll;
    @BindView(R.id.cinfoContent)
    TextView cinfoContent;
    @BindView(R.id.share_RL)
    RelativeLayout shareRL;
    private int nid;
    private int type;
    private BasePresenter basePresenter;
    private NewscontBean mnewscontBean;
    private ScinfoDetailBean mscinfoDetailBean;
    private BaseValidateCredentials shareRealization;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nid = getIntent().getIntExtra("NID", 0);
        type = getIntent().getIntExtra("TYPE", 0);
        titleContontTV.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        titleContontTV.getPaint().setStrokeWidth(1.3f);
        newscont();
    }

    private void newscont() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String blueColor = "#025BDE";
        if (type == 1) {
            shareRL.setVisibility(View.VISIBLE);
            hashMap.put("ly", "app");
            hashMap.put("nid", nid);
            RemoteRepository.getInstance()
                    .newscont(hashMap)
                    .subscribeWith(new DefaultDisposableSubscriber<NewscontBean>() {
                        @Override
                        protected void success(NewscontBean newscontBean) {
                            mnewscontBean = newscontBean;
                            cinfoContent.setVisibility(View.GONE);
                            company_message_type2.setVisibility(View.GONE);
                            company_submit_type2.setVisibility(View.GONE);
                            images_ll.setVisibility(View.GONE);
                            NewscontBean.RetvalBean retval = newscontBean.getRetval();
                            NewscontBean.RetvalBean.NewsinfoBean newsinfo = retval.getNewsinfo();
                            NewscontBean.RetvalBean.CompinfoBean compinfo = retval.getCompinfo();
                            titleTV.setText(newsinfo.getTitle());
                            titleContontTV.setText(newsinfo.getTitle());
                            String time = newsinfo.getAdd_time() + "    ";
                            String text = time + retval.getNewsnav().getStitle();
                            SpannableStringBuilder style = new SpannableStringBuilder(text);
                            style.setSpan(new ForegroundColorSpan(Color.parseColor(blueColor)), time.length(), text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                            timeTv.setText(style);
                            String s = MyUtilsWebView.setWebViewText(newsinfo.getContent(),"font-size:16px;color:#333333;line-height:1.6","");
                            WebViewSeting.setting(webView, NewsDetailActivity.this, s);
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            ToastUtil.showToast("服务器参数异常...");
                        }
                    });
        } else if (type == 2) {
            hashMap.put("ly", "app");
            hashMap.put("cid", nid);
            RemoteRepository.getInstance()
                    .scinfo_detail(hashMap)
                    .subscribeWith(new DefaultDisposableSubscriber<ScinfoDetailBean>() {
                        @Override
                        protected void success(ScinfoDetailBean newscontBean) {
                            try {
                                mscinfoDetailBean = newscontBean;
                                webView.setVisibility(View.GONE);
                                cinfoContent.setVisibility(View.VISIBLE);
                                company_message_type2.setVisibility(View.VISIBLE);
                                company_submit_type2.setVisibility(View.VISIBLE);
                                images_ll.setVisibility(View.VISIBLE);
                                ScinfoDetailBean.RetvalBean retval = newscontBean.getRetval();
                                titleTV.setText(retval.getTitle());
                                titleContontTV.setText(retval.getTitle());
                                cinfoContent.setText(retval.getContent());
                                String time = retval.getAdd_time() + "    ";
                                String text = time + retval.getCompname();
                                SpannableStringBuilder style = new SpannableStringBuilder(text);
                                style.setSpan(new ForegroundColorSpan(Color.parseColor(blueColor)), time.length(), text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                timeTv.setText(style);
                                List<ScinfoDetailBean.RetvalBean.ImagesBean> images = retval.getImages();
                                if (images != null && images.size() > 0) {
                                    for (int i = 0; i < images.size(); i++) {
                                        ScinfoDetailBean.RetvalBean.ImagesBean imagesBean = images.get(i);
                                        ImageView imageView = new ImageView(NewsDetailActivity.this);
                                        GlideUtil.loadImage(NewsDetailActivity.this, imagesBean.getUrl(), imageView);
                                        images_ll.addView(imageView);
                                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                        layoutParams.width = ScreenTools.instance(NewsDetailActivity.this).getScreenWidth() - ScreenTools.instance(NewsDetailActivity.this).dip2px(30);
                                        layoutParams.height = layoutParams.width * imagesBean.getH() / imagesBean.getW();
                                        imageView.setLayoutParams(layoutParams);
                                    }
                                }
                                companynameTv.setText("公司:  " + retval.getCompname());
                                companyliaxirenTv.setText("联系人:  " + retval.getName());
                                SpannableStringBuilder stylephone = new SpannableStringBuilder("联系电话:  " + retval.getStel());
                                stylephone.setSpan(new ForegroundColorSpan(Color.parseColor(blueColor)), 6, stylephone.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                companyphoneTv.setText(stylephone);
                            } catch (Exception e) {
                                ToastUtil.showToast("服务器参数异常...");
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            ToastUtil.showToast("服务器参数异常...");
                        }
                    });
        }
    }

    public static void simpleActivity(Context context, int nid, int type) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("NID", nid);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }

    @OnClick({R.id.bacK_RL, R.id.submit_tv, R.id.share_RL})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.share_RL://点击分享
                if (shareRealization == null){
                    ShareBean shareBean = new ShareBean();
                    shareBean.setTitle(mnewscontBean.getRetval().getPageinfo().getShareTitle());
                    shareBean.setDesc(mnewscontBean.getRetval().getPageinfo().getShareCont());
                    shareBean.setUrl(mnewscontBean.getRetval().getPageinfo().getShareUrl());
                    shareBean.setImage(mnewscontBean.getRetval().getPageinfo().getShareCover());
                    shareRealization = new ShareRealization(this,shareBean);
                }
                shareRealization.validateCredentials();
                break;
            case R.id.submit_tv:
                String name = nameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                if (name == null || name.length() == 0) {
                    ToastUtil.showToast("姓名不能为空");
                    return;
                }
                if (phone == null || phone.length() == 0) {
                    ToastUtil.showToast("电话不能为空");
                    return;
                }
                //提交信息
                DateBean dateBean = new DateBean();
                dateBean.setName(name);
                dateBean.setPhone(phone);
                if (type == 1) {
                    NewscontBean.RetvalBean.CompinfoBean compinfo = mnewscontBean.getRetval().getCompinfo();
                    dateBean.setLycomId(compinfo.getStoreid() + "");
                    dateBean.setLycate("");
                    dateBean.setLylm("news");
                    dateBean.setId(mnewscontBean.getRetval().getNewsinfo().getArticle_id());
                    dateBean.setLyagent("7");
                } else if (type == 2) {
                    ScinfoDetailBean.RetvalBean retval = mscinfoDetailBean.getRetval();
                    dateBean.setLycomId(retval.getStore_id());
                    dateBean.setLycate("");
                    dateBean.setLylm("scinfo");
                    dateBean.setId(retval.getId());
                    dateBean.setLyagent("7");
                }
                basePresenter = new LeaveMessagePersenterImpl(this, dateBean);
                basePresenter.validateCredentials();
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
        BaseBean bean = (BaseBean) o;
        ToastUtil.showToast(bean.getMsg());
    }
}