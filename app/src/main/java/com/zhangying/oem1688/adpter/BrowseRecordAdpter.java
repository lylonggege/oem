package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;

public class BrowseRecordAdpter extends BaseRecyclerAdapter<ListHistoryBean.RetvalBean.RecordListBean> {
    public BrowseRecordAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    public void setiJumPage(IJumPage iJumPage) {
        this.iJumPage = iJumPage;
    }

    private IJumPage iJumPage;


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.browserecorditem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, ListHistoryBean.RetvalBean.RecordListBean item) {
        RadiusImageView image = holder.findViewById(R.id.image);
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView time_tv = holder.findViewById(R.id.time_tv);
        name_tv.setText(item.getItem_name());
        time_tv.setText("浏览时间:" + item.getAdd_time());
        GlideUtil.loadImage(context, item.getItem_cover(), image);
        RelativeLayout checksign_rl = holder.findViewById(R.id.checksign_rl);
        ImageView checksign_imageview = holder.findViewById(R.id.checksign_imageview);
        if (item.isIschecked()) {
            checksign_imageview.setVisibility(View.VISIBLE);
        } else {
            checksign_imageview.setVisibility(View.GONE);
        }

        if (item.isIschecked_bg()) {
            checksign_rl.setVisibility(View.VISIBLE);
        } else {
            checksign_rl.setVisibility(View.GONE);
        }


        checksign_rl.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                if (item.isIschecked()) {
                    item.setIschecked(false);
                } else {
                    item.setIschecked(true);
                }
                notifyItemChanged(position);
                iJumPage.success();
            }
        });

    }
}
