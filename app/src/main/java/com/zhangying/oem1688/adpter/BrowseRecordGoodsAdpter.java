package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.onterface.ICallBcak;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.view.activity.home.GoodsDetailActivity;

public class BrowseRecordGoodsAdpter extends BaseRecyclerAdapter<ListHistoryBean.RetvalBean.RecordListBean> {
    public BrowseRecordGoodsAdpter(Context context) {
        this.context = context;
    }
    private Context context;

    public void setiJumPage(IJumPage iJumPage) {
        this.iJumPage = iJumPage;
    }

    private IJumPage iJumPage;


    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.browserecord_good_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, ListHistoryBean.RetvalBean.RecordListBean item) {
        TextView content_tv = (TextView) holder.findView(R.id.textView_content);
        RadiusImageView imageView = (RadiusImageView) holder.findView(R.id.imageView);
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        RelativeLayout checksign_rl = holder.findViewById(R.id.checksign_rl);
        content_tv.setText(item.getItem_name());

        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {

            }
        });
        LinearLayout.LayoutParams layoutParamsiv = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        int Morelength = ScreenTools.instance(context).dip2px(10);
        int screenWidth = ScreenTools.instance(context).getScreenWidth();
        layoutParamsiv.width = (screenWidth - Morelength) / 2;
        layoutParamsiv.height = layoutParamsiv.width;
        imageView.setLayoutParams(layoutParamsiv);

        GlideUtil.loadImage(context, item.getItem_cover(), imageView);

        TextView time_tv = (TextView) holder.findView(R.id.time_tv);
        time_tv.setText("浏览时间:"+item.getAdd_time());

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
