package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.BrowseRecordAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.TokenUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 浏览记录
 */
public class BrowseRecordActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.factory_tv)
    TextView factoryTv;
    @BindView(R.id.factory_tv_line)
    TextView factoryTvLine;
    @BindView(R.id.company_tv)
    TextView companyTv;
    @BindView(R.id.company_tv_line)
    TextView companyTvLine;
    @BindView(R.id.edit_tv)
    TextView editTv;
    @BindView(R.id.checksign_imageview)
    ImageView checksign_imageview;
    @BindView(R.id.checksign_rl)
    RelativeLayout checksign_rl;
    @BindView(R.id.relative_button)
    RelativeLayout relative_button;
    private int page = 1;
    private BrowseRecordAdpter browseRecordAdpter;
    private boolean iseditTv = true;
    private boolean isSelect = true;
    private int type = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse_record;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("浏览足迹");
        browseRecordAdpter = new BrowseRecordAdpter(this);
        browseRecordAdpter.setiJumPage(new IJumPage() {
            @Override
            public void success() {
                isSelect = true;
                checksign_imageview.setVisibility(View.GONE);
            }
        });
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(browseRecordAdpter);
        initRefresh();
        initdata();
        factoryTv.setSelected(true);
        factoryTvLine.setSelected(true);
    }

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, BrowseRecordActivity.class);
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
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("page", page);
        HashMapSingleton.getInstance().put("type", type);
        RemoteRepository.getInstance()
                .list_history(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<ListHistoryBean>() {

                    @Override
                    protected void success(ListHistoryBean data) {
                        dissmissLoading();
                        List<ListHistoryBean.RetvalBean.RecordListBean> listhistory = data.getRetval().getRecordList();
                        if (page == 1) {
                            browseRecordAdpter.refresh(listhistory);
                        } else {
                            browseRecordAdpter.loadMore(listhistory);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    @OnClick({R.id.factory_rl, R.id.company_rl, R.id.edit_tv, R.id.checksign_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.factory_rl:
                type = 1;
                page = 1;
                factoryTv.setSelected(true);
                factoryTvLine.setSelected(true);
                companyTv.setSelected(false);
                companyTvLine.setSelected(false);
                break;
            case R.id.company_rl:
                type = 1;
                page = 1;
                factoryTv.setSelected(false);
                factoryTvLine.setSelected(false);
                companyTv.setSelected(true);
                companyTvLine.setSelected(true);
                break;
            case R.id.edit_tv:
                iseditTv = !iseditTv;
                List<ListHistoryBean.RetvalBean.RecordListBean> data = browseRecordAdpter.getData();
                if (iseditTv) {
                    relative_button.setVisibility(View.GONE);
                    editTv.setText("编辑");
                    for (ListHistoryBean.RetvalBean.RecordListBean datum : data) {
                        datum.setIschecked_bg(false);
                    }
                } else {
                    relative_button.setVisibility(View.VISIBLE);
                    editTv.setText("完成");
                    for (ListHistoryBean.RetvalBean.RecordListBean datum : data) {
                        datum.setIschecked_bg(true);
                    }
                }
                browseRecordAdpter.notifyDataSetChanged();
                break;
            case R.id.checksign_rl:
                isSelect = !isSelect;
                List<ListHistoryBean.RetvalBean.RecordListBean> data2 = browseRecordAdpter.getData();
                if (isSelect) {
                    checksign_imageview.setVisibility(View.GONE);
                    for (ListHistoryBean.RetvalBean.RecordListBean datum : data2) {
                        datum.setIschecked(false);
                    }
                } else {
                    checksign_imageview.setVisibility(View.VISIBLE);
                    for (ListHistoryBean.RetvalBean.RecordListBean datum : data2) {
                        datum.setIschecked(true);
                    }
                }
                browseRecordAdpter.notifyDataSetChanged();
                break;
        }
    }
}