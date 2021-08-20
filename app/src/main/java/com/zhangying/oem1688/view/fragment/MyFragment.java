package com.zhangying.oem1688.view.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.base.BaseFragment;
import com.zhangying.oem1688.bean.MineinfoBean;
import com.zhangying.oem1688.constant.BuildConfig;
import com.zhangying.oem1688.mvp.my.MemberInfoPresenter;
import com.zhangying.oem1688.mvp.my.MemberInfoPresenterImpl;
import com.zhangying.oem1688.mvp.my.MemberInfoView;
import com.zhangying.oem1688.util.GlideUtil;
import com.zhangying.oem1688.util.ToastUtil;
import com.zhangying.oem1688.view.activity.my.BrowseRecordActivity;
import com.zhangying.oem1688.view.activity.my.LabelActivity;
import com.zhangying.oem1688.view.activity.my.ListCollectActivity;
import com.zhangying.oem1688.view.activity.my.MessageActivity;
import com.zhangying.oem1688.view.activity.my.MyAboutDGBActivity;
import com.zhangying.oem1688.view.activity.my.MyCustomerServiceActivity;
import com.zhangying.oem1688.view.activity.my.SetActivity;
import com.zhangying.oem1688.view.activity.my.WebActivity;
import com.zhangying.oem1688.view.activity.my.WordsActivity;

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
    @BindView(R.id.release_LL)
    LinearLayout releaseLL;
    @BindView(R.id.collection_LL)
    LinearLayout collectionLL;
    @BindView(R.id.words_LL)
    LinearLayout wordsLL;
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

    private MemberInfoPresenter memberInfoPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        memberInfoPresenter = new MemberInfoPresenterImpl(this);
        memberInfoPresenter.validateCredentials();
    }

    @OnClick({ R.id.update_TV, R.id.release_LL,
            R.id.collection_LL, R.id.words_LL, R.id.user_personal_RL,
            R.id.user_post_RL, R.id.user_zuji_RL, R.id.user_kefu_RL, R.id.user_about_RL,
            R.id.factorycenter1_IV, R.id.factorycenter2_IV, R.id.message_rl,R.id.user_set_RL})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_TV:
                break;
            case R.id.release_LL:

                break;
            case R.id.collection_LL:
                //收藏
                ListCollectActivity.simpleActivity(getActivity());
                break;
            case R.id.words_LL:
                //留言
                WordsActivity.simpleActivity(getActivity());
                break;
            case R.id.user_personal_RL:
                break;
            case R.id.user_post_RL:
                break;
            case R.id.user_zuji_RL:
                //足迹
                BrowseRecordActivity.simpleActivity(getActivity());
                break;
            case R.id.user_kefu_RL:
                MyCustomerServiceActivity.simpleActivity(getActivity());
                break;
            case R.id.user_about_RL:
                MyAboutDGBActivity.simpleActivity(getActivity());
                break;
            case R.id.factorycenter1_IV:
                //会员权益
                WebActivity.simpleActivity(getActivity(),"会员权益");
                break;
            case R.id.factorycenter2_IV:
                //贴牌商库
                LabelActivity.simpleActivity(getActivity());
                break;
            case R.id.message_rl:
                MessageActivity.simpleActivity(getActivity());
                break;
            case R.id.user_set_RL:
                SetActivity.simpleActivity(getActivity());
                break;
        }
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
        MineinfoBean.RetvalBean.MineinfoBean2 mineinfo = retval.getMineinfo();
        GlideUtil.loadImage(getContext(), BuildConfig.URL + mineinfo.getCurportrait(), headImageView);
        nameTv.setText(mineinfo.getNickname());

    }
}