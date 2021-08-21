package com.zhangying.oem1688.custom.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.zhangying.oem1688.util.ScreenTools;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView {
    private static final long TIME_AUTO_POLL = 8000;
    private final int screenWidth;
    AutoPollTask autoPollTask;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false

    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenWidth = ScreenTools.instance(context).getScreenWidth();
        autoPollTask = new AutoPollTask(this, context);
    }

    static class AutoPollTask implements Runnable {
        private Context context;
        private final WeakReference<AutoPollRecyclerView> mReference;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollRecyclerView reference, Context context) {
            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
            this.context = context;
        }

        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.scrollBy(ScreenTools.instance(context).getScreenWidth(), 1);
                recyclerView.postDelayed(recyclerView.autoPollTask, recyclerView.TIME_AUTO_POLL);
            }
        }
    }

    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }

    float start_x = 0;
    float endx = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                start_x = e.getX();
                Log.e("滑动的距离", "onScrolled:开始----" + start_x);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:

                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }


}