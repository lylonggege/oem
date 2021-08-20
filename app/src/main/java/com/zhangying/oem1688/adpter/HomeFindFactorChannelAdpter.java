package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.CateAreaListBean;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;

public class HomeFindFactorChannelAdpter extends BaseRecyclerAdapter<CateAreaListBean.RetvalBean.SchannelBean.QdlistBean> {

    public void setInterfacePosition(BaseInterfacePosition interfacePosition) {
        this.interfacePosition = interfacePosition;
    }

    private BaseInterfacePosition interfacePosition;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.findfactory;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, CateAreaListBean.RetvalBean.SchannelBean.QdlistBean item) {
        TextView tv_content = holder.findViewById(R.id.tv_content);
        RelativeLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getBoolean()) {
                    interfacePosition.getPosition(position, false, null);
                } else {
                    interfacePosition.getPosition(position, true, null);
                }
            }
        });
        if (item.getBoolean()) {
            tv_content.setTextColor(Color.WHITE);
            tv_content.setSelected(true);
        } else {
            tv_content.setSelected(false);
            tv_content.setTextColor(Color.parseColor("#666666"));
        }
        tv_content.setText(item.getSname());
    }
}
