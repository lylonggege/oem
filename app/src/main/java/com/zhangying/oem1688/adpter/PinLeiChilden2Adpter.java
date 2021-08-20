package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.JumpViewPage;
import com.zhangying.oem1688.onterface.IJumPage;


public class PinLeiChilden2Adpter extends BaseRecyclerAdapter<SitetopinfoBean.RetvalBean.childrenBean> {
    public PinLeiChilden2Adpter(Context context) {
        this.context = context;
    }

    public void setJumPage(IJumPage jumPage) {
        this.jumPage = jumPage;
    }

    private IJumPage jumPage;
    private Context context;

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    private String storeid;

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.pinleipopu2;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SitetopinfoBean.RetvalBean.childrenBean item) {
        TextView content_tv = holder.findViewById(R.id.content_tv);
        content_tv.setText(item.getCatename());
        content_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int stype = 1;
                String sid = storeid + "_" + item.getCateid();
                JumpViewPage jumpViewPage = new JumpViewPage();
                jumpViewPage.intentActivity(context, stype, sid, item.getCatename());
                jumPage.success();
            }
        });
    }
}
