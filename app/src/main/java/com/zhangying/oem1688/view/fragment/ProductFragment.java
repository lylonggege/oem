package com.zhangying.oem1688.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeGoodAdpter;
import com.zhangying.oem1688.adpter.MoreProstoreAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.bean.MoreProstoreBeanmvp;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.newfactoryproduct.FactoryProductPersenterImpl;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.util.SpacesItemDecoration;
import com.zhangying.oem1688.view.activity.home.SearchActivity;
import com.zhangying.oem1688.view.activity.home.SearchResultActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of getActivity() fragment.
 */
public class ProductFragment extends BaseFragment implements BaseView {

    @BindView(R.id.company_tv)
    TextView companyTv;
    @BindView(R.id.company_tv_line)
    TextView companyTvLine;
    @BindView(R.id.factory_tv)
    TextView factoryTv;
    @BindView(R.id.factory_tv_line)
    TextView factoryTvLine;
    @BindView(R.id.recycview)
    MyRecycleView recycleView;
    @BindView(R.id.goodsrecycview)
    MyRecycleView goodsrecycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty_LL)
    LinearLayout emptyLL;
    @BindView(R.id.companychildren_tv)
    TextView companychildrenTv;
    @BindView(R.id.factorychildren_tv)
    TextView factorychildrenTv;
    private FactoryProductPersenterImpl factoryProductPersenter;
    private int page = 1;
    //分类
    private String[] option;
    private String[][] mTimeOption1;
    private List<CompanyFactoryBean.RetvalBean.OemcateBean>cateList;
    private ArrayList<String> cateSelected;
    private ArrayList<Integer> cateIndexSelected;

    //地区
    private String[] option_address;
    private String[][] mTimeOption1_address;
    private List<CompanyFactoryBean.RetvalBean.OemareaBean>areaList;
    private ArrayList<String> areaSelected;
    private ArrayList<Integer> areaIndexSelected;

    private MoreProstoreBeanmvp moreProstoreBeanmvp;
    private int type = 1; //0是产品  1是工厂
    private MoreProstoreAdpter moreProstoreAdpter;
    private HomeGoodAdpter home_goodAdpter;
    private List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> getGoods = new ArrayList<>();
    private BaseValidateCredentials fenLeiRealization;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    public void initView() {
        cateSelected = new ArrayList<String>(Arrays.asList("0","0"));
        areaSelected = new ArrayList<String>(Arrays.asList("0","0"));
        cateIndexSelected = new ArrayList<Integer>(Arrays.asList(0,0));
        areaIndexSelected = new ArrayList<Integer>(Arrays.asList(0,0));

        type = getArguments().getInt("TYPE");
        setGoodsfactoryState(type);

        fenLeiRealization = new FenLeiRealization(getActivity(), this);
        moreProstoreBeanmvp = new MoreProstoreBeanmvp();
        factoryProductPersenter = new FactoryProductPersenterImpl(this);
        moreProstoreAdpter = new MoreProstoreAdpter(getActivity());
        home_goodAdpter = new HomeGoodAdpter(getActivity());
        WidgetUtils.initRecyclerView(recycleView);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        goodsrecycview.addItemDecoration(new SpacesItemDecoration(space, space));
        goodsrecycview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycleView.setAdapter(moreProstoreAdpter);
        goodsrecycview.setAdapter(home_goodAdpter);

        initdata();
        moredata();
        initRefresh();
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.imageView2, R.id.textView, R.id.company_rl,
            R.id.factory_rl, R.id.companychildren_rl,
            R.id.factoeychildren_rl, R.id.imageView5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(getActivity());
                break;
            case R.id.company_rl:
                page = 1;
                type = 0;
                companyTv.setSelected(true);
                companyTvLine.setSelected(true);
                factoryTv.setSelected(false);
                factoryTvLine.setSelected(false);
                moredata();
                break;
            case R.id.factory_rl:
                page = 1;
                type = 1;
                companyTv.setSelected(false);
                companyTvLine.setSelected(false);
                factoryTv.setSelected(true);
                factoryTvLine.setSelected(true);
                moredata();
                break;
            case R.id.companychildren_rl:
                if (option == null || option.length == 0) {
                    return;
                }
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), (v, options1, options2, options3) -> {
                    cateIndexSelected.set(0,options1);
                    cateIndexSelected.set(1,options2);

                    cateSelected.set(0,cateList.get(options1).getId() + "");
                    String minText = mTimeOption1[options1][options2];
                    String maxText = option[options1];
                    if (options2 == 0){
                        cateSelected.set(1,"0");
                        companychildrenTv.setText(maxText);
                    }else {
                        String minCate = cateList.get(options1).getChildren().get(options2 - 1).getId() + "";
                        cateSelected.set(1,minCate);
                        companychildrenTv.setText(minText);
                    }

                    moredata();
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(cateIndexSelected.get(0), cateIndexSelected.get(1))
                        .build();
                pvOptions.setPicker(option, mTimeOption1);
                pvOptions.show();
                break;
            case R.id.factoeychildren_rl:
                if (option_address == null || option_address.length == 0) {
                    return;
                }
                OptionsPickerView pvOptions_address = new OptionsPickerBuilder(getActivity(), (v, options1, options2, options3) -> {
                    areaIndexSelected.set(0,options1);
                    areaIndexSelected.set(1,options2);

                    areaSelected.set(0, areaList.get(options1).getRegionid() + "");
                    String minArea = areaList.get(options1).getNextList().get(options2).getRegionid() + "";
                    areaSelected.set(0, minArea);
                    factorychildrenTv.setText(option_address[options1]);

                    if ("0".equals(minArea)){
                        factorychildrenTv.setText(option_address[options1]);
                    }else {
                        factorychildrenTv.setText(mTimeOption1_address[options1][options2]);
                    }
                    moredata();
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(areaIndexSelected.get(0), areaIndexSelected.get(1))
                        .build();
                pvOptions_address.setPicker(option_address, mTimeOption1_address);
                pvOptions_address.show();
                break;
            case R.id.imageView5:
                moredata();
                break;
        }
    }


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
        MoreProstoreBean moreProstoreBean = (MoreProstoreBean) o;
        MoreProstoreBean.RetvalBean retval = moreProstoreBean.getRetval();
        List<MoreProstoreBean.RetvalBean.DataBean> data = retval.getData();
        if (data.size() > 0) {
            refreshLayout.setVisibility(View.VISIBLE);
            emptyLL.setVisibility(View.GONE);
        } else {
            refreshLayout.setVisibility(View.GONE);
            emptyLL.setVisibility(View.VISIBLE);
            return;
        }

        if (type == 1) {
            goodsrecycview.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            if (page == 1) {
                moreProstoreAdpter.refresh(retval.getData());
            } else {
                moreProstoreAdpter.loadMore(retval.getData());
            }

        } else {
            goodsrecycview.setVisibility(View.VISIBLE);
            recycleView.setVisibility(View.GONE);

            getGoods.clear();
            for (int i = 0; i < data.size(); i++) {
                MoreProstoreBean.RetvalBean.DataBean dataBean = data.get(i);
                HomeBena.RetvalBean.SgoodsListBean.GoodsBean goodsBean = new HomeBena.RetvalBean.SgoodsListBean.GoodsBean();
                goodsBean.setDefault_image(dataBean.getDefault_image());
                goodsBean.setGoods_id(dataBean.getGoods_id());
                goodsBean.setGoods_name(dataBean.getGoods_name());
                ArrayList<HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean> goodsTagsBeans = new ArrayList<>();
                List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean> goods_tags = dataBean.getGoods_tags();
                if (goods_tags != null && goods_tags.size() > 0) {
                    for (int i1 = 0; i1 < goods_tags.size(); i1++) {
                        HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean goodsTagsBean1 = goods_tags.get(i1);
                        HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean tagsBean = new HomeBena.RetvalBean.SgoodsListBean.GoodsBean.GoodsTagsBean();
                        tagsBean.setStag(goodsTagsBean1.getStag());
                        tagsBean.setSclass(goodsTagsBean1.getSclass());
                        goodsTagsBeans.add(tagsBean);
                    }
                }
                goodsBean.setGoods_tags(goodsTagsBeans);
                getGoods.add(goodsBean);
            }
            if (page == 1) {
                home_goodAdpter.refresh(getGoods);
            } else {
                home_goodAdpter.loadMore(getGoods);
            }
        }
    }

    private void initdata() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("catebid", 0);
        map.put("catesid", 0);
        RemoteRepository.getInstance()
                .getstorelists(map)
                .subscribeWith(new DefaultDisposableSubscriber<CompanyFactoryBean>() {
                    @Override
                    protected void success(CompanyFactoryBean data) {
                        dissmissLoading();
                        CompanyFactoryBean.RetvalBean retval = data.getRetval();
                        cateList = retval.getOemcate();

                        option = new String[retval.getOemcate().size()];
                        mTimeOption1 = new String[retval.getOemcate().size()][];
                        int childSize = 0;
                        List<CompanyFactoryBean.RetvalBean.OemcateBean.ChildrenBean> cateChildList = null;
                        for (int i = 0; i < retval.getOemcate().size(); i++) {
                            CompanyFactoryBean.RetvalBean.OemcateBean oemcateBean = retval.getOemcate().get(i);
                            option[i] = oemcateBean.getValue();

                            cateChildList = retval.getOemcate().get(i).getChildren();
                            childSize = cateChildList.size() + 1;
                            String[] time = new String[childSize];
                            time[0] = option[i] + "全部";
                            for (int i1 = 1; i1 <= cateChildList.size(); i1++) {
                                time[i1] = cateChildList.get(i1 - 1).getValue();
                            }
                            mTimeOption1[i] = time;
                        }

                        areaList = retval.getOemarea();
                        option_address = new String[retval.getOemarea().size()];
                        mTimeOption1_address = new String[retval.getOemarea().size()][];

                        for (int i = 0; i < retval.getOemarea().size(); i++) {
                            CompanyFactoryBean.RetvalBean.OemareaBean oemareaBean = retval.getOemarea().get(i);
                            option_address[i] = oemareaBean.getRegionname();

                            String[] time = new String[retval.getOemarea().get(i).getNextList().size()];
                            List<CompanyFactoryBean.nextListBean> nextList = retval.getOemarea().get(i).getNextList();
                            for (int i1 = 0; i1 < nextList.size(); i1++) {
                                time[i1] = nextList.get(i1).getRegionname();
                            }
                            mTimeOption1_address[i] = time;
                        }
                    }
                });
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                page++;
                moredata();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                moredata();

            }
        });
    }

    private void moredata() {
        moreProstoreBeanmvp.setLy("app");
        moreProstoreBeanmvp.setKw("");
        moreProstoreBeanmvp.setPage(page + "");
        moreProstoreBeanmvp.setCatebid(cateSelected.get(0));
        moreProstoreBeanmvp.setCatesid(cateSelected.get(1));
        moreProstoreBeanmvp.setAreabid(areaSelected.get(0));
        moreProstoreBeanmvp.setAreasid(areaSelected.get(1));
        moreProstoreBeanmvp.setItype(String.valueOf(type));
        factoryProductPersenter.saveData(moreProstoreBeanmvp);
        factoryProductPersenter.validateCredentials();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void evenBusBean(@NotNull EvenBusBean bean) {
        try {
            type = bean.getType();
            if (bean.getId() != null) {
                setGoodsfactoryState(type);
                companychildrenTv.setText(bean.getName());
                String[] ids = bean.getId().split("_");
                cateSelected.set(0,ids[0]);
                if (ids.length == 2) {
                    cateSelected.set(1,ids[1]);
                }
                moredata();
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setGoodsfactoryState(int type) {

        if (type == 1) {
            companyTv.setSelected(false);
            companyTvLine.setSelected(false);
            factoryTv.setSelected(true);
            factoryTvLine.setSelected(true);
        } else {
            companyTv.setSelected(true);
            companyTvLine.setSelected(true);
            factoryTv.setSelected(false);
            factoryTvLine.setSelected(false);
        }
    }
}