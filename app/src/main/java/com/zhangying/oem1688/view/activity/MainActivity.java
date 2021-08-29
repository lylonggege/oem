package com.zhangying.oem1688.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseActivity;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.HomeTabBean;
import com.zhangying.oem1688.mvp.home.TabberPresenter;
import com.zhangying.oem1688.mvp.home.TabberPresenterImpl;
import com.zhangying.oem1688.mvp.home.TabberView;
import com.zhangying.oem1688.view.fragment.HomeFragment;
import com.zhangying.oem1688.view.fragment.MyFragment;
import com.zhangying.oem1688.view.fragment.NewsFragment;
import com.zhangying.oem1688.view.fragment.ProductFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements TabberView {

    @BindView(R.id.content_view)
    FrameLayout contentView;
    @BindView(R.id.home_ima)
    ImageView homeIma;
    @BindView(R.id.home_text)
    TextView homeText;
    @BindView(R.id.home_line)
    LinearLayout homeLine;
    @BindView(R.id.factory_ima)
    ImageView factoryIma;
    @BindView(R.id.factory_text)
    TextView factoryText;
    @BindView(R.id.factory_line)
    LinearLayout factoryLine;
    @BindView(R.id.product_ima)
    ImageView productIma;
    @BindView(R.id.product_text)
    TextView productText;
    @BindView(R.id.product_line)
    LinearLayout productLine;
    @BindView(R.id.my_ima)
    ImageView myIma;
    @BindView(R.id.my_text)
    TextView myText;
    @BindView(R.id.my_line)
    LinearLayout myLine;
    @BindView(R.id.LinearLayout_share)
    LinearLayout LinearLayoutShare;
    @BindView(R.id.action_bar_view)
    View actionBarView;
    @BindView(R.id.news_ima)
    ImageView newsIma;
    @BindView(R.id.news_text)
    TextView newsText;
    @BindView(R.id.news_line)
    LinearLayout newsLine;
    private Fragment homeFragment, newsFragment, myFragmnet, productFragment, productFragment2;
    private TabberPresenter tabberPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabberPresenter = new TabberPresenterImpl(this);
        tabberPresenter.validateCredentials();
        selectTab(1);
        selectTab(0);
        EventBus.getDefault().register(this);

    }

//    private void initLogin() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("ly", "app");
//        map.put("phone", "zhangying");
//        map.put("code", "12356");
//        RemoteRepository.getInstance()
//                .pwdlogin(map)
//                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
//
//                    @Override
//                    protected void success(BaseBean data) {
//                        ToastUtil.showToast(data.getMsg());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        super.onError(t);
//
//                    }
//                });
//    }


    private void selectTab(int index) {
        releaseImaAndText();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        hintAllFragment(transaction);
        selectImaAndText(index);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content_view, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE",1);
                    productFragment.setArguments(bundle);
                    transaction.add(R.id.content_view, productFragment);
                } else {
                    transaction.show(productFragment);
                }

                break;
            case 2:
                if (productFragment2 == null) {
                    productFragment2 = new ProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("TYPE",0);
                    productFragment2.setArguments(bundle);
                    transaction.add(R.id.content_view, productFragment2);
                } else {
                    transaction.show(productFragment2);
                }
                break;
            case 3:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content_view, newsFragment);
                } else {
                    transaction.show(newsFragment);
                }
                break;

            case 4:
                if (myFragmnet == null) {
                    myFragmnet = new MyFragment();
                    transaction.add(R.id.content_view, myFragmnet);
                } else {
                    transaction.show(myFragmnet);
                }
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    private void releaseImaAndText() {
        homeIma.setSelected(false);
        homeText.setSelected(false);
        factoryIma.setSelected(false);
        factoryText.setSelected(false);
        productIma.setSelected(false);
        productText.setSelected(false);
        myIma.setSelected(false);
        myText.setSelected(false);
        newsIma.setSelected(false);
        newsText.setSelected(false);
    }

    private void selectImaAndText(int index) {
        switch (index) {
            case 0:
                homeIma.setSelected(true);
                homeText.setSelected(true);
                break;
            case 1:
                factoryIma.setSelected(true);
                factoryText.setSelected(true);
                break;
            case 2:
                productIma.setSelected(true);
                productText.setSelected(true);
                break;
            case 3:
                newsIma.setSelected(true);
                newsText.setSelected(true);
                break;
            case 4:
                myIma.setSelected(true);
                myText.setSelected(true);
                break;

        }
    }


    private void hintAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (productFragment != null) {
            transaction.hide(productFragment);
        }
        if (productFragment2 != null) {
            transaction.hide(productFragment2);
        }
        if (newsFragment != null) {
            transaction.hide(newsFragment);
        }
        if (myFragmnet != null) {
            transaction.hide(myFragmnet);
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.home_line, R.id.factory_line, R.id.product_line, R.id.my_line, R.id.news_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_line:
                selectTab(0);
                break;
            case R.id.factory_line:
                selectTab(1);
                break;
            case R.id.product_line:
                selectTab(2);
                break;
            case R.id.news_line:
                selectTab(3);
                break;
            case R.id.my_line:
                selectTab(4);
                break;
        }
    }


    @Override
    public void showloading() {
    }

    @Override
    public void hidenloading() {
        Log.e("开始加载数据到结束", "validateCredentials: ");
    }

    @Override
    public void success(HomeTabBean data) {
        for (int i = 0; i < data.getRetval().size(); i++) {
            switch (i) {
                case 0:
                    homeText.setText(data.getRetval().get(0).getFname());
                    break;
                case 1:
                    factoryText.setText(data.getRetval().get(1).getFname());
                    break;
                case 2:
                    productText.setText(data.getRetval().get(2).getFname());
                    break;
                case 3:
                    newsText.setText(data.getRetval().get(3).getFname());
                    break;
                case 4:
                    myText.setText(data.getRetval().get(4).getFname());
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void evenBusBean(EvenBusBean bean) {
        int type = bean.getType();
        if (type == 1) {
            selectTab(1);
        }
    }


}