package com.zhangying.oem1688.adpter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.FactoryGCateBean;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.OnMultiClickListener;

public class SitetopinfoLeftAdpter extends BaseRecyclerAdapter<FactoryGCateBean.RetvalBean> {

    public void setBaseInterfacePosition(BaseInterfacePosition baseInterfacePosition) {
        this.baseInterfacePosition = baseInterfacePosition;
    }

    private BaseInterfacePosition baseInterfacePosition;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.sitetopinfopopu_left_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, FactoryGCateBean.RetvalBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView tv_line = holder.findViewById(R.id.tv_line);
        RelativeLayout rootView_left = holder.findViewById(R.id.rootView_left);
        name_tv.setText(item.getValue());
        if (item.isIsboolean()) {
            tv_line.setVisibility(View.VISIBLE);
        } else {
            tv_line.setVisibility(View.INVISIBLE);
        }
        rootView_left.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                baseInterfacePosition.getPosition(position,true,rootView_left);
            }
        });
    }
}
