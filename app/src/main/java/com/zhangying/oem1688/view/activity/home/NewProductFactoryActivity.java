package com.zhangying.oem1688.view.activity.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeGoodAdpter;
import com.zhangying.oem1688.adpter.MoreProstoreAdpter;
import com.zhangying.oem1688.base.BaseActivity;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewProductFactoryActivity extends BaseActivity implements BaseView {
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
    @BindView(R.id.title_TV)
    TextView title_TV;

    private FactoryProductPersenterImpl factoryProductPersenter;
    private int page = 1;
    private String[] option;
    private String[][] mTimeOption1;
    private String[] option_id;
    private String[][] mTimeOption1_id;
    private String moptions1;
    private String moptions2;

    //地区
    private String[] option_address;
    private String[][] mTimeOption1_address;
    private String[] option_id_address;
    private String[][] mTimeOption1_id_address;
    private String moptions1_address;
    private String moptions2_address;

    private MoreProstoreBeanmvp moreProstoreBeanmvp;
    private int type = 1; //0是产品  1是工厂
    private MoreProstoreAdpter moreProstoreAdpter;
    private HomeGoodAdpter home_goodAdpter;
    private List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> getGoods = new ArrayList<>();
    private BaseValidateCredentials fenLeiRealization;
    private String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_product_factory;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("TYPE", 0);
        name = getIntent().getStringExtra("NAME");
        companychildrenTv.setText(name);
        String id = getIntent().getStringExtra("ID");

        String[] ids = id.split("_");
        moptions1 = ids[0];
        if (ids.length == 2) {
            moptions2 = ids[1];
        }

        setGoodsfactoryState(type);

        fenLeiRealization = new FenLeiRealization(NewProductFactoryActivity.this, this);
        moreProstoreBeanmvp = new MoreProstoreBeanmvp();
        factoryProductPersenter = new FactoryProductPersenterImpl(this);
        moreProstoreAdpter = new MoreProstoreAdpter(NewProductFactoryActivity.this);
        home_goodAdpter = new HomeGoodAdpter(NewProductFactoryActivity.this);
        WidgetUtils.initRecyclerView(recycleView);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        goodsrecycview.addItemDecoration(new SpacesItemDecoration(space, space));
        goodsrecycview.setLayoutManager(new GridLayoutManager(NewProductFactoryActivity.this, 2));
        recycleView.setAdapter(moreProstoreAdpter);
        goodsrecycview.setAdapter(home_goodAdpter);

        initdata();
        moredata();
        initRefresh();
        EventBus.getDefault().register(this);
    }


    @OnClick({R.id.imageView2, R.id.textView, R.id.company_rl,
            R.id.factory_rl, R.id.companychildren_rl,
            R.id.factoeychildren_rl, R.id.imageView5, R.id.bacK_RL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.imageView2:
                fenLeiRealization.validateCredentials();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(NewProductFactoryActivity.this);
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
                OptionsPickerView pvOptions = new OptionsPickerBuilder(NewProductFactoryActivity.this, (v, options1, options2, options3) -> {
                    moptions1 = option_id[options1];
                    String[] strings = mTimeOption1_id[options1];
                    moptions2 = strings[options2];
                    companychildrenTv.setText(option[options1]);
                    moredata();
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(0, 0)
                        .build();
                pvOptions.setPicker(option, mTimeOption1);
                pvOptions.show();
                break;
            case R.id.factoeychildren_rl:
                if (option_address == null || option_address.length == 0) {
                    return;
                }
                OptionsPickerView pvOptions_address = new OptionsPickerBuilder(NewProductFactoryActivity.this, (v, options1, options2, options3) -> {
                    moptions1_address = option_id_address[options1];
                    String[] strings = mTimeOption1_id_address[options1];
                    moptions2_address = strings[options2];
                    factorychildrenTv.setText(option_address[options1]);
                    moredata();
                    return false;
                })
                        .setTitleText("")
                        .isRestoreItem(true)
                        .setSelectOptions(0, 0)
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
                        title_TV.setText(name + "OEM,ODM贴牌工厂-代工帮");
                        //title_TV.setTypeface(Typeface.DEFAULT_BOLD);
                        option = new String[retval.getOemcate().size()];
                        mTimeOption1 = new String[retval.getOemcate().size()][];
                        option_id = new String[retval.getOemcate().size()];
                        mTimeOption1_id = new String[retval.getOemcate().size()][];
                        for (int i = 0; i < retval.getOemcate().size(); i++) {
                            CompanyFactoryBean.RetvalBean.OemcateBean oemcateBean = retval.getOemcate().get(i);
                            option[i] = oemcateBean.getValue();
                            option_id[i] = oemcateBean.getId();
                            String[] time = new String[retval.getOemcate().get(i).getChildren().size()];
                            String[] time_id = new String[retval.getOemcate().get(i).getChildren().size()];
                            List<CompanyFactoryBean.RetvalBean.OemcateBean.ChildrenBean> children = retval.getOemcate().get(i).getChildren();
                            for (int i1 = 0; i1 < children.size(); i1++) {
                                time[i1] = children.get(i1).getValue();
                                time_id[i1] = children.get(i1).getId();
                            }
                            mTimeOption1[i] = time;
                            mTimeOption1_id[i] = time_id;
                        }

                        option_address = new String[retval.getOemarea().size()];
                        mTimeOption1_address = new String[retval.getOemarea().size()][];
                        option_id_address = new String[retval.getOemarea().size()];
                        mTimeOption1_id_address = new String[retval.getOemarea().size()][];
                        for (int i = 0; i < retval.getOemarea().size(); i++) {
                            CompanyFactoryBean.RetvalBean.OemareaBean oemareaBean = retval.getOemarea().get(i);
                            option_address[i] = oemareaBean.getRegionname();
                            option_id_address[i] = oemareaBean.getRegionid() + "";
                            String[] time = new String[retval.getOemarea().get(i).getNextList().size()];
                            String[] time_id = new String[retval.getOemarea().get(i).getNextList().size()];
                            List<CompanyFactoryBean.nextListBean> nextList = retval.getOemarea().get(i).getNextList();
                            for (int i1 = 0; i1 < nextList.size(); i1++) {
                                time[i1] = nextList.get(i1).getRegionname();
                                time_id[i1] = nextList.get(i1).getRegionid() + "";
                            }
                            mTimeOption1_address[i] = time;
                            mTimeOption1_id_address[i] = time_id;
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
        moreProstoreBeanmvp.setCatebid(moptions1);
        moreProstoreBeanmvp.setCatesid(moptions2);
        moreProstoreBeanmvp.setAreabid(moptions1_address);
        moreProstoreBeanmvp.setAreasid(moptions2_address);
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
                moptions1 = ids[0];
                if (ids.length == 2) {
                    moptions2 = ids[1];
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static void simpleActivity(Context context, String id, int type, String name, String productyname) {
        Intent intent = new Intent(context, NewProductFactoryActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("TYPE", type);
        intent.putExtra("NAME", name);
        intent.putExtra("PRODUCTYNAME", productyname);
        context.startActivity(intent);
    }
}