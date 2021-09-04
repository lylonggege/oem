package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.onterface.OnMultiClickListener;


public class GoodsDetailOemAdpter extends BaseRecyclerAdapter<GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean> {

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.goodsdetailoemitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, GoodsdetailBean.RetvalBean.StoreDataBean.StoreGcatesBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        name_tv.setText(item.getValue());

        if (item.isaBoolean()) {
            name_tv.setSelected(true);
            name_tv.setTextColor(Color.WHITE);
        } else {
            name_tv.setSelected(false);
            name_tv.setTextColor(Color.parseColor("#666666"));
        }
        name_tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                if (item.isaBoolean()) {
                    item.setaBoolean(false);
                } else {
                    item.setaBoolean(true);
                }
                notifyDataSetChanged();
            }
        });

    }
}
