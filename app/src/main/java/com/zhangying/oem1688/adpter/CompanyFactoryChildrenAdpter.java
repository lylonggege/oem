package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.OemcateAreaBeanChildren;
import com.zhangying.oem1688.mvp.factory.ComapanyFactoryAdpter;
import com.zhangying.oem1688.util.ToastUtil;

public class CompanyFactoryChildrenAdpter extends BaseRecyclerAdapter<OemcateAreaBeanChildren> {

    private ComapanyFactoryAdpter comapanyFactoryAdpter;

    public void setComapanyFactoryAdpter(ComapanyFactoryAdpter comapanyFactoryAdpter) {
        this.comapanyFactoryAdpter = comapanyFactoryAdpter;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.textview;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, OemcateAreaBeanChildren item) {
        TextView textView = holder.findViewById(R.id.textView);
        TextView line_tv = holder.findViewById(R.id.line_tv);
        LinearLayout root_tv = holder.findViewById(R.id.root_tv);
        line_tv.setVisibility(View.VISIBLE);
        textView.setText(item.getName());
        if (item.getSelect()) {
            textView.setTextColor(Color.parseColor("#ff3600"));
        } else {
            textView.setTextColor(Color.parseColor("#666666"));
        }
        root_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(item.getName());
                comapanyFactoryAdpter.Success(null,item,position);
            }
        });
    }
}
