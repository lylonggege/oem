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
import com.zhangying.oem1688.onterface.OnMultiClickListener;

public class HomeFindFactorParentAdpter extends BaseRecyclerAdapter<CateAreaListBean.catesBean> {

    public void setInterfacePosition(BaseInterfacePosition interfacePosition) {
        this.interfacePosition = interfacePosition;
    }

    private BaseInterfacePosition interfacePosition;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.findfactory;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, CateAreaListBean.catesBean item) {
        TextView tv_content = holder.findViewById(R.id.tv_content);
        RelativeLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                if (item.isBoolean()) {
//                    tv_content.setSelected(false);
//                    tv_content.setTextColor(Color.parseColor("#666666"));
//                    item.setBoolean(false);
                    interfacePosition.getPosition(position, false, null);
                } else {
//                    item.setBoolean(true);
//                    tv_content.setSelected(false);
//                    tv_content.setTextColor(Color.WHITE);
                    interfacePosition.getPosition(position, true, null);
                }
            }
        });
        if (item.isBoolean()) {
            tv_content.setTextColor(Color.WHITE);
            tv_content.setSelected(true);
        } else {
            tv_content.setSelected(false);
            tv_content.setTextColor(Color.parseColor("#666666"));
        }
        tv_content.setText(item.getName());
    }
}
