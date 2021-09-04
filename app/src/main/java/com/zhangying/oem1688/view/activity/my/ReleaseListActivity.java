package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.ChengJieDaiGongAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.MoreScinfoBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.view.activity.home.ReleaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class ReleaseListActivity extends BaseActivity {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.nulldatatextview)
    RelativeLayout nulldatatextview;
    @BindView(R.id.null_tv)
    TextView nullTv;
    @BindView(R.id.null_tv_button)
    TextView nullTvButton;
    private int page = 1;
    private ChengJieDaiGongAdpter chengJieDaiGongAdpter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("我的发布");
        chengJieDaiGongAdpter = new ChengJieDaiGongAdpter(this);
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(chengJieDaiGongAdpter);
        initRefresh();
        showLoading();
        initdata();
    }

    @OnClick({R.id.back_im, R.id.bacK_RL})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.back_im:
                finish();
                break;
            case R.id.bacK_RL:
                finish();
                break;
        }
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
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("self", 1);
        HashMapSingleton.getInstance().put("page", page);
        HashMapSingleton.getInstance().put("self", "1");
        HashMapSingleton.getInstance().put("navid", "2");
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

                        setnullView();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, ReleaseListActivity.class);
        context.startActivity(intent);
    }

    private void setnullView() {
        if (chengJieDaiGongAdpter.getItemCount() > 0) {
            nulldatatextview.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        } else {
            nulldatatextview.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            nullTv.setText("暂未发布相关信息");
            nullTvButton.setText("去发布");
            nullTvButton.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    ReleaseActivity.simpleActivity(ReleaseListActivity.this);
                    finish();
                }
            });
        }
    }


}