package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.NewsOemMoreAdpter;
import com.zhangying.oem1688.adpter.WordsAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.OemNewsMoreBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordsActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private WordsAdpter wordsAdpter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("我的留言");
        wordsAdpter = new WordsAdpter();
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(wordsAdpter);
        initdata();
        initRefresh();
    }

    @OnClick(R.id.bacK_RL)
    public void onClick() {
        finish();
    }


    private void initdata() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        map.put("page", page);
        RemoteRepository.getInstance()
                .mymessage(map)
                .subscribeWith(new DefaultDisposableSubscriber<WordsBean>() {

                    @Override
                    protected void success(WordsBean data) {
                        dissmissLoading();
                        WordsBean.RetvalBean.MineagentBean mineagent = data.getRetval().getMineagent();
                        if (page == 1) {
                            wordsAdpter.refresh(mineagent.getGoods());
                        } else {
                            wordsAdpter.loadMore(mineagent.getGoods());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
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

    public static void simpleActivity(Context context) {
        Intent intent = new Intent(context, WordsActivity.class);
        context.startActivity(intent);
    }
}