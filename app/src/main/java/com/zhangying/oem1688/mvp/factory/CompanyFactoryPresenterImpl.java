package com.zhangying.oem1688.mvp.factory;

import android.content.Intent;

import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.OemcateAreaBean;
import com.zhangying.oem1688.bean.OemcateAreaBeanChildren;

import java.util.ArrayList;
import java.util.List;

public class CompanyFactoryPresenterImpl implements ComapanyFactoryPresenter, CompanyFactoryFinishListener {
    private CompanyFactoryView companyFactoryView;
    private CompanyFactoryModelImpl companyFactoryModel;
    private int cate_area_type, company_factory_type;
    private int parent_id, parent_position, children_id, child_podition;

    public CompanyFactoryPresenterImpl(CompanyFactoryView companyFactoryView, int company_factory_type, int cate_area_type, int parent_id, int parent_position, int children_id, int child_podition) {
        this.cate_area_type = cate_area_type;
        this.company_factory_type = company_factory_type;
        this.companyFactoryView = companyFactoryView;
        this.parent_id = parent_id;
        this.parent_position = parent_position;
        this.children_id = children_id;
        this.child_podition = child_podition;
        companyFactoryModel = new CompanyFactoryModelImpl();
    }


    @Override
    public void validateCredentials(int catebid, int catsid) {
        //开始操作入口
        companyFactoryView.showloading();
        companyFactoryModel.getdata(company_factory_type,catebid, catsid, this);
    }

    @Override
    public void success(CompanyFactoryBean bean) {
        //处理数据 0代表品类 1代表区域
        List<OemcateAreaBean> listParent = new ArrayList<>();
        if (cate_area_type == 0) {
            for (int i = 0; i < bean.getRetval().getOemcate().size(); i++) {
                CompanyFactoryBean.RetvalBean.OemcateBean oemcateBean = bean.getRetval().getOemcate().get(i);
                OemcateAreaBean oemcateAreaBean = new OemcateAreaBean();
                oemcateAreaBean.setName(oemcateBean.getValue());
                oemcateAreaBean.setId(Integer.parseInt(oemcateBean.getId()));
                if (oemcateAreaBean.getId() == parent_id) {
                    oemcateAreaBean.setaBoolean(true);
                } else {
                    oemcateAreaBean.setaBoolean(false);
                }
                //获取子类的
                List<OemcateAreaBeanChildren> lisrtChildren = new ArrayList<>();
                for (CompanyFactoryBean.RetvalBean.OemcateBean.ChildrenBean child : oemcateBean.getChildren()) {
                    OemcateAreaBeanChildren oemcateAreaBeanchildren = new OemcateAreaBeanChildren();
                    oemcateAreaBeanchildren.setId(Integer.parseInt(child.getId()));
                    oemcateAreaBeanchildren.setName(child.getValue());
                    oemcateAreaBeanchildren.setParent_position(i);
                    oemcateAreaBeanchildren.setParent_id(Integer.parseInt(oemcateBean.getId()));
                    if (Integer.valueOf(child.getId()) == children_id) {
                        oemcateAreaBeanchildren.setSelect(true);
                    } else {
                        oemcateAreaBeanchildren.setSelect(false);
                    }
                    lisrtChildren.add(oemcateAreaBeanchildren);
                }
                oemcateAreaBean.setChildrenList(lisrtChildren);
                listParent.add(oemcateAreaBean);
            }

        } else {
            for (int i = 0; i < bean.getRetval().getOemarea().size(); i++) {
                CompanyFactoryBean.RetvalBean.OemareaBean oemareaBean = bean.getRetval().getOemarea().get(i);
                OemcateAreaBean oemcateAreaBean = new OemcateAreaBean();
                oemcateAreaBean.setName(oemareaBean.getRegionname());
                oemcateAreaBean.setId(oemareaBean.getRegionid());
                if (oemareaBean.getRegionid() == parent_id) {
                    oemcateAreaBean.setaBoolean(true);
                } else {
                    oemcateAreaBean.setaBoolean(false);
                }
                //获取子类的
                List<OemcateAreaBeanChildren> lisrtChildren = new ArrayList<>();
                for (CompanyFactoryBean.nextListBean child : oemareaBean.getNextList()) {
                    OemcateAreaBeanChildren oemcateAreaBeanchildren = new OemcateAreaBeanChildren();
                    oemcateAreaBeanchildren.setId(child.getRegionid());
                    oemcateAreaBeanchildren.setName(child.getRegionname());
                    oemcateAreaBeanchildren.setParent_position(i);
                    oemcateAreaBeanchildren.setParent_id(Integer.parseInt(String.valueOf(oemareaBean.getRegionid())));
                    if (Integer.valueOf(child.getRegionid()) == children_id) {
                        oemcateAreaBeanchildren.setSelect(true);
                    } else {
                        oemcateAreaBeanchildren.setSelect(false);
                    }
                    lisrtChildren.add(oemcateAreaBeanchildren);
                }
                oemcateAreaBean.setChildrenList(lisrtChildren);
                listParent.add(oemcateAreaBean);
            }
        }

        companyFactoryView.success(listParent, parent_position == -1 ? 0 : parent_position, bean.getRetval().getProgslist());
    }

    @Override
    public void hidenloading() {
        companyFactoryView.hidenloading();
    }
}
