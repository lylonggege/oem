package com.zhangying.oem1688.mvp.newfactoryproduct;

import com.zhangying.oem1688.bean.MoreProstoreBeanmvp;
import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.util.StringUtils;

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
        String page = moreProstoreBeanmvp.getPage();
        Integer iPage = 0;
        if (!StringUtils.isEmity(page) && StringUtils.isNumeric(page)){
            iPage = Integer.parseInt(page);
        }

        if (iPage == 1)baseView.showloading();

        mfactoryProductNewModel.getData(moreProstoreBeanmvp, this);
    }
}
