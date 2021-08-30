package com.zhangying.oem1688.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhangying.oem1688.R;

public class MyfollowsLL extends LinearLayout {
    public MyfollowsLL(Context context) {
        super(context);
    }

    public MyfollowsLL(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.myfragmnetmyfollows, this);
    }

    public MyfollowsLL(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
