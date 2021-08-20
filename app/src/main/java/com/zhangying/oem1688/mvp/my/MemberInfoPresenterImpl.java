package com.zhangying.oem1688.mvp.my;

import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.bean.MineinfoBean;

public class MemberInfoPresenterImpl implements MemberInfoPresenter, MemberInfoFinishListener {
    private MemberInfoModel memberInfoModel;
    private MemberInfoView memberInfoView;

    public MemberInfoPresenterImpl(MemberInfoView memberInfoView) {
        this.memberInfoView =memberInfoView;
        memberInfoModel = new MemberInfoModelImpl();
    }

    @Override
    public void success(MineinfoBean memberInfoBean) {
        memberInfoView.success(memberInfoBean);
    }

    @Override
    public void hidendloading() {
        memberInfoView.hidenloading();
    }

    @Override
    public void validateCredentials() {
        memberInfoView.showloading();
        memberInfoModel.getData(this);
    }
}
