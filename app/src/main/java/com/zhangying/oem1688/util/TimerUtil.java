package com.zhangying.oem1688.util;

import android.os.Handler;
import android.widget.TextView;

public class TimerUtil {

    private TextView textView;
    private long sec;
    private String msg;
    private Handler handler = new Handler();
    private Runnable downRunable =  new Runnable() {
        @Override
        public void run() {
            if(sec>=0) {
                sec--;
                textView.setText(sec + "秒");
                handler.postDelayed(downRunable, 1000);
            }else {
                handler.removeCallbacks(downRunable);
                textView.setText(msg);
            }
        }
    };
    private Runnable upRunable = new Runnable() {
        @Override
        public void run() {
            if(sec<=0)
                sec=0;
            sec++;
            textView.setText(sec + "秒");
            handler.postDelayed(downRunable, 1000);
        }
    };

    /*倒計時*/
    public void downTime(long sec, TextView textView , String msg){
        this.sec = sec;
        this.textView = textView;
        this.msg = msg;
        handler.postDelayed(downRunable , 1000);
    }
    /*正计时*/
    public void upTime(long sec, TextView textView){
        this.sec = sec;
        this.textView = textView;
        handler.postDelayed(downRunable , 1000);
    }
}
