package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.view.activity.home.NewProductFactoryActivity;

public class ScatehdMenuAdpter extends BaseRecyclerAdapter<HomeBena.RetvalBean.ScatehdBean> {

    public ScatehdMenuAdpter(Context context,int itemW) {
        this.context = context;
        this.itemW = itemW;
    }

    private Context context;
    private int itemW;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.griditem;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, HomeBena.RetvalBean.ScatehdBean item) {
        ImageView iv_apply_image = holder.findViewById(R.id.iv_apply_image);
        TextView tv_apply_name = holder.findViewById(R.id.tv_apply_name);
        LinearLayout rootView = holder.findViewById(R.id.rootView);

        int itemH = rootView.getLayoutParams().height;
        rootView.setLayoutParams(new LinearLayout.LayoutParams(this.itemW,itemH));
        rootView.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                int stype;
                if (item.getStype() == 6 || item.getStype() == 7) {
                    stype = 1;
                } else {
                    stype = item.getStype();
                }

                String sid = item.getSid();
                NewProductFactoryActivity.simpleActivity(context, sid, stype, item.getSname(),item.getSname());
            }
        });
        GlideUtil.loadImage(context, item.getSpic(), iv_apply_image);
        tv_apply_name.setText(item.getSname());
    }

}
