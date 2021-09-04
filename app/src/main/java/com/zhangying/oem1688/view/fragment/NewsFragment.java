package com.zhangying.oem1688.view.fragment;

import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.LoadingDialog;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.NewsOemMoreAdpter;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.OemNewsMoreBean;
import com.zhangying.oem1688.custom.FenLeiRealization;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.view.activity.home.SearchActivity;

import java.util.HashMap;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class NewsFragment extends BaseFragment {
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    NewsOemMoreAdpter newsOemMoreAdpter;
    LoadingDialog loading;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        newsOemMoreAdpter = new NewsOemMoreAdpter(getActivity());
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(newsOemMoreAdpter);
        initRefresh();
        initdata();
    }

    private void initdata() {
        if (page == 1){
            loading = new LoadingDialog(getActivity());
            loading.show();
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ly", "app");
        hashMap.put("page", page);
        RemoteRepository.getInstance()
                .oemnewsmore(hashMap)
                .subscribeWith(new DefaultDisposableSubscriber<OemNewsMoreBean>() {
                    @Override
                    protected void success(OemNewsMoreBean data) {
                        if (page == 1) {
                            loading.dismiss();
                            newsOemMoreAdpter.refresh(data.getRetval());
                        } else {
                            newsOemMoreAdpter.loadMore(data.getRetval());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (page == 1) {
                            loading.dismiss();
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


    @OnClick({R.id.imageView2, R.id.textView})
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

                    }
                });
                fenLeiRealization.realization();
                break;
            case R.id.textView:
                SearchActivity.simpleActivity(getActivity());
                break;
        }
    }
}