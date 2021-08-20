package com.zhangying.oem1688.adpter;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.util.ScreenTools;

public class LabelAdpter extends BaseRecyclerAdapter<MessageListBean.RetvalBean> {

    private int type;
    private Context context;


    public LabelAdpter(Context context) {
        this.context = context;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.label;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, MessageListBean.RetvalBean item) {
        TextView name_tv = holder.findViewById(R.id.name_tv);
        TextView address_tv = holder.findViewById(R.id.address_tv);
        TextView time_tv = holder.findViewById(R.id.time_tv);
        TextView see_tv = holder.findViewById(R.id.see_tv);
        name_tv.setText(item.getS_name());
        address_tv.setText(item.getS_areatype());
        time_tv.setText(item.getAdd_time());
        if (type == 0 || type == 1) {
            String s_view_ys = item.getS_view_ys();
            if (s_view_ys.equals("1")) {
                see_tv.setTextColor(Color.argb(255, 35, 109, 232));
            } else {
                see_tv.setTextColor(Color.argb(255, 255, 54, 0));
            }
            see_tv.setText(item.getS_view_txt());
        } else {
            see_tv.setText(item.getS_view_nums() + item.getS_view_numtxt());
        }

        RelativeLayout rootView = holder.findViewById(R.id.rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(context);// 取得LayoutInflater对象
                View popView = inflater.inflate(R.layout.labepopu, null);
                PopupWindow popupWindow = new PopupWindow(popView, ScreenTools.instance(context).getScreenWidth(), ScreenTools.instance(context).getScreenHeight(), true);
                TextView close_tv = popView.findViewById(R.id.close_tv); // 取得组件
                TextView name_tv = popView.findViewById(R.id.name_tv);
                TextView address_tv = popView.findViewById(R.id.address_tv);

                name_tv.setText(item.getS_name());
                address_tv.setText(item.getS_areatype());
                close_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);        // 显示弹出窗口

            }
        });


    }
}
