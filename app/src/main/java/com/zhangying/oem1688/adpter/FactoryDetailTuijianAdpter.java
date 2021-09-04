package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

import java.util.List;

import static com.xuexiang.xui.utils.ResUtils.getResources;

public class FactoryDetailTuijianAdpter extends BaseRecyclerAdapter<FactoryDetailBean.RetvalBean.OstoresBean> {


    public FactoryDetailTuijianAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.factorymessage;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, FactoryDetailBean.RetvalBean.OstoresBean item) {
        RelativeLayout companyname_rootview = holder.findViewById(R.id.companyname_rootview);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) companyname_rootview.getLayoutParams();
        layoutParams.setMargins(0, 15, 0, 0);
        companyname_rootview.setLayoutParams(layoutParams);

        RadiusImageView company_loge_iv = holder.findViewById(R.id.company_loge_iv);
        TextView companyname_tv = holder.findViewById(R.id.companyname_tv);
        TextView companyname_authtag_tv = holder.findViewById(R.id.companyname_authtag_tv);
        TextView company_storetime_tv = holder.findViewById(R.id.company_storetime_tv);
        TextView cate_tv = holder.findViewById(R.id.cate_tv);
        TextView rootView_bang_tv = holder.findViewById(R.id.rootView_bang_tv);
        TextView tuijian_tv = holder.findViewById(R.id.tuijian_tv);
        TextView company_verification_tv = holder.findViewById(R.id.company_verification_tv);

        LinearLayout rootView_bang_ll = holder.findViewById(R.id.rootView_bang_ll);

        GlideUtil.loadImage(context, item.getStorelogo(), company_loge_iv);
        companyname_tv.setText(item.getStorename());
        companyname_authtag_tv.setText(item.getAuthtag());
        String storetime = item.getStoretime();
        if (storetime.length() > 0) {
            company_storetime_tv.setVisibility(View.VISIBLE);
            company_storetime_tv.setText(storetime);
        } else {
            company_storetime_tv.setVisibility(View.GONE);
        }
        cate_tv.setText(item.getStoretip() + item.getService());

        String sColor = item.getScolor();
        LinearLayout dian = holder.findViewById(R.id.dian);
        GradientDrawable drawable = (GradientDrawable) dian.getBackground();
        if (sColor.length() > 0){
            drawable.setStroke(2, Color.parseColor(sColor));//设置边框的宽度和颜色
            companyname_authtag_tv.setBackgroundColor(Color.parseColor(sColor));
        }else {
            drawable.setStroke(2, getResources().getColor(R.color.redf04142));
        }

        List<FactoryDetailBean.RetvalBean.storetagsBean> storetags = item.getStoretags();
        if (storetags.size() > 0) {
            if (storetags.get(0) != null) {
                tuijian_tv.setText(storetags.get(0).getStag());
            } else {
                tuijian_tv.setVisibility(View.GONE);
            }

            if (storetags.size() >= 2 && storetags.get(1) != null) {
                company_verification_tv.setText(storetags.get(1).getStag());
            } else {
                company_verification_tv.setVisibility(View.GONE);
            }
        } else {
            tuijian_tv.setVisibility(View.GONE);
            company_verification_tv.setVisibility(View.GONE);
        }

        if (item.getStorebang() != null && item.getStorebang().length() > 0) {
            rootView_bang_tv.setText(item.getStorebang());
        } else {
            rootView_bang_ll.setVisibility(View.GONE);
        }

        RelativeLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                FactoryDetailActivity.simpleActivity(context, item.getStoreid());
            }
        });


    }
}
