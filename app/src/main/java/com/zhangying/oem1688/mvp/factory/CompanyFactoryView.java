package com.zhangying.oem1688.mvp.factory;

import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.OemcateAreaBean;

import java.util.List;

public interface CompanyFactoryView {
    void showloading();

    void hidenloading();

    void success(List<OemcateAreaBean> bean, int type, List<CompanyFactoryBean.RetvalBean.ProgslistBean> list);
}
