package com.zhangying.oem1688.mvp.home;

import com.zhangying.oem1688.bean.HomeTabBean;

public interface TabberFinishListener {
    void success(HomeTabBean data);
    void hidenloading();
}
