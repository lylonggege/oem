package com.zhangying.oem1688.view.fragment;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.FragmnetPagerAdapter;
import com.zhangying.oem1688.base.XBaseFragment;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MySlidingTabLayout;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.view.activity.home.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends XBaseFragment implements TabLayout.OnTabSelectedListener, BaseView {
    @BindView(R.id.parent_tab_indictor)
    MySlidingTabLayout parentTabIndictor;
    @BindView(R.id.ViewPagerSlide)
    ViewPager view_pager2;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private com.zhangying.oem1688.view.fragment.home.HomeFragment homeFragment;
    private FragmnetPagerAdapter fragmnetPagerAdapter;
    private BaseValidateCredentials fenLeiRealization;

    public int getCurrentTab(){
        return parentTabIndictor.getCurrentTab();
    }

    public void setHomeCurrentTab(){
        parentTabIndictor.setCurrentTab(0);
    }

    //设置菜单适配器里面的recycleView
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        gethome();
        fenLeiRealization = new FenLeiRealization(getActivity(), this);
    }

    @OnClick({R.id.imageView2, R.id.textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(getActivity());
                break;
        }
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void gethome() {
        RemoteRepository.getInstance()
                .gethome()
                .subscribeWith(new DefaultDisposableSubscriber<HomeBena>() {

                    @Override
                    protected void success(HomeBena data) {

                        HomeBena.RetvalBean retval = data.getRetval();
                        //tab
                        List<HomeBena.RetvalBean.SindustryBean> sindustry = retval.getSindustry();
                        refreshAdapter(sindustry);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void refreshAdapter(List<HomeBena.RetvalBean.SindustryBean> sindustry) {
        homeFragment = com.zhangying.oem1688.view.fragment.home.HomeFragment.newInstance("-999");
        tabList.add("行业");
        fragmentList.add(homeFragment);
        for (HomeBena.RetvalBean.SindustryBean sindustryBean : sindustry) {
            homeFragment = com.zhangying.oem1688.view.fragment.home.HomeFragment.newInstance(sindustryBean.getCate_id());
            fragmentList.add(homeFragment);
            tabList.add(sindustryBean.getCate_name());
        }

        if (fragmentList != null && fragmentList.size() > 0) {
            fragmnetPagerAdapter = new FragmnetPagerAdapter(getFragmentManager(), fragmentList, tabList);
            //view_pager2.setOffscreenPageLimit(fragmentList.size());
            view_pager2.setAdapter(fragmnetPagerAdapter);
            parentTabIndictor.setViewPager(view_pager2);
            parentTabIndictor.setCurrentTab(0);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void showloading() {
        getMessageLoader().isLoading();
    }

    @Override
    public void hidenloading() {
        getMessageLoader().dismiss();
    }

    @Override
    public void success(Object o) {

    }
}