package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;

public class LabelRightAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.SindustryBean> {

    private BaseInterfacePosition baseInterfacePosition;

    public void setBaseInterfacePosition(BaseInterfacePosition baseInterfacePosition) {
        this.baseInterfacePosition = baseInterfacePosition;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.goodsdetailoemitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.SindustryBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        name_tv.setText(item.getCate_name());

        if (item.isIsboolean()) {
            name_tv.setSelected(true);
            name_tv.setTextColor(Color.WHITE);
        } else {
            name_tv.setSelected(false);
            name_tv.setTextColor(Color.parseColor("#666666"));
        }
        name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseInterfacePosition.getPosition(position, true, name_tv);
            }
        });
    }
}
