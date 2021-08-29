package com.zhangying.oem1688.mvp.newfactoryproduct;

import com.zhangying.oem1688.bean.MoreProstoreBeanmvp;
import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseView;

public class FactoryProductPersenterImpl implements BasePresenter, BaseFinishListener {
    private FactoryProductNewModel mfactoryProductNewModel;
    private BaseView baseView;
    private MoreProstoreBeanmvp moreProstoreBeanmvp;
    public FactoryProductPersenterImpl( BaseView baseView) {
        this.baseView = baseView;

        mfactoryProductNewModel = new FactoryProductNewImpl();
    }

    public void saveData(MoreProstoreBeanmvp moreProstoreBeanmvp) {
        this.moreProstoreBeanmvp = moreProstoreBeanmvp;
    }

    @Override
    public void success(Object o) {
        baseView.success(o);
    }

    @Override
    public void hidendloading() {
        baseView.hidenloading();
    }

    @Override
    public void validateCredentials() {
        if ("1".equals(moreProstoreBeanmvp.getPage())){
            baseView.showloading();
        }

        mfactoryProductNewModel.getData(moreProstoreBeanmvp, this);
    }
}
