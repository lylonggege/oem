package com.zhangying.oem1688.popu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.DrawerPopupView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xutil.data.DateUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.util.ToastUtil;

public class LabeRightPopu extends DrawerPopupView {

    private Context context;
    private TextView start_time_tv;
    private TextView end_time_tv;
    private EditText address_et;
    private TextView sure_tv;
    private TextView clear_tv;
    private MyRecycleView recycview;


    public LabeRightPopu(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.laberightpopu;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        start_time_tv = findViewById(R.id.start_time_tv);
        end_time_tv = findViewById(R.id.end_time_tv);
        address_et = findViewById(R.id.address_et);
        sure_tv = findViewById(R.id.sure_tv);
        clear_tv = findViewById(R.id.clear_tv);
        recycview = findViewById(R.id.recycview);

//        start_time_tv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePicker();
//            }
//        });
//
//        end_time_tv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePicker();
//            }
//        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();
    }


}
