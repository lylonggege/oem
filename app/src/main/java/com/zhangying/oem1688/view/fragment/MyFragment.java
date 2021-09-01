package com.zhangying.oem1688.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.bean.EvenBusMessageBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.MineinfoBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.custom.MyfollowsLL;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.mvp.my.MemberInfoPresenter;
import com.zhangying.oem1688.mvp.my.MemberInfoPresenterImpl;
import com.zhangying.oem1688.mvp.my.MemberInfoView;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AutoForcePermissionUtils;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ScreenTools;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.util.TokenUtils;
import com.zhangying.oem1688.view.activity.entry.LoginActivity;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;
import com.zhangying.oem1688.view.activity.my.BrowseRecordActivity;
import com.zhangying.oem1688.view.activity.my.LabelActivity;
import com.zhangying.oem1688.view.activity.my.ListCollectActivity;
import com.zhangying.oem1688.view.activity.my.MessageActivity;
import com.zhangying.oem1688.view.activity.my.MyAboutDGBActivity;
import com.zhangying.oem1688.view.activity.my.MyCustomerServiceActivity;
import com.zhangying.oem1688.view.activity.my.ReleaseListActivity;
import com.zhangying.oem1688.view.activity.my.SetActivity;
import com.zhangying.oem1688.view.activity.my.StoreMessageActivity;
import com.zhangying.oem1688.view.activity.my.WebActivity;
import com.zhangying.oem1688.view.activity.my.WordsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment implements MemberInfoView {
    @BindView(R.id.head_imageView)
    RadiusImageView headImageView;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.update_TV)
    TextView updateTV;
    @BindView(R.id.user_personal)
    ImageView userPersonal;
    @BindView(R.id.user_personal_RL)
    RelativeLayout userPersonalRL;
    @BindView(R.id.user_post)
    ImageView userPost;
    @BindView(R.id.user_post_RL)
    RelativeLayout userPostRL;
    @BindView(R.id.user_zuji)
    ImageView userZuji;
    @BindView(R.id.user_zuji_RL)
    RelativeLayout userZujiRL;
    @BindView(R.id.user_kefu)
    ImageView userKefu;
    @BindView(R.id.user_kefu_RL)
    RelativeLayout userKefuRL;
    @BindView(R.id.user_about)
    ImageView userAbout;
    @BindView(R.id.user_about_RL)
    RelativeLayout userAboutRL;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_logout)
    TextView btnLogout;
    @BindView(R.id.factorycenter1_IV)
    RadiusImageView factorycenter1IV;
    @BindView(R.id.factorycenter2_IV)
    RadiusImageView factorycenter2IV;
    @BindView(R.id.message_rl)
    RelativeLayout messagerl;
    @BindView(R.id.group_ll)
    LinearLayout group_ll;

    private MemberInfoPresenter memberInfoPresenter;
    private MineinfoBean.RetvalBean.MineinfoBean2 mineinfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        memberInfoPresenter = new MemberInfoPresenterImpl(this);
        memberInfoPresenter.validateCredentials();

        if (!LoginActivity.hasLogin()){
            btnLogout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.update_TV, R.id.user_personal_RL,
            R.id.user_post_RL, R.id.user_zuji_RL, R.id.user_kefu_RL, R.id.user_about_RL,
            R.id.factorycenter1_IV, R.id.factorycenter2_IV, R.id.message_rl, R.id.user_set_RL,R.id.btn_login})
    public void onClick(View view) {
        boolean hasLogin = LoginActivity.simpleActivity(getActivity(), BuildConfig.UPDATE_MYFRAGMNET_ENTER_TYPE);
        if (!hasLogin) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_logout://退出登录
                doLogout();
                break;
            case R.id.update_TV:
                break;
            case R.id.user_personal_RL://个人信息
                MessageActivity.simpleActivity(getActivity());
                break;
            case R.id.user_post_RL://我的发布
                ReleaseListActivity.simpleActivity(getActivity());
                break;
            case R.id.user_zuji_RL://浏览足迹
                //足迹
                BrowseRecordActivity.simpleActivity(getActivity());
                break;
            case R.id.user_kefu_RL://在线客服
                MyCustomerServiceActivity.simpleActivity(getActivity());
                break;
            case R.id.user_about_RL://关于代工帮
                MyAboutDGBActivity.simpleActivity(getActivity());
                break;
            case R.id.factorycenter1_IV://会员权益
                WebActivity.simpleActivity(getActivity(), "会员权益");
                break;
            case R.id.factorycenter2_IV://贴牌商库
                boolean canDo = true;
                List<MineinfoBean.RetvalBean.MineinfoBean2.ItemfactoryBean> itemFactorys = mineinfo.getItemfactory();
                if (itemFactorys == null || itemFactorys.size() < 2){
                    canDo = false;
                }else if (StringUtils.isEmity(itemFactorys.get(1).getSurl())){
                    canDo = false;
                }

                if (!canDo){
                    ToastUtil.showToast("只限会员查看...");
                    break;
                }

                LabelActivity.simpleActivity(getActivity());
                break;
            case R.id.message_rl://个人信息
                MessageActivity.simpleActivity(getActivity());
                break;
            case R.id.user_set_RL:
                SetActivity.simpleActivity(getActivity());
                break;
        }
    }

    //退出登录
    private void doLogout(){
        new XPopup.Builder(getActivity())
                .hasShadowBg(true)
                .asConfirm(null, "确定退出登录吗？",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                showloading();
                                HashMapSingleton.getInstance().reload();
                                RemoteRepository.getInstance()
                                        .logout(HashMapSingleton.getInstance())
                                        .subscribeWith(new DefaultDisposableSubscriber<BaseBean>() {
                                            @Override
                                            protected void success(BaseBean data) {
                                                dissmissLoading();
                                                //清除本地token
                                                TokenUtils.clearToken();

                                                //刷新本地数据
                                                btnLogout.setVisibility(View.GONE);
                                                memberInfoPresenter.validateCredentials();
                                            }

                                            @Override
                                            public void onError(Throwable t) {
                                                super.onError(t);
                                                dissmissLoading();
                                            }
                                        });
                            }
                        }, null, false)
                .show();
    }

    @Override
    public void showloading() {
        showLoading();
    }

    @Override
    public void hidenloading() {
        dissmissLoading();
    }

    @Override
    public void success(MineinfoBean memberInfoBean) {
        MineinfoBean.RetvalBean retval = memberInfoBean.getRetval();
        mineinfo = retval.getMineinfo();
        GlideUtil.loadImage(getContext(), BuildConfig.URL + mineinfo.getCurportrait(), headImageView);
        nameTv.setText(mineinfo.getNickname());
        updateTV.setText(mineinfo.getShop_name());

        //设置发布.收藏、留言
        List<MineinfoBean.RetvalBean.MineinfoBean2.MyfollowsBean> myfollows = mineinfo.getMyfollows();
        group_ll.removeAllViews();
        for (int i = 0; i < myfollows.size(); i++) {
            int screenWidth = ScreenTools.instance(getActivity()).getScreenWidth();
            int mWidth = screenWidth / myfollows.size();
            MineinfoBean.RetvalBean.MineinfoBean2.MyfollowsBean myfollowsBean = myfollows.get(i);
            MyfollowsLL myfollowsLL = new MyfollowsLL(getActivity(), null);
            group_ll.addView(myfollowsLL);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) myfollowsLL.getLayoutParams();
            layoutParams.width = mWidth;
            myfollowsLL.setLayoutParams(layoutParams);
            LinearLayout rootView_follows_ll = myfollowsLL.findViewById(R.id.rootView_follows_ll);
            TextView nums_textview = myfollowsLL.findViewById(R.id.nums_textview);
            ImageView spic_imageview = myfollowsLL.findViewById(R.id.spic_imageview);
            TextView sname_textview = myfollowsLL.findViewById(R.id.sname_textview);
            String spic = myfollowsBean.getSpic();
            if (spic != null && spic.length() > 0) {
                spic_imageview.setVisibility(View.VISIBLE);
                nums_textview.setVisibility(View.GONE);
                GlideUtil.loadImage(getActivity(), spic, spic_imageview);
            } else {
                spic_imageview.setVisibility(View.GONE);
                nums_textview.setVisibility(View.VISIBLE);
                nums_textview.setText(myfollowsBean.getNums());
            }
            sname_textview.setText(myfollowsBean.getSname());

            rootView_follows_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //判断是否登录
                    boolean hasLogin = LoginActivity.simpleActivity(getActivity(), BuildConfig.UPDATE_MYFRAGMNET_ENTER_TYPE);
                    if (!hasLogin) {
                        return;
                    }

                    //操作类型(8：收藏店铺、9：留言咨询、10：我的店铺、11：店铺咨询
                    String stype = myfollowsBean.getStype();
                    //使用switch 有bug
                    if ("2".equals(stype)) {
                        //我的发布
                        ReleaseListActivity.simpleActivity(getActivity());
                    } else if ("8".equals(stype)) {
                        //8：收藏店铺
                        ListCollectActivity.simpleActivity(getActivity());
                    } else if ("9".equals(stype)) {
                        //9：留言咨询
                        WordsActivity.simpleActivity(getActivity());
                    } else if ("10".equals(stype)) {
                        //10：我的店铺
                        Integer iStoreId = mineinfo.getStore_id();
                        if (iStoreId <= 0){
                            ToastUtil.showToast("未关联工厂");
                            return;
                        }

                        FactoryDetailActivity.simpleActivity(getActivity(),iStoreId + "");
                    } else if ("11".equals(stype)) {
                        //11：店铺咨询
                        StoreMessageActivity.simpleActivity(getActivity(), mineinfo.getStore_id());
                    }
                }
            });
        }
    }

    //登录成功后路由返回数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventData(EvenBusMessageBean message) {
        if (message != null) {
            //进行了收藏操作  跟新UI
            if (message.getType() == 3) {
                if (memberInfoPresenter != null) {
                    memberInfoPresenter.validateCredentials();
                }

                if (LoginActivity.hasLogin()){
                    btnLogout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}