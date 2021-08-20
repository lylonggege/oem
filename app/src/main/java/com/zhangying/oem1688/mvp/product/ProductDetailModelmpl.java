package com.zhangying.oem1688.mvp.product;

import com.zhangying.oem1688.bean.MineinfoBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.onterface.BaseModel;
import com.zhangying.oem1688.singleton.HashMapSingleton;

public class ProductDetailModelmpl implements BaseModel {
    public ProductDetailModelmpl(String id) {
        this.id = id;
    }
    private String id;

    @Override
    public void getData(BaseFinishListener baseFinishListener) {
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
        HashMapSingleton.getInstance().put("id", id);
        RemoteRepository.getInstance()
                .mineinfo(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<MineinfoBean>() {
                    @Override
                    protected void success(MineinfoBean data) {
                        baseFinishListener.success(data);
                        baseFinishListener.hidendloading();
                    }
                });
    }
}
