package com.zhangying.oem1688.view.fragment.home;

import android.util.Log;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.widget.dialog.LoadingDialog;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.HomeGoodAdpter;
import com.zhangying.oem1688.adpter.SitetopinfoLeftAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.FactoryGCateBean;
import com.zhangying.oem1688.bean.FactoryGoodsBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseInterfacePosition;
import com.zhangying.oem1688.onterface.ICallBcak;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

public class FactoryDetailClassFragment extends BaseFragment {
    private HomeGoodAdpter home_goodAdpter;
    private List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> getGoods = new ArrayList<>();
    private  SitetopinfoLeftAdpter sitetopinfoLeftAdpter;
    private static String mcid;
    private static String cateId;

    @BindView(R.id.recycview_left)
    MyRecycleView recycleView_left;
    @BindView(R.id.recycleView_right)
    MyRecycleView recycleView_right;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private LoadingDialog loading;

    @Override
    protected int getLayoutId() {
        return  R.layout.sitetopinfopoopu;
    }

    @Override
    public void initView() {
        recycleView_left.setLayoutManager(new LinearLayoutManager(getActivity()));
        LoadingDialog loading = new LoadingDialog(getActivity());
        loading.show();

        mcid = getArguments().getString("mcid");
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("cid", mcid);
        RemoteRepository.getInstance()
                .get_store_gcate(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryGCateBean>() {

                    @Override
                    protected void success(FactoryGCateBean data) {
                        setDataView(data);
                        loading.dismiss();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        loading.dismiss();
                    }
                });
    }

    //初始化右侧产品分类视图
    private void initRightView(){
        if (page == 1){
            loading = new LoadingDialog(getActivity());
            loading.show();
        }

        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", mcid);
        HashMapSingleton.getInstance().put("cate_id", cateId);
        HashMapSingleton.getInstance().put("page", page);
        RemoteRepository.getInstance()
                .get_store_goods(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<FactoryGoodsBean>() {

                    @Override
                    protected void success(FactoryGoodsBean data) {
                        if (page == 1){loading.dismiss();}
                        List<FactoryGoodsBean.RetvalBean.GoodsBean> goodsList = data.getRetval().getGoodsList();
                        if (page == 1) {getGoods.clear();}
                        for (int i = 0; i < goodsList.size(); i++) {
                            FactoryGoodsBean.RetvalBean.GoodsBean glistBean = goodsList.get(i);
                            HomeBena.RetvalBean.SgoodsListBean.GoodsBean goodsBean = new HomeBena.RetvalBean.SgoodsListBean.GoodsBean();
                            goodsBean.setDefault_image(glistBean.getGpic());
                            goodsBean.setGoods_id(glistBean.getGid());
                            goodsBean.setGoods_name(glistBean.getGname());
                            getGoods.add(goodsBean);
                        }
                        if (page == 1) {
                            home_goodAdpter.refresh(getGoods);
                        } else {
                            if (goodsList.size() > 0){
                                home_goodAdpter.loadMore(getGoods);
                            }else {
                                refreshLayout.setNoMoreData(true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (page == 1){loading.dismiss();}
                    }
                });
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                page++;
                initRightView();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                initRightView();
            }
        });
    }

    private void  setDataView(FactoryGCateBean data){
        List<FactoryGCateBean.RetvalBean> gcates = new ArrayList<FactoryGCateBean.RetvalBean>();

        FactoryGCateBean.RetvalBean firstCate = new FactoryGCateBean.RetvalBean();
        firstCate.setId("");
        firstCate.setIsboolean(true);
        firstCate.setValue("全部");
        gcates.add(firstCate);
        gcates.addAll(data.getRetval());

        //上下加载刷新
        initRefresh();

        //左侧分类
        sitetopinfoLeftAdpter=new SitetopinfoLeftAdpter();
        sitetopinfoLeftAdpter.refresh(gcates);
        recycleView_left.setAdapter(sitetopinfoLeftAdpter);

        //右侧产品
        home_goodAdpter = new HomeGoodAdpter(getActivity());
        int screenWidth = ScreenTools.instance(context).getScreenWidth();
        home_goodAdpter.setParentWidth(screenWidth - recycleView_left.getWidth());
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        recycleView_right.addItemDecoration(new SpacesItemDecoration(space, space));
        recycleView_right.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycleView_right.setAdapter(home_goodAdpter);

        //初始化右侧产品分类视图
        cateId = "";
        initRightView();

        sitetopinfoLeftAdpter.setBaseInterfacePosition(new BaseInterfacePosition() {
            @Override
            public void getPosition(int position, Boolean isboolean, View view) {
                Log.e("位置", "getPosition: "+position );
                for (int i = 0; i < gcates.size(); i++) {
                    FactoryGCateBean.RetvalBean gcatesBean1 = gcates.get(i);
                    if (position==i){
                        gcatesBean1.setIsboolean(true);
                        cateId = gcatesBean1.getId();
                        page = 1;
                        initRightView();
                    }else {
                        gcatesBean1.setIsboolean(false);
                    }
                }
                sitetopinfoLeftAdpter.notifyDataSetChanged();
            }
        });

        home_goodAdpter.setiCallBcak(new ICallBcak() {
            @Override
            public void success() {

            }
        });
    }
}
