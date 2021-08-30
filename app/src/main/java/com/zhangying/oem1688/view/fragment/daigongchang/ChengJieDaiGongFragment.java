package com.zhangying.oem1688.view.fragment.daigongchang;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.ChengJieDaiGongAdpter;
import com.zhangying.oem1688.adpter.NewsOemMoreAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.bean.MoreScinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;

import butterknife.BindView;

public class ChengJieDaiGongFragment extends BaseFragment {
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private ChengJieDaiGongAdpter chengJieDaiGongAdpter;

    public ChengJieDaiGongFragment(String id) {
        this.id = id;
    }
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cheng_jie_dai_gong;
    }

    @Override
    public void initView() {
        chengJieDaiGongAdpter =new ChengJieDaiGongAdpter(getActivity());
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(chengJieDaiGongAdpter);
        initRefresh();
        showLoading();
        initdata();
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                page++;
                initdata();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                page = 1;
                initdata();
            }
        });
    }

    private void initdata() {
        showLoading();
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
        HashMapSingleton.getInstance().put("cateid", id);
        HashMapSingleton.getInstance().put("navid", 2);
        HashMapSingleton.getInstance().put("page", page);
        RemoteRepository.getInstance()
                .more_scinfo(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<MoreScinfoBean>() {
                    @Override
                    protected void success(MoreScinfoBean data) {
                        dissmissLoading();
                        if (page == 1) {
                            chengJieDaiGongAdpter.refresh(data.getRetval().getRecordList());
                        } else {
                            chengJieDaiGongAdpter.loadMore(data.getRetval().getRecordList());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }


}