package com.zhangying.oem1688.adpter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;


public class HomeTabAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.SindustryBean> {
    int mypositon;

    public void setPositon(int positon) {
        this.mypositon = positon;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.home_title_tab;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.SindustryBean item) {
        TextView name = (TextView) holder.findView(R.id.name);
        TextView line = (TextView) holder.findView(R.id.line);
        LinearLayout rootView = (LinearLayout) holder.findView(R.id.rootView);
        holder.text(R.id.name, item.getCate_name());
        if (mypositon == position) {
            name.setTextSize(16);
            line.setVisibility(View.VISIBLE);
        } else {
            line.setVisibility(View.GONE);
            name.setTextSize(14);
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mypositon = position;
                
                notifyDataSetChanged();
            }
        });
    }
}
