package com.zhangying.oem1688.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.CompanyFactoryTabberAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.custom.CompanyFactoryTabberViewgrounp;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryFinishListener;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryFragment;
import com.zhangying.oem1688.mvp.factory.CompanyFactoryModelImpl;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.view.activity.home.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhangying.oem1688.constant.BuildConfig.CATEBID;
import static com.zhangying.oem1688.constant.BuildConfig.CATESID;
import static com.zhangying.oem1688.constant.BuildConfig.COMPANY_FACTORY_TYPE;
import static com.zhangying.oem1688.constant.BuildConfig.DAIGONGPINGLEI;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FactoryFragment extends BaseFragment {

    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.tab_RL)
    RelativeLayout tab_RL;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_LL)
    LinearLayout empty_ll;
    private CompanyFactoryTabberAdpter companyFactoryTabberAdpter;
    private CompanyFactoryModelImpl companyFactoryModel;
    private List<CompanyFactoryBean.RetvalBean.ProgslistBean> progslist;
    private int page;
    private CompanyFactoryTabberViewgrounp titleViewGrounp;
    //分類大ID
    private int catebid;
    //分类小ID
    private int catesid;
    private int company_factory_type;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_factory;
    }

    @Override
    public void initView() {
        company_factory_type = getArguments().getInt(COMPANY_FACTORY_TYPE);
        catebid = getArguments().getInt(CATEBID);
        catesid = getArguments().getInt(CATESID);
        //代工品类
        String name = getArguments().getString(DAIGONGPINGLEI);
        companyFactoryTabberAdpter = new CompanyFactoryTabberAdpter(context);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recycview.addItemDecoration(new SpacesItemDecoration(space, space));
        recycview.setLayoutManager(new GridLayoutManager(context, 2));
        titleViewGrounp = new CompanyFactoryTabberViewgrounp(getActivity(), null);
        titleViewGrounp.setCompany_factory_type(company_factory_type, name, catebid, catesid);
        titleViewGrounp.setCompanyFactoryFragment(new CompanyFactoryFragment() {
            @Override
            public void getList(int type, int parent_id, int children_id) {
                showLoading();
                companyFactoryModel = new CompanyFactoryModelImpl();
                companyFactoryModel.getdata(type, parent_id, children_id, new CompanyFactoryFinishListener() {
                    @Override
                    public void success(CompanyFactoryBean bean) {
                        progslist = bean.getRetval().getProgslist();
                        setEmptyView();
                        companyFactoryTabberAdpter.refresh(progslist.get(0).getGoods());
                        recycview.setAdapter(companyFactoryTabberAdpter);
                    }

                    @Override
                    public void hidenloading() {
                        dissmissLoading();
                    }
                });

            }
        });
        tab_RL.addView(titleViewGrounp);
        //获取数据
        initData(company_factory_type);
        //上拉加载下拉刷新
        initRefresh();

    }

    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                page++;
                if (page < progslist.size()) {
                    companyFactoryTabberAdpter.loadMore(progslist.get(page).getGoods());
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 0;
                companyFactoryTabberAdpter.refresh(progslist.get(0).getGoods());
            }
        });
    }

    private void initData(int company_factory_type) {
        showLoading();
        CompanyFactoryModelImpl companyFactoryModel = new CompanyFactoryModelImpl();
        companyFactoryModel.getdata(company_factory_type, catebid, catesid, new CompanyFactoryFinishListener() {
            @Override
            public void success(CompanyFactoryBean bean) {
                progslist = bean.getRetval().getProgslist();
                setEmptyView();

                companyFactoryTabberAdpter.refresh(progslist.get(0).getGoods());
                recycview.setAdapter(companyFactoryTabberAdpter);
            }

            @Override
            public void hidenloading() {
                dissmissLoading();
            }
        });
    }

    private void setEmptyView() {
        if (progslist.get(0).getGoods().size() > 0) {
            refreshLayout.setVisibility(View.VISIBLE);
            empty_ll.setVisibility(View.GONE);
        } else {
            refreshLayout.setVisibility(View.GONE);
            empty_ll.setVisibility(View.VISIBLE);
            return;
        }
    }


    @OnClick({R.id.imageView2, R.id.empty_LL,R.id.textView})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.imageView2:
                FenLeiRealization fenLeiRealization = new FenLeiRealization(getActivity(), new BaseView() {
                    @Override
                    public void showloading() {
                        showLoading();
                    }

                    @Override
                    public void hidenloading() {
                        dissmissLoading();
                    }

                    @Override
                    public void success(Object o) {
                        showloading();
                    }
                });
                fenLeiRealization.realization();
                break;
            case R.id.empty_LL:
                //获取数据
                initData(company_factory_type);
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(getActivity());
                break;
        }
    }
}

