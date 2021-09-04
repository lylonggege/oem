package com.zhangying.oem1688.view.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.MyRightBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.title_TV)
    TextView titleTV;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("TITLE");
        titleTV.setText(title);
        myright();
    }

    @OnClick(R.id.bacK_RL)
    public void onClick() {
        if (!AppUtils.isFastClick()){
            return;
        }

        finish();
    }

    public static void simpleActivity(Context context,String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE",title);
        context.startActivity(intent);
    }

    private void myright() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", "app");
        RemoteRepository.getInstance()
                .myright(map)
                .subscribeWith(new DefaultDisposableSubscriber<MyRightBean>() {

                    @Override
                    protected void success(MyRightBean data) {
                        dissmissLoading();
                        String content = data.getRetval().getContent();
                        webView.loadUrl(content);//加载url

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        dissmissLoading();
                    }
                });
    }
}