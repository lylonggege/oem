package com.zhangying.oem1688.mvp.home;


import com.zhangying.oem1688.bean.HomeTabBean;

public interface TabberView {
    void showloading();

    void hidenloading();

    void success(HomeTabBean data);
}
