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
import com.zhangying.oem1688.custom.scroll.AutoPollRecyclerView;
import com.zhangying.oem1688.custom.scroll.HorizontalPageLayoutManager;
import com.zhangying.oem1688.custom.scroll.PagingScrollHelper;

import java.util.ArrayList;
import java.util.List;

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
        AutoPollRecyclerView recyclerView = holder.findViewById(R.id.MyRecycleView);
        PagingScrollHelper scrollHelper = new PagingScrollHelper();//初始化横向管理器
        HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 2);//这里两个参数是行列，这里实现的是一行三列
        FactoryDetailCatesHorizontalAdpter factoryDetailCatesHorizontalAdpter = new FactoryDetailCatesHorizontalAdpter(context);
        scrollHelper.setUpRecycleView(recyclerView);//将横向布局管理器和recycler view绑定到一起
        recyclerView.setLayoutManager(horizontalPageLayoutManager);//设置为横向
        scrollHelper.updateLayoutManger();
        scrollHelper.scrollToPosition(0);//默认滑动到第一页
        recyclerView.setHorizontalScrollBarEnabled(true);

        if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
            recyclerView.start();

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        name_tv.setText(item.getValue());

        List<FactoryDetailBean.RetvalBean.GcatesBean.GlistBean> glist = item.getGlist();
        List<FactoryDetailBean.RetvalBean.GcatesBean.GlistBean> glists = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            for (int i = 0; i <  glist.size(); i++) {
                FactoryDetailBean.RetvalBean.GcatesBean.GlistBean bean1 = glist.get(i);
                FactoryDetailBean.RetvalBean.GcatesBean.GlistBean bean=new FactoryDetailBean.RetvalBean.GcatesBean.GlistBean();
                bean.setDefault_image(bean1.getDefault_image());
                bean.setGoods_id(bean1.getGoods_id());
                glists.add(bean);
            }
        }
        factoryDetailCatesHorizontalAdpter.refresh(glists);
        recyclerView.setAdapter(factoryDetailCatesHorizontalAdpter);
    }
}
