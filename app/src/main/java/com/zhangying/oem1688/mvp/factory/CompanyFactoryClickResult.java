package com.zhangying.oem1688.mvp.factory;

import com.zhangying.oem1688.bean.CompanyFactoryBean;

import java.util.List;

public interface CompanyFactoryClickResult {
    void getResult(int cate_parent_id, int cate_parent_position, int cate_children_id, int cate_children_position,
                   int area_parent_id, int area_parent_position, int area_children_id, int area_children_position,
                   String name, List<CompanyFactoryBean.RetvalBean.ProgslistBean> list);
}
