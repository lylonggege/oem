package com.zhangying.oem1688.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VerticalScrolledListview extends LinearLayout {
    private Context mContext;
    private List<String> data = new ArrayList<String>();
    private OnItemClickListener itemClickListener;

    public VerticalScrolledListview(Context context) {
        super(context);

        init(context);
    }

    public VerticalScrolledListview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setOrientation(VERTICAL);
    }

    /**
     * 更新文字需要在UI线程
     */
    Handler mHandler = new Handler();
    Runnable mUpdateResults = new Runnable() {
        public void run() {
            traversalView(VerticalScrolledListview.this);
        }
    };

    private void initTextView() {
        if (data != null && data.size() != 0) {
            String[] strArr = null;
            for (int i = 0; i < data.size(); i++) {
                LinearLayout lineRow = new LinearLayout(mContext);
                lineRow.setOrientation(HORIZONTAL);
                this.addView(lineRow);

                strArr = data.get(i).split(",");

                //左边文本
                TextView txtViewLeft = new TextView(mContext);
                txtViewLeft.setText(strArr[0]);
                txtViewLeft.setMaxLines(1);
                txtViewLeft.setTextColor(Color.parseColor("#333333"));
                lineRow.addView(txtViewLeft);

                LinearLayout.LayoutParams layoutParamsL = (LayoutParams) txtViewLeft.getLayoutParams();
                layoutParamsL.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParamsL.setMargins(20, 20, 10, 0);

                txtViewLeft.setClickable(true);
                final int index = i;
                txtViewLeft.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            itemClickListener.onItemClick(index);
                        }catch (Exception e){
                        }
                    }
                });

//                TextView txtViewMiddle = new TextView(mContext);
//                lineRow.addView(txtViewMiddle);
//                LinearLayout.LayoutParams layoutParamsM = (LayoutParams) txtViewMiddle.getLayoutParams();
//                layoutParamsM.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//                layoutParamsM.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//                layoutParamsM.weight = 1;


                //右边文本
                TextView txtViewRight = new TextView(mContext);
                txtViewRight.setText(strArr[1]);
                txtViewRight.setMaxLines(1);
                txtViewRight.setTextColor(Color.parseColor("#999999"));
                lineRow.addView(txtViewRight);

                LinearLayout.LayoutParams layoutParamsR = (LayoutParams) txtViewRight.getLayoutParams();
                layoutParamsR.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParamsR.setMargins(10, 20, 20, 0);

                txtViewRight.setClickable(true);
                txtViewRight.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(index);
                    }
                });
            }
            //初始化控件结束调用计时器
            startTimer();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * @param data view初始化优先于此方法
     *             so控件需要手动调动填充
     */
    public void setData(List<String> data) {
        this.data = data;
        initTextView();
    }

    public void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //将数据源重新排序
                mHandler.post(mUpdateResults);
            }
        };
        timer.schedule(task, 2000, 2000);

    }

    /**
     * 遍历view的所有子控件设值，不用在创建
     *
     * @param viewGroup
     */
    public void traversalView(final ViewGroup viewGroup) {
        if (viewGroup.getChildCount() != 0) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (i == 0) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 此处必须这么处理，先removeview子view，解除与父类关系，再添加进去
                             * 否则会报错java.lang.IllegalStateException
                             */
                            LinearLayout newView = (LinearLayout) viewGroup.getChildAt(0);
                            viewGroup.removeView(viewGroup.getChildAt(0));
                            viewGroup.addView(newView);
                        }
                    });

                }
            }
        }
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(VerticalScrolledListview.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }


}