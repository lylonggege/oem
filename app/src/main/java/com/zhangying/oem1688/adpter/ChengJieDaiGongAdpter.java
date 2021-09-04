package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MoreScinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.BaseImagePreview;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ImageViewInfo;
import com.zhangying.oem1688.util.PreviewImageView;
import com.zhangying.oem1688.view.activity.home.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ChengJieDaiGongAdpter extends BaseRecyclerAdapter<MoreScinfoBean.RetvalBean.RecordListBean> {
    public ChengJieDaiGongAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.chengjiedaigongitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MoreScinfoBean.RetvalBean.RecordListBean item) {
        LinearLayout rootView_company = holder.findViewById(R.id.rootView_company);
        RadiusImageView company_loge_iv = holder.findViewById(R.id.company_loge_iv);
        TextView company_kehu_name_tv = holder.findViewById(R.id.company_kehu_name_tv);
        TextView companyphone_tv = holder.findViewById(R.id.companyphone_tv);
        TextView company_tv = holder.findViewById(R.id.company_tv);
        TextView company_contont_tv = holder.findViewById(R.id.company_contont_tv);
        MyRecycleView company_recycleview = (MyRecycleView) holder.findView(R.id.company_recycleview);
        TextView company_time_tv = holder.findViewById(R.id.company_time_tv);

        company_recycleview.setVisibility(View.VISIBLE);
        GlideUtil.loadImage(context, item.getPortrait(), company_loge_iv);
        company_kehu_name_tv.setText(item.getName());
        companyphone_tv.setText(item.getStel());
        company_tv.setText(item.getCompname());
        String cate_name = "#" + item.getNcate() + "#";
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

        rootView_company.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                NewsDetailActivity.simpleActivity(context, Integer.parseInt(item.getNid()), Integer.parseInt(item.getType()));
            }
        });
    }
}
