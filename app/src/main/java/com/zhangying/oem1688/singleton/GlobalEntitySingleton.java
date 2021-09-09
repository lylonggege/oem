package com.zhangying.oem1688.singleton;

import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.HomeBena;

import java.util.HashSet;

//
public class GlobalEntitySingleton extends Object {

    public void setFactoryDetail(FactoryDetailBean.RetvalBean factoryDetail) { this.factoryDetail = factoryDetail; }
    public FactoryDetailBean.RetvalBean getFactoryDetail() {
        return factoryDetail;
    }
    private FactoryDetailBean.RetvalBean factoryDetail;

    public HomeBena getHomeData() { return homeData; }
    public void setHomeData(HomeBena homeData) { this.homeData = homeData; }
    private HomeBena homeData;

    private static class LazyCookieHoler {
        private static final GlobalEntitySingleton instance = new GlobalEntitySingleton();
    }

    public static final GlobalEntitySingleton getInstance() {
        return LazyCookieHoler.instance;
    }

    public GlobalEntitySingleton() {

    }

}
