package com.zhangying.oem1688.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.bean.HomeTabBean;
import com.zhangying.oem1688.bean.PrivacyBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.home.TabberPresenter;
import com.zhangying.oem1688.mvp.home.TabberPresenterImpl;
import com.zhangying.oem1688.mvp.home.TabberView;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.EventBusStyeSingleton;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
import com.zhangying.oem1688.util.MyUtilsWebView;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.WebViewSeting;
import com.zhangying.oem1688.view.activity.my.MyWebActivity;
import com.zhangying.oem1688.view.fragment.HomeFragment;
import com.zhangying.oem1688.view.fragment.MyFragment;
import com.zhangying.oem1688.view.fragment.NewsFragment;
import com.zhangying.oem1688.view.fragment.ProductFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TabberView {

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
    @BindView(R.id.webView)
    WebView webPrivacy;
    @BindView(R.id.agree_tv_popu)
    TextView btnAgreePriv;
    @BindView(R.id.disagree_tv_popu)
    TextView btnDisagreePriv;
    @BindView(R.id.priLayout)
    RelativeLayout priLayout;
    private Fragment homeFragment, newsFragment, myFragmnet, productFragment, productFragment2;
    private TabberPresenter tabberPresenter;
    private int tabIndex;
    // IWXAPI ????????????app??????????????????openApi??????
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tabberPresenter = new TabberPresenterImpl(this);
        tabberPresenter.validateCredentials();
        //selectTab(1);
        //?????????????????????
        selectTab(0);

        //??????????????????
        setPrivacyView();
        EventBus.getDefault().register(this);
    }

    //???????????????????????????????????????????????????????????????????????????????????????????????????????????? id
    private void regToWx() {
        // ??????WXAPIFactory???????????????IWXAPI?????????
        api = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APPID, true);

        // ????????????appId???????????????
        api.registerApp(BuildConfig.WX_APPID);

        //?????????????????????????????????????????????????????????
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // ??????app???????????????
                api.registerApp(BuildConfig.WX_APPID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    private void setPrivacyContent(String content){
        String s = MyUtilsWebView.setWebViewText(content, "font-size:14px;color:#666666;line-height:1.8", "\na{text-decoration:none}\n");
        WebViewSeting.setting(webPrivacy, MainActivity.this, s);
    }

    //?????????????????????????????????
    private void setPrivacyView(){
        //??????SharedPreferences??????
        String STRING_KEY = "STRING_KEY";
        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        int iAgree = sp.getInt(STRING_KEY, 0);
        if (iAgree == 1){//?????????
            priLayout.setVisibility(View.GONE);
        }else {
            String privacyInfo = String.format("<p>????????????????????????App!<br>" +
                    "?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????<a href=\"%s\" style=\"color:#4395ff\">??????????????????</a>???<a href=\"%s\" style=\"color:#4395ff\">??????????????????</a>???????????????????????????<br>" +
                    "1.??????????????????????????????????????????????????????????????????????????????????????????;<br>" +
                    "2.??????????????????????????????????????????????????????????????????????????????????????????????????????;<br>" +
                    "3.?????????????????????????????????????????????????????????????????????????????????;??????????????????????????????????????????????????????????????????;<br>" +
                    "4.????????????????????????????????????????????????????????????????????????????????????????????????;", BuildConfig.URL_AGREEMENT,BuildConfig.URL_PRIVACY);
            setPrivacyContent(privacyInfo);

            //??????????????????
            RemoteRepository.getInstance()
                    .get_privacy()
                    .subscribeWith(new DefaultDisposableSubscriber<PrivacyBean>() {

                        @Override
                        protected void success(PrivacyBean data) {
                            setPrivacyContent(data.getRetval().getInfo());
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                        }
                    });

            Context that = this;
            webPrivacy.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("phone://")){

                    }else if (url.startsWith("email://")){

                    }else if (url.startsWith("local://")){

                    }else if (url.startsWith("share://")){

                    }else {
                        MyWebActivity.simpleActivity(that, url, "");
                    }
                    return true;
                }
            });

            //????????????
            btnAgreePriv.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(STRING_KEY, 1);
                    editor.commit();
                    priLayout.setVisibility(View.GONE);

                    //?????????????????????
                    createUniqueID();
                    //???????????????
                    regToWx();
                    //MobSDK??????????????????????????????
                    MobSDK.submitPolicyGrantResult(true, null);
                }
            });

            //???????????????
            btnDisagreePriv.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {
                    priLayout.setVisibility(View.GONE);
                    System.exit(0);
                }
            });

            priLayout.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View v) {

                }
            });
        }
    }

    //??????????????????
    private void createUniqueID(){
        //we make this look like a valid IMEI//13 digits
        String m_szDevIDShort = "35" +
                Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10;

        //????????????
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        hashMap.put("did",m_szDevIDShort);
        hashMap.put("dtype","2");
        hashMap.put("ctype",Build.MODEL);
        hashMap.put("cversion",android.os.Build.VERSION.SDK_INT);

        PackageInfo packInfo = getPackageInfo(this);
        hashMap.put("aversion",packInfo != null ? packInfo.versionCode : "");
        RemoteRepository.getInstance()
                .count_device(hashMap)
                .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                    @Override
                    protected void success(BaseBean newscontBean) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);

                    }
                });
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    private void selectTab(int index) {
        releaseImaAndText();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        hintAllFragment(transaction);
        selectImaAndText(index);
        tabIndex = index;
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
                    bundle.putInt("TYPE", 1);
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
                    bundle.putInt("TYPE", 0);
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

                //??????????????????
                EventBusStyeSingleton.getInstance().updateMyfragment();
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
        Log.e("???????????????????????????", "validateCredentials: ");
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
        }else if (type == 0){
            selectTab(0);
        }
    }

    //??????????????????????????????????????????
    private long firstTime = 0;

    /**
     * ????????????keyUp   ?????????????????????????????????
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (tabIndex > 0){//??????????????????????????????
                selectTab(0);
                return false;
            }else {//???????????????????????????app
                if (homeFragment == null){
                    return false;
                }

                //???????????????????????????
                HomeFragment tmpHomeFragment = (HomeFragment)homeFragment;
                int iHomeTab = tmpHomeFragment.getCurrentTab();
                if (iHomeTab > 0){
                    tmpHomeFragment.setHomeCurrentTab();
                    return false;
                }

                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtil.showToast("????????????????????????");
                    firstTime = secondTime;
                    return true;
                }

                System.exit(0);
                return false;
            }
        }

        return super.onKeyUp(keyCode, event);
    }
}