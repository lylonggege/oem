package com.zhangying.oem1688.onterface;

import android.view.View;

public interface BaseInterfacePosition {
     /**
      * @param position  点击的位置
      * @param isboolean 点击的按钮是否被点击过
      * @param view      单击的按钮
      */
     void getPosition(int position, Boolean isboolean, View view);
}
