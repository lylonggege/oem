package com.zhangying.oem1688.mvp.factory;

import com.zhangying.oem1688.bean.CompanyFactoryBean;

public interface CompanyFactoryFinishListener {
    void success(CompanyFactoryBean bean);
    void hidenloading();
}
