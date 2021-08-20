package com.zhangying.oem1688.mvp.leave;

import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BaseModel;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseView;

public class LeaveMessagePersenterImpl implements BasePresenter, BaseFinishListener {

    private BaseView baseView;
    private BaseModel baseModel;

    public LeaveMessagePersenterImpl(BaseView baseView, DateBean dateBean) {
        this.baseView = baseView;
        baseModel = new LeaveMessageModel(dateBean);
    }


    @Override
    public void validateCredentials() {
        //开始加载
        baseView.showloading();
        baseModel.getData(this);
    }

    @Override
    public void success(Object o) {
        baseView.success(o);
    }

    @Override
    public void hidendloading() {
        baseView.hidenloading();
    }
}
