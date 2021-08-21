package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;

public class MoreProstoreChidrenAdpter extends BaseRecyclerAdapter<MoreProstoreBean.RetvalBean.DataBean.GlistBean> {
    public MoreProstoreChidrenAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.factorydetailcateshorizontal_bg;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MoreProstoreBean.RetvalBean.DataBean.GlistBean item) {
        RadiusImageView image = holder.findViewById(R.id.image);
        RelativeLayout relative = holder.findViewById(R.id.relative);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relative.getLayoutParams();
        int surplus = ScreenTools.instance(context).getScreenWidth() - ScreenTools.instance(context).dip2px(65);
        layoutParams.width = surplus / 4;
        layoutParams.height = layoutParams.width;
        int i10 = ScreenTools.instance(context).dip2px(5);
        relative.setLayoutParams(layoutParams);
        layoutParams.setMargins(i10, i10, 0, i10);
        image.setCornerRadius(5);
        GlideUtil.loadImage(context, item.getDefault_image(), image);
    }
}
