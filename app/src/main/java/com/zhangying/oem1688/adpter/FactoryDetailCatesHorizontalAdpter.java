package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.view.activity.home.GoodsDetailActivity;

public class FactoryDetailCatesHorizontalAdpter extends BaseRecyclerAdapter<FactoryDetailBean.RetvalBean.GcatesBean.GlistBean> {
    public FactoryDetailCatesHorizontalAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.factorydetailcateshorizontal;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, FactoryDetailBean.RetvalBean.GcatesBean.GlistBean item) {
        RadiusImageView image = holder.findViewById(R.id.image);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) image.getLayoutParams();
        layoutParams.width = ScreenTools.instance(context).getScreenWidth() / 2;
        layoutParams.height = ScreenTools.instance(context).getScreenWidth() / 2;
        int i10 = ScreenTools.instance(context).dip2px(5);
        image.setLayoutParams(layoutParams);
        layoutParams.setMargins(i10, i10, 0, i10);
        GlideUtil.loadImage(context, item.getDefault_image(), image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDetailActivity.simpleActivity(context,item.getGoods_id());
            }
        });
    }
}
