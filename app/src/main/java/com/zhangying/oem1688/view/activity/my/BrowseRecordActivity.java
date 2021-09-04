package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.zhangying.oem1688.adpter.BrowseRecordGoodsAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;

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
    @BindView(R.id.recycview2)
    MyRecycleView recycview2;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.factory_tv)
    TextView factoryTv;
    @BindView(R.id.factory_tv_line)
    TextView factoryTvLine;
    @BindView(R.id.goods_tv)
    TextView companyTv;
    @BindView(R.id.goods_tv_line)
    TextView goodsTvLine;
    @BindView(R.id.edit_tv)
    TextView editTv;
    @BindView(R.id.checksign_imageview)
    ImageView checksign_imageview;
    @BindView(R.id.checksign_rl)
    RelativeLayout checksign_rl;
    @BindView(R.id.relative_button)
    RelativeLayout relative_button;
    @BindView(R.id.delete_rl)
    RelativeLayout delete_rl;
    @BindView(R.id.record_number_tv)
    TextView record_number_tv;
    private int page = 1;
    private BrowseRecordAdpter browseRecordAdpter;
    private BrowseRecordGoodsAdpter browseRecordGoodsAdpter;
    private boolean iseditTv = false;  //点击编辑是改变状态
    private boolean isSelect = false;  //点击全选是改变状态
    private int type = 1; //1工厂  2是产品

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse_record;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("浏览足迹");
        browseRecordAdpter = new BrowseRecordAdpter(this);
        browseRecordGoodsAdpter = new BrowseRecordGoodsAdpter(this);


        browseRecordAdpter.setiJumPage(new IJumPage() {
            @Override
            public void success() {

                //判断是否全部选中
                isSelect = false;
                checksign_imageview.setVisibility(View.GONE);
                int existence = 0;
                for (int i = 0; i < browseRecordAdpter.getData().size(); i++) {
                    ListHistoryBean.RetvalBean.RecordListBean recordListBean = browseRecordAdpter.getData().get(i);
                    if (!recordListBean.isIschecked()) {
                        existence = 1;
                        break;
                    }
                }

                if (existence == 0) {
                    isSelect = true;
                    checksign_imageview.setVisibility(View.VISIBLE);
                }


            }
        });

        browseRecordGoodsAdpter.setiJumPage(new IJumPage() {
            @Override
            public void success() {

                //判断是否全部选中
                isSelect = false;
                checksign_imageview.setVisibility(View.GONE);
                int existence = 0;
                for (int i = 0; i < browseRecordGoodsAdpter.getData().size(); i++) {
                    ListHistoryBean.RetvalBean.RecordListBean recordListBean = browseRecordGoodsAdpter.getData().get(i);
                    if (!recordListBean.isIschecked()) {
                        existence = 1;
                        break;
                    }
                }

                if (existence == 0) {
                    isSelect = true;
                    checksign_imageview.setVisibility(View.VISIBLE);
                }


            }
        });

        WidgetUtils.initRecyclerView(recycview);
        int space = getResources().getDimensionPixelSize(R.dimen.dp_5);
        WidgetUtils.initGridRecyclerView(recycview2, 2, space);
        recycview.setAdapter(browseRecordAdpter);
        recycview2.setAdapter(browseRecordGoodsAdpter);
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

                        if (type == 1) {
                            recycview.setVisibility(View.VISIBLE);
                            recycview2.setVisibility(View.GONE);
                            if (page == 1) {
                                browseRecordAdpter.refresh(listhistory);
                            } else {
                                browseRecordAdpter.loadMore(listhistory);
                            }
                            String content = "您有 <font color='red'>" + data.getRetval().getTotalCount() + "</font> 条浏览记录</font>";
                            record_number_tv.setText(Html.fromHtml(content));
                        } else {
                            recycview.setVisibility(View.GONE);
                            recycview2.setVisibility(View.VISIBLE);
                            if (page == 1) {
                                browseRecordGoodsAdpter.refresh(listhistory);
                            } else {
                                browseRecordGoodsAdpter.loadMore(listhistory);
                            }
                            String content = "您有 <font color='red'>" + data.getRetval().getTotalCount() + "</font> 条浏览记录</font>";
                            record_number_tv.setText(Html.fromHtml(content));
                        }

                        //编辑的状态true
                        iseditTv = false;
                        bianji(listhistory);
                        checksign_imageview.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }

    @OnClick({R.id.factory_rl, R.id.goods_rl, R.id.edit_tv, R.id.checksign_rl, R.id.delete_rl, R.id.bacK_RL})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        switch (view.getId()) {
            case R.id.bacK_RL:
                finish();
                break;
            case R.id.delete_rl:
                //删除  record_number_tv
                //适配器数据清除
                StringBuffer stringBuffer = new StringBuffer();
                if (type == 1) {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data1 = browseRecordAdpter.getData();
                    for (int i = 0; i < data1.size(); i++) {
                        ListHistoryBean.RetvalBean.RecordListBean recordListBean = data1.get(i);
                        if (recordListBean.isIschecked()) {
                            stringBuffer.append(recordListBean.getItem_id() + ",");
                        }
                    }
                } else {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data1 = browseRecordGoodsAdpter.getData();
                    for (int i = 0; i < data1.size(); i++) {
                        ListHistoryBean.RetvalBean.RecordListBean recordListBean = data1.get(i);
                        if (recordListBean.isIschecked()) {
                            stringBuffer.append(recordListBean.getItem_id() + ",");
                        }
                    }
                }


                if (stringBuffer.length() == 0) {
                    break;
                }

                String substring = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
                drop_history(substring);

                break;
            case R.id.factory_rl:
                type = 1;
                page = 1;
                isSelect = true;
                iseditTv = false;
                factoryTv.setSelected(true);
                factoryTvLine.setSelected(true);
                companyTv.setSelected(false);
                goodsTvLine.setSelected(false);
                initdata();

                break;
            case R.id.goods_rl:
                type = 2;
                page = 1;
                isSelect = true;
                iseditTv = false;
                factoryTv.setSelected(false);
                factoryTvLine.setSelected(false);
                companyTv.setSelected(true);
                goodsTvLine.setSelected(true);

                initdata();
                break;
            case R.id.edit_tv:
                if (type == 1) {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data = browseRecordAdpter.getData();
                    if (iseditTv) {
                        bianji(data);
                    } else {
                        relative_button.setVisibility(View.VISIBLE);
                        editTv.setText("完成");
                        for (ListHistoryBean.RetvalBean.RecordListBean datum : data) {
                            datum.setIschecked_bg(true);
                        }
                    }
                    browseRecordAdpter.notifyDataSetChanged();
                } else {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data = browseRecordGoodsAdpter.getData();
                    if (iseditTv) {
                        bianji(data);
                    } else {
                        relative_button.setVisibility(View.VISIBLE);
                        editTv.setText("完成");
                        for (ListHistoryBean.RetvalBean.RecordListBean datum : data) {
                            datum.setIschecked_bg(true);
                        }
                    }
                    browseRecordGoodsAdpter.notifyDataSetChanged();

                    iseditTv = !iseditTv;

                }

                break;
            case R.id.checksign_rl:
                isSelect = !isSelect;
                if (type == 1) {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data2 = browseRecordAdpter.getData();
                    if (!isSelect) {
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
                } else {
                    List<ListHistoryBean.RetvalBean.RecordListBean> data2 = browseRecordGoodsAdpter.getData();
                    if (!isSelect) {
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
                    browseRecordGoodsAdpter.notifyDataSetChanged();
                }

                break;
        }
    }

    private void drop_history(String ids) {
        HashMapSingleton.getInstance().reload();
        //清空标记(1：是、0：否).
        if (isSelect) {
            HashMapSingleton.getInstance().put("all", 1);
        } else {
            HashMapSingleton.getInstance().put("all", 0);
        }

        HashMapSingleton.getInstance().put("type", type);
        HashMapSingleton.getInstance().put("ids", ids);

        RemoteRepository.getInstance()
                .drop_history(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {

                    @Override
                    protected void success(BaseBean data) {
                        if (data.isDone()) {
                            initdata();

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    private void bianji(List<ListHistoryBean.RetvalBean.RecordListBean> data) {

        relative_button.setVisibility(View.GONE);
        editTv.setText("编辑");
        for (ListHistoryBean.RetvalBean.RecordListBean datum : data) {
            datum.setIschecked_bg(false);
        }
    }
}