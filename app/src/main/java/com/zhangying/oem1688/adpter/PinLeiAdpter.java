package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.OnMultiClickListener;

public class PinLeiAdpter extends BaseRecyclerAdapter<SitetopinfoBean.RetvalBean.CatelistBean> {

    public void setBaseInterfacePosition(BaseInterfacePosition baseInterfacePosition) {
        this.baseInterfacePosition = baseInterfacePosition;
    }

    private BaseInterfacePosition baseInterfacePosition;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.sitetopinfopopu_left_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SitetopinfoBean.RetvalBean.CatelistBean item) {
        TextView tv_line = holder.findViewById(R.id.tv_line);
        TextView name_tv = holder.findViewById(R.id.name_tv);
        RelativeLayout rootView_left = holder.findViewById(R.id.rootView_left);
        name_tv.setText(item.getCatename());
        if (!item.isaBoolean()) {
            tv_line.setVisibility(View.INVISIBLE);
            name_tv.setTextColor(Color.parseColor("#666666"));
            rootView_left.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            tv_line.setVisibility(View.VISIBLE);
            name_tv.setTextColor(Color.parseColor("#ff3600"));
            rootView_left.setBackgroundColor(Color.parseColor("#ffffff"));

        }
        rootView_left.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                baseInterfacePosition.getPosition(position, item.isaBoolean(), rootView_left);
            }
        });
    }
}
