package com.zhangying.oem1688.adpter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.WordsBean;

public class WordsAdpter extends BaseRecyclerAdapter<WordsBean.GoodsBena> {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.wordsitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, WordsBean.GoodsBena item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView state_tv = holder.findViewById(R.id.state_tv);
        TextView time_tv = holder.findViewById(R.id.time_tv);
        name_tv.setText(item.getStitle());
        state_tv.setText(item.getIcheck());
        time_tv.setText(item.getAddtime());
    }
}
