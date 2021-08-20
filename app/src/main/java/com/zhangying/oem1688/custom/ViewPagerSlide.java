package com.zhangying.oem1688.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/*禁止滑动viewpager*/
public class ViewPagerSlide extends ViewPager {
    private boolean isSlid = false;//是否可以滑倒标识  默认不滑动
    public ViewPagerSlide(@NonNull Context context) {
        super(context);
    }

    public ViewPagerSlide(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSlid(boolean isSlid){
        this.isSlid = isSlid;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return isSlid;
//    }

//    @Override
//    public boolean canScrollVertically(int direction) {
//        return false;
//    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return false;
    }
}

