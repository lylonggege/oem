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
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.ListCollectAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.ListCollectBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.ICallBackPosition;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.view.activity.MainActivity;
import com.zhangying.oem1688.view.activity.home.NewProductFactoryActivity;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.nulldatatextview)
    RelativeLayout nulldatatextview;
    @BindView(R.id.null_tv)
    TextView nullTv;
    @BindView(R.id.null_tv_button)
    TextView nullTvButton;
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
        listCollectAdpter.setiCallBackPosition(new ICallBackPosition() {
            @Override
            public void getPosition(int position) {
                drop_collect(position);
            }
        });


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
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("page", page);
        RemoteRepository.getInstance()
                .list_collect(HashMapSingleton.getInstance())
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

                        setnullView();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    //取消收藏
    private void drop_collect(int position) {
        String store_id = listCollectAdpter.getItem(position).getStore_id();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("id", store_id);
        RemoteRepository.getInstance()
                .drop_collect(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean data) {
                        dissmissLoading();
                        if (data.isDone()) {
                            //移除列表数据
                            ToastUtils.toast("取消收藏成功");
                            initdata();
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

    private void setnullView() {
        if (listCollectAdpter.getItemCount() > 0) {
            nulldatatextview.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        } else {
            nulldatatextview.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            nullTv.setText("你还没有关注的店铺");
            nullTvButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EvenBusBean evenBusBean = new EvenBusBean();
                    evenBusBean.setType(1);
                    AppManagerUtil.getInstance().finishAllActivity();
                    EventBus.getDefault().post(evenBusBean);
                }
            });
        }
    }
}