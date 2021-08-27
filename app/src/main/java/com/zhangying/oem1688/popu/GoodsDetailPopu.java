package com.zhangying.oem1688.popu;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.DrawerPopupView;
import com.lxj.xpopup.core.PositionPopupView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.onterface.BaseMessageListener;

public class GoodsDetailPopu extends PositionPopupView {
    private EditText name_et_popu;
    private EditText phone_et_popu;
    private TextView submit_tv_popu;
    private LinearLayout rootView;

    public GoodsDetailPopu(@NonNull Context context) {
        super(context);
    }

    public void setMessageLister(BaseMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    private BaseMessageListener messageListener;

    @Override
    protected int getImplLayoutId() {
        return R.layout.goodsdetailopopu;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        name_et_popu = findViewById(R.id.name_et_popu);
        phone_et_popu = findViewById(R.id.phone_et_popu);
        submit_tv_popu = findViewById(R.id.submit_tv_popu);
        rootView = findViewById(R.id.rootView);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                destroy();
            }
        });

        submit_tv_popu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = messageListener.submit(name_et_popu.getText().toString(),phone_et_popu.getText().toString());
                if (result)destroy();
            }
        });
    }
}
