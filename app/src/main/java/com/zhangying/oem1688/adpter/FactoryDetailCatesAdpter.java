package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.custom.MyRecycleView;

public class FactoryDetailCatesAdpter extends BaseRecyclerAdapter<FactoryDetailBean.RetvalBean.GcatesBean> {
    public FactoryDetailCatesAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.factorydetailcatesitem;

    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, FactoryDetailBean.RetvalBean.GcatesBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        MyRecycleView recyclerView = holder.findViewById(R.id.MyRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        name_tv.setText(item.getValue());
        FactoryDetailCatesHorizontalAdpter factoryDetailCatesHorizontalAdpter = new FactoryDetailCatesHorizontalAdpter(context);
        factoryDetailCatesHorizontalAdpter.refresh(item.getGlist());
        recyclerView.setAdapter(factoryDetailCatesHorizontalAdpter);

    }
}
