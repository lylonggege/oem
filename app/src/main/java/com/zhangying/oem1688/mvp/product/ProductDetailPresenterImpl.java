package com.zhangying.oem1688.mvp.product;

import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BaseModel;
import com.zhangying.oem1688.onterface.BasePresenter;
import com.zhangying.oem1688.onterface.BaseView;

public class ProductDetailPresenterImpl implements BasePresenter, BaseFinishListener {


    private BaseModel baseModel;
    private BaseView baseView;

    public ProductDetailPresenterImpl(String id, BaseView baseView) {
        this.baseView = baseView;
        baseModel = new ProductDetailModelmpl(id);
    }

    //开始
    @Override
    public void validateCredentials() {
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
