package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.ListCollectAdpter;
import com.zhangying.oem1688.adpter.WordsAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.ListCollectBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.TokenUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListCollectActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private ListCollectAdpter listCollectAdpter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_collect;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("收藏");
        listCollectAdpter = new ListCollectAdpter(this);
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(listCollectAdpter);
        initRefresh();
        initdata();

    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, ListCollectActivity.class);
        context.startActivity(intent);
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
        long timestamp = System.currentTimeMillis() / 1000;
        HashMap<String, Object> map = new HashMap<>();
        HashMapSingleton.getInstance().put("page", page);
        map.put("timestamp", timestamp);
        map.put("token", TokenUtils.getToken());
        String url = timestamp  + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
        String md5Str = MD5Util.getMD5Str(url);
        map.put("sign", md5Str);
        RemoteRepository.getInstance()
                .list_collect(map)
                .subscribeWith(new DefaultDisposableSubscriber<ListCollectBean>() {

                    @Override
                    protected void success(ListCollectBean data) {
                        dissmissLoading();
                        List<ListCollectBean.RetvalBean.RecordListBean> recordList = data.getRetval().getRecordList();
                        if (page == 1) {
                            listCollectAdpter.refresh(recordList);
                        } else {
                            listCollectAdpter.loadMore(recordList);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    @OnClick(R.id.bacK_RL)
    public void onClick() {
        finish();
    }
}