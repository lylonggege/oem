package com.zhangying.oem1688.mvp.factory;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lxj.xpopup.core.BottomPopupView;
import com.xuexiang.xui.widget.progress.loading.MiniLoadingView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.CompanyFactoryAdpter;
import com.zhangying.oem1688.adpter.CompanyFactoryChildrenAdpter;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.OemcateAreaBean;
import com.zhangying.oem1688.bean.OemcateAreaBeanChildren;
import com.zhangying.oem1688.custom.MyRecycleView;

import java.util.List;

public class CompanyFactoryPopup extends BottomPopupView implements CompanyFactoryView {

    private MyRecycleView MyRecycleView_left;
    private MyRecycleView MyRecycleView_right;
    private ComapanyFactoryPresenter comapanyFactoryPresenter;
    private MiniLoadingView miniLoadingView;
    private Context context;
    private TextView companychildren_tv, factorychildren_tv;

    public void setCompanyFactoryClickResult(CompanyFactoryClickResult companyFactoryClickResult) {
        this.companyFactoryClickResult = companyFactoryClickResult;
    }

    private CompanyFactoryClickResult companyFactoryClickResult;

    //0 代表点击品类  1 代表区域

    //区分产品还是工厂 功能 区分接口使用
    private int company_factory_type;
    //区分点击的是分类还是地区  区分返回的数据使用
    private int cate_area_type;
    //记录分类的父类id
    private int cate_parent_id;
    //记录分类子类的id
    private int cate_childen_id;
    //记录分类选中父类的位置
    private int cate_parent_position;
    //记录分类选中子类的位置
    private int cate_childen_position;
    //记录地区的父类id
    private int area_parent_id;
    //记录地区子类的id
    private int area_childen_id;
    //记录地区选中父类的位置
    private int area_parent_position;
    //记录地区选中子类的位置
    private int area_childen_position;

    public void setType(int company_factory_type, int cate_area_type, int parent_id, int parent_position, int children_id, int child_podition) {
        this.cate_area_type = cate_area_type;
        comapanyFactoryPresenter = new CompanyFactoryPresenterImpl(this, company_factory_type, cate_area_type, parent_id, parent_position, children_id, child_podition);
        comapanyFactoryPresenter.validateCredentials(parent_id, children_id);
    }

    private CompanyFactoryAdpter companyFactoryAdpter;
    private CompanyFactoryChildrenAdpter companyFactoryChildrenAdpter;


    public CompanyFactoryPopup(@NonNull Context context) {
        super(context);
        this.context = context;
        companyFactoryAdpter = new CompanyFactoryAdpter();
        companyFactoryChildrenAdpter = new CompanyFactoryChildrenAdpter();
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        MyRecycleView_left = findViewById(R.id.MyRecycleView_left);
        MyRecycleView_right = findViewById(R.id.MyRecycleView_right);
        companychildren_tv = findViewById(R.id.companychildren_tv);
        factorychildren_tv = findViewById(R.id.factorychildren_tv);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.companyfactorypopup;
    }

    @Override
    public void showloading() {
        if (miniLoadingView != null) {
            miniLoadingView = new MiniLoadingView(context);
            miniLoadingView.start();
        }
    }

    @Override
    public void hidenloading() {
        if (miniLoadingView != null) {
            miniLoadingView.stop();
        }

    }

    @Override
    public void success(List<OemcateAreaBean> bean, int children_id, List<CompanyFactoryBean.RetvalBean.ProgslistBean> list) {

        MyRecycleView_left.setLayoutManager(new LinearLayoutManager(context));
        companyFactoryAdpter.refresh(bean);
        MyRecycleView_left.setAdapter(companyFactoryAdpter);


        List<OemcateAreaBeanChildren> childrenList = bean.get(children_id).getChildrenList();
        MyRecycleView_right.setLayoutManager(new LinearLayoutManager(context));
        companyFactoryChildrenAdpter.refresh(childrenList);
        MyRecycleView_right.setAdapter(companyFactoryChildrenAdpter);

        //父类
        companyFactoryAdpter.setComapanyFactoryResult(new ComapanyFactoryAdpter() {
            @Override
            public void Success(OemcateAreaBean oemcateAreaBean1, OemcateAreaBeanChildren oemcateAreaBeanChildren, int position) {

                for (int i = 0; i < bean.size(); i++) {
                    OemcateAreaBean oemcateAreaBean2 = bean.get(i);
                    if (i == position) {
                        oemcateAreaBean2.setaBoolean(true);
                    } else {
                        oemcateAreaBean2.setaBoolean(false);
                    }
                }
                companyFactoryAdpter.notifyDataSetChanged();
                companyFactoryChildrenAdpter.refresh(bean.get(position).getChildrenList());
            }
        });

        //子类
        companyFactoryChildrenAdpter.setComapanyFactoryAdpter(new ComapanyFactoryAdpter() {
            @Override
            public void Success(OemcateAreaBean oemcateAreaBean, OemcateAreaBeanChildren oemcateAreaBeanChildren, int position) {
                check(oemcateAreaBean, oemcateAreaBeanChildren, position, list);
            }
        });
    }

    private void check(OemcateAreaBean oemcateAreaBean1, OemcateAreaBeanChildren oemcateAreaBeanChildren, int position,List<CompanyFactoryBean.RetvalBean.ProgslistBean> list) {
        //是分类还是地区
        if (cate_area_type == 0) {
            cate_parent_id = oemcateAreaBeanChildren.getParent_id();
            cate_parent_position = oemcateAreaBeanChildren.getParent_position();
            cate_childen_id = oemcateAreaBeanChildren.getId();
            cate_childen_position = position;
            String name = oemcateAreaBeanChildren.getName();
            companyFactoryClickResult.getResult(cate_parent_id, cate_parent_position, cate_childen_id, cate_childen_position, area_parent_id, area_parent_position, area_childen_id, area_childen_position, name,list);
            companychildren_tv.setText(name);
        } else {
            area_parent_id = oemcateAreaBeanChildren.getParent_id();
            area_parent_position = oemcateAreaBeanChildren.getParent_position();
            area_childen_id = oemcateAreaBeanChildren.getId();
            area_childen_position = position;
            String name = oemcateAreaBeanChildren.getName();
            companyFactoryClickResult.getResult(cate_parent_id, cate_parent_position, cate_childen_id, cate_childen_position, area_parent_id, area_parent_position, area_childen_id, area_childen_position, name,list);
            factorychildren_tv.setText(name);
        }
    }
}
