package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.OemcateAreaBeanChildren;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;

public class CompanyFactoryTabberAdpter extends BaseRecyclerAdapter<CompanyFactoryBean.RetvalBean.ProgslistBean.GoodsBean> {
    public CompanyFactoryTabberAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.good_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, CompanyFactoryBean.RetvalBean.ProgslistBean.GoodsBean item) {
        TextView content_tv = (TextView) holder.findView(R.id.textView_content);
        RadiusImageView imageView = (RadiusImageView) holder.findView(R.id.imageView);
        LinearLayout.LayoutParams layoutParamsiv = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        int Morelength = ScreenTools.instance(context).dip2px(10);
        int screenWidth = ScreenTools.instance(context).getScreenWidth();
        layoutParamsiv.width = (screenWidth - Morelength) / 2;
        layoutParamsiv.height = layoutParamsiv.width;
        imageView.setLayoutParams(layoutParamsiv);

        content_tv.setText(item.getGoods_name());
        GlideUtil.loadImage(context, item.getDefault_image(), imageView);

    }
}
