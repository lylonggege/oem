package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.OemcateAreaBean;
import com.zhangying.oem1688.mvp.factory.ComapanyFactoryAdpter;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.ToastUtil;

public class CompanyFactoryAdpter extends BaseRecyclerAdapter<OemcateAreaBean> {
    private ComapanyFactoryAdpter comapanyFactoryAdpter;

    public void setComapanyFactoryResult(ComapanyFactoryAdpter comapanyFactoryAdpter) {
        this.comapanyFactoryAdpter = comapanyFactoryAdpter;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.textview;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, OemcateAreaBean item) {
        TextView textView = holder.findViewById(R.id.textView);
        LinearLayout root_tv = holder.findViewById(R.id.root_tv);
        textView.setText(item.getName());
        if (item.isaBoolean()) {
            textView.setTextColor(Color.parseColor("#ff3600"));
        } else {
            textView.setTextColor(Color.parseColor("#666666"));
        }
        root_tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                ToastUtil.showToast(item.getName());
                comapanyFactoryAdpter.Success(item,null,position);
            }
        });
    }
}
