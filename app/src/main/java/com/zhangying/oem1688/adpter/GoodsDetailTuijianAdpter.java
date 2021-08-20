package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.GoodsDetailActivity;

public class GoodsDetailTuijianAdpter extends BaseRecyclerAdapter<GoodsdetailBean.RetvalBean.GoodsBean.OgoodsBean> {
    public GoodsDetailTuijianAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.goodsdetailtuijianitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, GoodsdetailBean.RetvalBean.GoodsBean.OgoodsBean item) {
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        TextView content_tv = (TextView) holder.findView(R.id.textView_content);
        RadiusImageView imageView = (RadiusImageView) holder.findView(R.id.imageView);
        content_tv.setText(item.getGoods_name());
        GlideUtil.loadImage(context, item.getDefault_image(), imageView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsDetailActivity.simpleActivity(context, item.getGoods_id());
            }
        });

    }
}
