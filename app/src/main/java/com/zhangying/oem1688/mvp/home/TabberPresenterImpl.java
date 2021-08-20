package com.zhangying.oem1688.mvp.home;

import android.util.Log;

import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.HomeTabBean;

public class TabberPresenterImpl implements TabberPresenter, TabberFinishListener {

    //回调状态
    private TabberView iTabView;
    private TabberModl tabberModl;

    public TabberPresenterImpl(TabberView iTabView) {
        this.iTabView = iTabView;
        this.tabberModl = new TabberModel();
    }

    //开始记载数据
    @Override
    public void validateCredentials() {
        //开始
        Log.e("开始加载数据", "validateCredentials: ");
        iTabView.showloading();
        tabberModl.showtabber(this);
    }

    @Override
    public void success(HomeTabBean data) {
        iTabView.success(data);
    }

    @Override
    public void hidenloading() {
       iTabView.hidenloading();
    }
}
