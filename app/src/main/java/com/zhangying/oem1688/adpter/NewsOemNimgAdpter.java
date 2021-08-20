package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.util.GlideUtil;

public class NewsOemNimgAdpter extends BaseRecyclerAdapter<String> {
    public NewsOemNimgAdpter(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.newsoemrecyleviewitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        ImageView image = holder.findViewById(R.id.image);
        GlideUtil.loadImage(context,item,image);
    }
}
