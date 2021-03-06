package com.zhangying.oem1688.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.AppUtils;
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

import java.lang.reflect.Field;
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
    @BindView(R.id.nologin_vew)
    TextView noLoginTipView;
    @BindView(R.id.login_vew)
    RelativeLayout loginTipView;
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
        //??????????????????
        memberInfoPresenter = new MemberInfoPresenterImpl(this);
        if (LoginActivity.hasLogin()){
            memberInfoPresenter.validateCredentials();

            noLoginTipView.setVisibility(View.GONE);
            loginTipView.setVisibility(View.VISIBLE);
        }else {
            noLoginTipView.setVisibility(View.VISIBLE);
            loginTipView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.update_TV, R.id.user_personal_RL,
            R.id.user_post_RL, R.id.user_zuji_RL, R.id.user_kefu_RL, R.id.user_about_RL,
            R.id.factorycenter1_IV, R.id.factorycenter2_IV, R.id.message_rl, R.id.user_set_RL, R.id.nologin_vew})
    public void onClick(View view) {
        if (!AppUtils.isFastClick()){
            return;
        }

        int tagId = view.getId();
        if (tagId != R.id.user_kefu_RL && tagId != R.id.user_about_RL && tagId != R.id.user_set_RL){
            boolean hasLogin = LoginActivity.simpleActivity(getActivity(), BuildConfig.UPDATE_MYFRAGMNET_ENTER_TYPE);
            if (!hasLogin) {
                return;
            }
        }

        switch (view.getId()) {
            case R.id.update_TV:
                break;
            case R.id.user_personal_RL://????????????
                MessageActivity.simpleActivity(getActivity());
                break;
            case R.id.user_post_RL://????????????
                ReleaseListActivity.simpleActivity(getActivity());
                break;
            case R.id.user_zuji_RL://????????????
                //??????
                BrowseRecordActivity.simpleActivity(getActivity());
                break;
            case R.id.user_kefu_RL://????????????
                MyCustomerServiceActivity.simpleActivity(getActivity());
                break;
            case R.id.user_about_RL://???????????????
                MyAboutDGBActivity.simpleActivity(getActivity());
                break;
            case R.id.factorycenter1_IV://????????????
                WebActivity.simpleActivity(getActivity(), "????????????");
                break;
            case R.id.factorycenter2_IV://????????????
                boolean canDo = true;
                List<MineinfoBean.RetvalBean.MineinfoBean2.ItemfactoryBean> itemFactorys = mineinfo.getItemfactory();
                if (itemFactorys == null || itemFactorys.size() < 2){
                    canDo = false;
                }else if (StringUtils.isEmity(itemFactorys.get(1).getSurl())){
                    canDo = false;
                }

                if (!canDo){
                    ToastUtil.showToast("??????????????????...");
                    break;
                }

                LabelActivity.simpleActivity(getActivity());
                break;
            case R.id.message_rl://????????????
                MessageActivity.simpleActivity(getActivity());
                break;
            case R.id.user_set_RL:
                SetActivity.simpleActivity(getActivity());
                break;
            default:
                break;
        }
    }

    //????????????
    private void doLogout(){
        new XPopup.Builder(getActivity())
                .hasShadowBg(true)
                .asConfirm(null, "????????????????????????",
                        "??????", "??????",
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
                                                //????????????token
                                                TokenUtils.clearToken();

                                                //??????????????????
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

    private int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //??????????????????
    private void updateMinePage(MineinfoBean memberInfoBean){
        MineinfoBean.RetvalBean retval = memberInfoBean.getRetval();
        mineinfo = retval.getMineinfo();
        GlideUtil.loadImage(getContext(), BuildConfig.URL + mineinfo.getCurportrait(), headImageView);
        nameTv.setText(mineinfo.getNickname());
        updateTV.setText(mineinfo.getShop_name());

        //????????????.???????????????
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
            nums_textview.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
            nums_textview.getPaint().setStrokeWidth(1.2f);

            ImageView spic_imageview = myfollowsLL.findViewById(R.id.spic_imageview);
            TextView sname_textview = myfollowsLL.findViewById(R.id.sname_textview);
            String spic = myfollowsBean.getSpic();
            if (spic != null && spic.length() > 0) {
                spic_imageview.setVisibility(View.VISIBLE);
                nums_textview.setVisibility(View.GONE);

                spic_imageview.setBackground(getResources().getDrawable(getResId(spic,R.drawable.class)));
                //GlideUtil.loadImage(getActivity(), spic, spic_imageview);
            } else {
                spic_imageview.setVisibility(View.GONE);
                nums_textview.setVisibility(View.VISIBLE);
                nums_textview.setText(myfollowsBean.getNums());
            }
            sname_textview.setText(myfollowsBean.getSname());

            rootView_follows_ll.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    //??????????????????
                    boolean hasLogin = LoginActivity.simpleActivity(getActivity(), BuildConfig.UPDATE_MYFRAGMNET_ENTER_TYPE);
                    if (!hasLogin) {
                        return;
                    }

                    //????????????(8??????????????????9??????????????????10??????????????????11???????????????
                    String stype = myfollowsBean.getStype();
                    //??????switch ???bug
                    if ("2".equals(stype)) {
                        //????????????
                        ReleaseListActivity.simpleActivity(getActivity());
                    } else if ("8".equals(stype)) {
                        //8???????????????
                        ListCollectActivity.simpleActivity(getActivity());
                    } else if ("9".equals(stype)) {
                        //9???????????????
                        WordsActivity.simpleActivity(getActivity());
                    } else if ("10".equals(stype)) {
                        //10???????????????
                        Integer iStoreId = mineinfo.getStore_id();
                        if (iStoreId <= 0){
                            ToastUtil.showToast("???????????????");
                            return;
                        }

                        FactoryDetailActivity.simpleActivity(getActivity(),iStoreId + "");
                    } else if ("11".equals(stype)) {
                        //11???????????????
                        StoreMessageActivity.simpleActivity(getActivity(), mineinfo.getStore_id());
                    }
                }
            });
        }
    }

    @Override
    public void success(MineinfoBean memberInfoBean) {
        updateMinePage(memberInfoBean);
    }

    //?????????????????????????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventData(EvenBusMessageBean message) {
        if (message == null || message.getType() != 3) { return; }
        if (memberInfoPresenter == null) {return;}

        if (LoginActivity.hasLogin()){
            memberInfoPresenter.validateCredentials();

            noLoginTipView.setVisibility(View.GONE);
            loginTipView.setVisibility(View.VISIBLE);
            return;
        }

        headImageView.setImageResource(R.drawable.avatar); //????????????
        group_ll.removeAllViews();
        noLoginTipView.setVisibility(View.VISIBLE);
        loginTipView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}