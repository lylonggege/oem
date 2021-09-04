package com.zhangying.oem1688.view.activity.my;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xutil.common.StringUtils;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.adpter.LabelAdpter;
import com.zhangying.oem1688.adpter.WordsAdpter;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.custom.MyRecycleView;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.ICallMobile;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.ToastUtil;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

public class StoreMessageActivity extends BaseActivity {
    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.recycview)
    MyRecycleView recycview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private int page = 1;
    private LabelAdpter labelAdpter;
    private static int storeId;

    @Override
    protected int getLayoutId() {
        return R.layout.store_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTV.setText("公司留言");
        labelAdpter = new LabelAdpter(this);
        labelAdpter.setiCallMobile(new ICallMobile() {
            @Override
            public void toCall(String tel) {
                if (StringUtils.isEmpty(tel)){
                    return;
                }

                //判断权限并拨打电话
                AutoForcePermissionUtils.requestPermissions(StoreMessageActivity.this, new AutoForcePermissionUtils.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + tel);
                        intent.setData(data);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied() {
                        ToastUtil.showToast("拨打电话权限被拒绝，请手动拨打！");
                    }
                }, Manifest.permission.CALL_PHONE);
            }
        });
        labelAdpter.setStoreMsg(true);
        WidgetUtils.initRecyclerView(recycview);
        recycview.setAdapter(labelAdpter);
        initdata();
        initRefresh();
    }

    @OnClick(R.id.bacK_RL)
    public void onClick() {
        if (!AppUtils.isFastClick()){
            return;
        }

        finish();
    }

    private void initdata() {
        showLoading();
        HashMapSingleton.getInstance().reload();
        HashMapSingleton.getInstance().put("lytype", "gsly");
        HashMapSingleton.getInstance().put("storeid", storeId);
        HashMapSingleton.getInstance().put("page", page);
        RemoteRepository.getInstance()
                .message_list(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<MessageListBean>() {

                    @Override
                    protected void success(MessageListBean messageListBean) {
                        dissmissLoading();
                        List<MessageListBean.RetvalBean> retval = messageListBean.getRetval();
                        if (page == 1) {
                            labelAdpter.refresh(retval);
                        } else {
                            labelAdpter.loadMore(retval);
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

    public static void simpleActivity(Context context, int iStoreId) {
        Intent intent = new Intent(context, StoreMessageActivity.class);
        storeId = iStoreId;
        context.startActivity(intent);
    }
}