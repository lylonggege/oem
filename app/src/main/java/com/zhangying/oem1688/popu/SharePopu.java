package com.zhangying.oem1688.popu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.PositionPopupView;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.PinLeiAdpter;
import com.zhangying.oem1688.adpter.PinLeiChilden1Adpter;
import com.zhangying.oem1688.adpter.PinLeiChilden2Adpter;
import com.zhangying.oem1688.adpter.ShareItemAdpter;
import com.zhangying.oem1688.bean.ShareBean;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.onterface.IJumPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SharePopu extends BottomPopupView {
    private MyRecycleView MyRecycleView_share;
    private Context mContext;
    private ShareItemAdpter shareAdpter;
    private ShareBean shareBean;
    
    public SharePopu(@NonNull Context context, @NonNull ShareBean shareBean) {
        super(context);
        this.mContext = context;
        this.shareBean = shareBean;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.sharepopup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        List<String> itemList = Arrays.asList("1","2","3","4");

        MyRecycleView_share = findViewById(R.id.MyRecycleView_Share);
        WidgetUtils.initGridRecyclerView(MyRecycleView_share, 4, DensityUtils.dp2px(2));
        shareAdpter = new ShareItemAdpter(mContext,shareBean);

        shareAdpter.refresh(itemList);
        MyRecycleView_share.setAdapter(shareAdpter);

        TextView btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}