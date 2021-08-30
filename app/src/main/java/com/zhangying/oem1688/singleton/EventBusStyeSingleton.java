package com.zhangying.oem1688.singleton;
import com.zhangying.oem1688.bean.EvenBusMessageBean;

import org.greenrobot.eventbus.EventBus;

public class EventBusStyeSingleton {

    private static class Holer {
        private static final EventBusStyeSingleton instance = new EventBusStyeSingleton();
    }

    public static final EventBusStyeSingleton getInstance() {
        return EventBusStyeSingleton.Holer.instance;
    }

    public EventBusStyeSingleton() {

    }

    //跟新我的界面数据
    public void updateMyfragment() {
        EvenBusMessageBean evenBusMessageBean = new EvenBusMessageBean();
        evenBusMessageBean.setType(3);
        EventBus.getDefault().post(evenBusMessageBean);
    }

}
