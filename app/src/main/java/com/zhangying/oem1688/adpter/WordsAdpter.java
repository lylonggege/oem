package com.zhangying.oem1688.adpter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.util.StringUtils;

public class WordsAdpter extends BaseRecyclerAdapter<WordsBean.GoodsBena> {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.wordsitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, WordsBean.GoodsBena item) {
        Integer iCheck = 0;
        if (StringUtils.isNumeric(item.getIcheck())){
            iCheck = Integer.parseInt(item.getIcheck());
        }
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView state_tv = holder.findViewById(R.id.state_tv);
        TextView time_tv = holder.findViewById(R.id.time_tv);
        name_tv.setText(item.getStitle());
        if (iCheck == 1){
            state_tv.setTextColor(Color.parseColor("#ff3600"));
            state_tv.setText("已审");
        }else {
            state_tv.setTextColor(Color.parseColor("#999999"));
            state_tv.setText("待审");
        }

        time_tv.setText(item.getAddtime());
    }
}
