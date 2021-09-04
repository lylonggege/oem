package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.ListCollectBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.onterface.ICallBackPosition;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

public class ListCollectAdpter extends BaseRecyclerAdapter<ListCollectBean.RetvalBean.RecordListBean> {
    public ListCollectAdpter(Context context) {
        this.context = context;
    }

    private Context context;

    public void setiCallBackPosition(ICallBackPosition iCallBackPosition) {
        this.iCallBackPosition = iCallBackPosition;
    }

    private ICallBackPosition iCallBackPosition;


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.mycollectionitem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, ListCollectBean.RetvalBean.RecordListBean item) {
        TextView company_kehu_name_tv = holder.findViewById(R.id.company_kehu_name_tv);
        RadiusImageView company_loge_iv = holder.findViewById(R.id.company_loge_iv);
        TextView company_tv = holder.findViewById(R.id.company_tv);
        TextView Collect_tv = holder.findViewById(R.id.Collect_tv);
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                FactoryDetailActivity.simpleActivity(context, item.getStore_id());
            }
        });


        company_kehu_name_tv.setText(item.getStore_name());
        company_tv.setText(item.getService());
        GlideUtil.loadImage(context, item.getStore_logo(), company_loge_iv);

        Collect_tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                iCallBackPosition.getPosition(position);
            }
        });
    }
}
