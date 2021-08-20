package com.zhangying.oem1688.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryClickResult;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryFragment;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryPopup;

import java.util.List;


public class CompanyFactoryTabberViewgrounp extends RelativeLayout {

    private View inflate;
    private TextView company_tv, company_tv_line, factory_text, factory_line, companychildren_tv, factorychildren_tv;
    private RelativeLayout cate_rl, area_rl, company_rl, factory_rl;
    private Context context;
    //记录分类的父类id
    private int cate_parent_id = -1;
    //记录分类子类的id
    private int cate_children_id = -1;
    //记录分类选中父类的位置
    private int cate_parent_position = -1;
    //记录分类选中子类的位置
    private int cate_childen_position = -1;
    //记录地区的父类id
    private int area_parent_id = -1;
    //记录地区子类的id
    private int area_childen_id = -1;
    //记录地区选中父类的位置
    private int area_parent_position = -1;
    //记录地区选中子类的位置
    private int area_children_position = -1;

    private int mcatebid;
    private int mcatesid;


    public void setCompany_factory_type(int company_factory_type, String name, int catebid, int catesid) {
        this.company_factory_type = company_factory_type;
        this.mname = name;
        this.mcatebid = catebid;
        this.mcatesid = catesid;
    }

    //区分产品还是工厂
    private int company_factory_type;
    private String mname;


    public void setCompanyFactoryFragment(CompanyFactoryFragment companyFactoryFragment) {
        this.companyFactoryFragment = companyFactoryFragment;
        //初始化
        init();
    }

    private CompanyFactoryFragment companyFactoryFragment;

    public CompanyFactoryTabberViewgrounp(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate = LayoutInflater.from(context).inflate(R.layout.company_factory_tab_viewgrounp, this);
    }

    public CompanyFactoryTabberViewgrounp(Context context) {
        super(context);
    }

    private void init() {
        company_tv = inflate.findViewById(R.id.company_tv);
        company_tv_line = inflate.findViewById(R.id.company_tv_line);
        factory_text = inflate.findViewById(R.id.factory_tv);
        factory_line = inflate.findViewById(R.id.factory_tv_line);
        company_rl = inflate.findViewById(R.id.company_rl);
        factory_rl = inflate.findViewById(R.id.factory_rl);
        cate_rl = inflate.findViewById(R.id.companychildren_rl);
        area_rl = inflate.findViewById(R.id.factoeychildren_rl);
        companychildren_tv = inflate.findViewById(R.id.companychildren_tv);
        factorychildren_tv = inflate.findViewById(R.id.factorychildren_tv);


        //默认选中
        defaultCompanyAndProduct();


        //初始化数据
        savetextviewcolor(company_factory_type);
        /**
         * 点击产品  parentid为一级分类id  childrenid 二级分类id
         */
        company_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                company_factory_type = 7;
                companychildren_tv.setText(mname);
                savetextviewcolor(company_factory_type);
                companyFactoryFragment.getList(company_factory_type, mcatebid, mcatesid);
            }
        });
        //点击工厂
        factory_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                company_factory_type = 6;
                companychildren_tv.setText(mname);
                savetextviewcolor(company_factory_type);
                companyFactoryFragment.getList(company_factory_type, mcatebid, mcatesid);
            }
        });
        //点击分类
        cate_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyFactoryPopup companyFactoryPopup = new CompanyFactoryPopup(context);
                companyFactoryPopup.setType(company_factory_type, 0, cate_parent_id, cate_parent_position, cate_children_id, cate_childen_position);
                BasePopupView show = new XPopup.Builder(getContext())
                        .enableDrag(true)
                        .asCustom(companyFactoryPopup)
                        .show();
                companyFactoryPopup.setCompanyFactoryClickResult(new CompanyFactoryClickResult() {
                    @Override
                    public void getResult(int cate_parent_id_, int cate_parent_position_, int cate_children_id_, int cate_children_position_,
                                          int area_parent_id_, int area_parent_position_, int area_children_id_, int area_children_position_,
                                          String name, List<CompanyFactoryBean.RetvalBean.ProgslistBean> list) {
                        companychildren_tv.setText(name);
                        mname = name;
                        cate_parent_id = cate_parent_id_;
                        cate_parent_position = cate_parent_position_;
                        cate_children_id = cate_children_id_;
                        cate_childen_position = cate_children_position_;
                        mcatebid = cate_parent_id;
                        mcatesid = cate_children_id;
                        companyFactoryFragment.getList(company_factory_type, cate_parent_id, cate_children_id);
                        show.dismiss();
                    }
                });
            }
        });
        //点击区域
        area_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyFactoryPopup companyFactoryPopup = new CompanyFactoryPopup(context);
                companyFactoryPopup.setType(company_factory_type, 1, area_parent_id, area_parent_position, area_childen_id, area_children_position);
                BasePopupView show = new XPopup.Builder(getContext())
                        .enableDrag(true)
                        .asCustom(companyFactoryPopup)
                        .show();
                companyFactoryPopup.setCompanyFactoryClickResult(new CompanyFactoryClickResult() {
                    @Override
                    public void getResult(int cate_parent_id_, int cate_parent_position_, int cate_children_id_, int cate_children_position_,
                                          int area_parent_id_, int area_parent_position_, int area_children_id_, int area_children_position_,
                                          String name, List<CompanyFactoryBean.RetvalBean.ProgslistBean> list) {
                        factorychildren_tv.setText(name);
                        area_parent_id = area_parent_id_;
                        area_parent_position = area_parent_position_;
                        area_childen_id = area_children_id_;
                        area_children_position = area_children_position_;
                        companyFactoryFragment.getList(company_factory_type, area_parent_id, area_childen_id);
                        show.dismiss();
                    }
                });
            }
        });
        companychildren_tv.setText(mname);

    }

    private void defaultCompanyAndProduct() {
        savetextviewcolor(company_factory_type);
    }

    private void savetextviewcolor(int type) {

        if (type == 7) {
            company_tv.setSelected(true);
            company_tv_line.setSelected(true);
            factory_text.setSelected(false);
            factory_line.setSelected(false);

        } else if (type == 6) {
            company_tv.setSelected(false);
            company_tv_line.setSelected(false);
            factory_text.setSelected(true);
            factory_line.setSelected(true);

        }
        factorychildren_tv.setText("所属区域");
        cate_children_id = -1;
        cate_childen_position = -1;
        cate_parent_id = -1;
        cate_parent_position = -1;
        area_childen_id = -1;
        area_children_position = -1;
        area_parent_id = -1;
        area_parent_position = -1;
    }
}