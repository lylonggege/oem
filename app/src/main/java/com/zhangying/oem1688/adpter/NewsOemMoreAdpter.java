package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.OemNewsMoreBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.BaseImagePreview;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ImageViewInfo;
import com.zhangying.oem1688.util.PreviewImageView;
import com.zhangying.oem1688.view.activity.home.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsOemMoreAdpter extends BaseRecyclerAdapter<OemNewsMoreBean.RetvalBean> {
    public NewsOemMoreAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.oemnewsmore;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, OemNewsMoreBean.RetvalBean item) {
        int type = item.getType();
        //类型(1：新闻；2：动态).
        //有图片
        RelativeLayout news_type_1_ImageView = holder.findViewById(R.id.news_type_1_ImageView);
        //一张图片布局
        RelativeLayout news_type_1_ImageView_1 = holder.findViewById(R.id.news_type_1_ImageView_1);
        //公司布局
        LinearLayout rootView_company = holder.findViewById(R.id.rootView_company);
        //多图新闻
        MyRecycleView myRecycleView_type1 = holder.findViewById(R.id.myRecycleView_type1);
        TextView content_tv = holder.findViewById(R.id.content_tv);
        TextView ncate_tv = holder.findViewById(R.id.ncate_tv);
        TextView ntime_tv = holder.findViewById(R.id.ntime_tv);
        TextView nhits_tv = holder.findViewById(R.id.nhits_tv);

        //一张新闻图片的viewid
        RadiusImageView image_one = holder.findViewById(R.id.image_one);
        TextView content_tv_image_one = holder.findViewById(R.id.content_tv_image_one);
        TextView ncate_tv_image_one = holder.findViewById(R.id.ncate_tv_image_one);
        TextView ntime_tv_image_one = holder.findViewById(R.id.ntime_tv_image_one);
        TextView nhits_tv_image_one = holder.findViewById(R.id.nhits_tv_image_one);

        //动态
        RadiusImageView company_loge_iv = holder.findViewById(R.id.company_loge_iv);
        TextView company_kehu_name_tv = holder.findViewById(R.id.company_kehu_name_tv);
        TextView companyphone_tv = holder.findViewById(R.id.companyphone_tv);
        TextView company_tv = holder.findViewById(R.id.company_tv);
        TextView company_contont_tv = holder.findViewById(R.id.company_contont_tv);
        MyRecycleView company_recycleview = (MyRecycleView) holder.findView(R.id.company_recycleview);
        TextView company_time_tv = holder.findViewById(R.id.company_time_tv);

        news_type_1_ImageView.setVisibility(View.GONE);
        news_type_1_ImageView_1.setVisibility(View.GONE);
        rootView_company.setVisibility(View.GONE);

        LinearLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                NewsDetailActivity.simpleActivity(context, item.getNid(), type);
            }
        });

        if (type == 1) {
            List<String> nimg = item.getNimg();
            if (nimg != null && nimg.size() > 0) {
                myRecycleView_type1.setVisibility(View.VISIBLE);
                if (nimg.size() == 1) {
                    //一张新闻图片的布局
                    news_type_1_ImageView_1.setVisibility(View.VISIBLE);
                    GlideUtil.loadImage(context, nimg.get(0), image_one);
                    content_tv_image_one.setText(item.getNtitle());
                    ncate_tv_image_one.setText(item.getNcate());
                    ntime_tv_image_one.setText(item.getNtime());
                    nhits_tv_image_one.setText(item.getNhits());

                } else {
                    news_type_1_ImageView.setVisibility(View.VISIBLE);
                    myRecycleView_type1.setVisibility(View.VISIBLE);
                    WidgetUtils.initGridRecyclerView(myRecycleView_type1, 3, DensityUtils.dp2px(5));
                    NewsOemNimgAdpter newsOemNimgAdpter = new NewsOemNimgAdpter(context);
                    newsOemNimgAdpter.refresh(nimg);
                    newsOemNimgAdpter.setImagePreview(new BaseImagePreview() {
                        @Override
                        public void startPosition(int position,ImageView imageView) {
                            NewsDetailActivity.simpleActivity(context, item.getNid(), type);
                        }
                    });
                    myRecycleView_type1.setAdapter(newsOemNimgAdpter);

                    content_tv.setText(item.getNtitle());
                    ncate_tv.setText(item.getNcate());
                    ntime_tv.setText(item.getNtime());
                    nhits_tv.setText(item.getNhits());
                }
            } else {
                myRecycleView_type1.setVisibility(View.GONE);
            }
        } else if (type == 2) {
            rootView_company.setVisibility(View.VISIBLE);
            company_recycleview.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(context, item.getPortrait(), company_loge_iv);
            company_kehu_name_tv.setText(item.getName());
            companyphone_tv.setText(item.getStel());
            company_tv.setText(item.getCompname());
            String cate_name="#"+item.getNcate()+"#";
            String text = cate_name + item.getNtitle();
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.setSpan(new ForegroundColorSpan(Color.parseColor("#047cf7")), 0, cate_name.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            company_contont_tv.setText(style);
            company_time_tv.setText(item.getNtime());
            List<String> images = item.getNimg();
            if (images != null && images.size() > 0) {
                WidgetUtils.initGridRecyclerView(company_recycleview, 3, DensityUtils.dp2px(5));
                NewsOemNimgAdpter newsOemNimgAdpter = new NewsOemNimgAdpter(context);
                newsOemNimgAdpter.setImagePreview(new BaseImagePreview() {
                    @Override
                    public void startPosition(int position,ImageView imageView) {
                        List<ImageViewInfo> list = new ArrayList<>();
                        for (int i1 = 0; i1 < images.size(); i1++) {
                            ImageViewInfo imageViewInfo = new ImageViewInfo((String) images.get(i1));
                            list.add(imageViewInfo);
                        }
                        PreviewImageView.save(imageView, position, list);
                    }
                });
                newsOemNimgAdpter.refresh(images);
                company_recycleview.setAdapter(newsOemNimgAdpter);
            }

        }

    }
}
