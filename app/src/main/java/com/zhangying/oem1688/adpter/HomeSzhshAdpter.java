package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.util.SpacesItemDecoration;

import static com.xuexiang.xui.utils.ResUtils.getResources;

public class HomeSzhshAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.SzhshlistBean> {
    public HomeSzhshAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.zhshlist_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.SzhshlistBean item) {
        TextView name_tv = (TextView) holder.findView(R.id.name);
        MyRecycleView recycView_szhshlist_item = (MyRecycleView) holder.findView(R.id.recycView_szhshlist_item);
        name_tv.setText(item.getZhshname());
        HomeSzhshiChildrenAdpter home_szhshiChildren_adpter = new HomeSzhshiChildrenAdpter(context);
        home_szhshiChildren_adpter.refresh(item.getZhshimg());
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recycView_szhshlist_item.addItemDecoration(new SpacesItemDecoration(space, space));
        recycView_szhshlist_item.setLayoutManager(new GridLayoutManager(context, 2));
        recycView_szhshlist_item.setAdapter(home_szhshiChildren_adpter);


    }
}
