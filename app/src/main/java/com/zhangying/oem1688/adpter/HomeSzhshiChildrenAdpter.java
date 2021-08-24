package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.utils.DrawableUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.custom.JumpViewPage;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

public class HomeSzhshiChildrenAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.SzhshlistBean.ZhshimgBean> {
    public HomeSzhshiChildrenAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.zhshlist_item_children;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.SzhshlistBean.ZhshimgBean item) {
        TextView content_tv = (TextView) holder.findView(R.id.textView_content);
        ImageView imageView = (ImageView) holder.findView(R.id.imageView);
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        content_tv.setText(item.getAd_name());
        GlideUtil.loadImage(context, item.getAd_logo(), imageView);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpViewPage jumpViewPage = new JumpViewPage();
                jumpViewPage.intentActivity(context, item.getLink_type(), item.getLink_id(),"");

            }
        });
    }
}
