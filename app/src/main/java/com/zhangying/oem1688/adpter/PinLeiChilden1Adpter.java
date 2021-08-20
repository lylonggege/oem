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
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.JumpViewPage;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.util.GlideUtil;

public class PinLeiChilden1Adpter extends BaseRecyclerAdapter<SitetopinfoBean.RetvalBean.childrenBean> {
    public void setJumPage(IJumPage jumPage) {
        this.jumPage = jumPage;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    private String storeid;

    private IJumPage jumPage;

    public PinLeiChilden1Adpter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.pinleipopu1;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SitetopinfoBean.RetvalBean.childrenBean item) {
        LinearLayout rootView = holder.findViewById(R.id.rootView);
        TextView content_tv = holder.findViewById(R.id.name_tv);
        content_tv.setText(item.getCatename());
        ImageView imageView = holder.findViewById(R.id.imageView);
        GlideUtil.loadImage(context, item.getCatelogo(), imageView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stype = 1;
                String sid = item.getCateid() + "_" + 0;
                JumpViewPage jumpViewPage = new JumpViewPage();
                jumpViewPage.intentActivity(context, stype, sid, item.getCatename());
                jumPage.success();
            }
        });

    }
}
