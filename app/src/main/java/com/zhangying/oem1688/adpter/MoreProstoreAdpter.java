package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.IMessageView;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoreProstoreAdpter extends BaseRecyclerAdapter<MoreProstoreBean.RetvalBean.DataBean> {
    public MoreProstoreAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.moreprostoreitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MoreProstoreBean.RetvalBean.DataBean retval) {

        ImageView company_loge_iv = holder.findViewById(R.id.company_loge_iv);
        company_loge_iv.setVisibility(View.GONE);
        TextView companynameTv = holder.findViewById(R.id.companyname_tv);
        companynameTv.setText(retval.getStorename());
        companynameTv.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        companynameTv.getPaint().setStrokeWidth(0.5f);

        TextView companynameAuthtagTv = holder.findViewById(R.id.companyname_authtag_tv);
        companynameAuthtagTv.setText(retval.getAuthtag());
        TextView companyStoretimeTv = holder.findViewById(R.id.company_storetime_tv);
        String storetime = retval.getStoretime();
        if (storetime != null && storetime.length() > 0) {
            companyStoretimeTv.setText(storetime);
            companyStoretimeTv.setVisibility(View.VISIBLE);
        } else {
            companyStoretimeTv.setVisibility(View.GONE);
        }

        String sColor = retval.getScolor();
        LinearLayout vipView = holder.findViewById(R.id.dian);
        GradientDrawable drawable = (GradientDrawable) vipView.getBackground();
        if (sColor.length() > 0){
            companynameAuthtagTv.setBackgroundColor(Color.parseColor(sColor));
            drawable.setStroke(2, Color.parseColor(sColor));//??????????????????????????????
        } else {
            companynameAuthtagTv.setBackgroundColor(context.getColor(R.color.redf04142));
            drawable.setStroke(2, context.getColor(R.color.redf04142));
        }

        TextView cateTv = holder.findViewById(R.id.cate_tv);
        cateTv.setText(retval.getStoretip() + retval.getService());

        TextView address_tv1 = holder.findViewById(R.id.address_tv1);
        address_tv1.setVisibility(View.VISIBLE);
        address_tv1.setText(retval.getStorearea());

        List<MoreProstoreBean.RetvalBean.DataBean.StoretagsBean> storetags = retval.getStoretags();
        TextView tuijianTv = holder.findViewById(R.id.tuijian_tv);
        TextView companyVerificationTv = holder.findViewById(R.id.company_verification_tv);
        TextView rootViewBangTv = holder.findViewById(R.id.rootView_bang_tv);
        LinearLayout rootViewBangLl = holder.findViewById(R.id.rootView_bang_ll);
        if (storetags != null && storetags.size() > 0) {
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

        MyRecycleView recycview = holder.findViewById(R.id.recycview);
        recycview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        MoreProstoreChidrenAdpter moreProstoreChidrenAdpter = new MoreProstoreChidrenAdpter(context);
        moreProstoreChidrenAdpter.refresh(retval.getGlist());
        moreProstoreChidrenAdpter.setiClickEvent(new IMessageView() {
            @Override
            public void viewPosition(int position) {
                FactoryDetailActivity.simpleActivity(context, retval.getStoreid());
            }
        });
        recycview.setAdapter(moreProstoreChidrenAdpter);

        CardView cardview = holder.findViewById(R.id.cardview);
        cardview.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                FactoryDetailActivity.simpleActivity(context, retval.getStoreid());
            }
        });
    }
}
