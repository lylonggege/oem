package com.zhangying.oem1688.mvp.newfactoryproduct;

import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.bean.MoreProstoreBeanmvp;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseFinishListener;
import com.zhangying.oem1688.singleton.HashMapSingleton;

import java.util.HashMap;

public class FactoryProductNewImpl implements FactoryProductNewModel {
    @Override
    public void getData(MoreProstoreBeanmvp moreProstoreBeanmvp, BaseFinishListener baseFinishListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ly", moreProstoreBeanmvp.getLy());
        map.put("page", moreProstoreBeanmvp.getPage());
        map.put("catebid", moreProstoreBeanmvp.getCatebid());
        map.put("catesid", moreProstoreBeanmvp.getCatesid());
        map.put("areabid", moreProstoreBeanmvp.getAreabid());
        map.put("areasid", moreProstoreBeanmvp.getAreasid());
        map.put("kw", moreProstoreBeanmvp.getKw());
        map.put("itype", moreProstoreBeanmvp.getItype());

        RemoteRepository.getInstance()
                .moreprostore(map)
                .subscribeWith(new DefaultDisposableSubscriber<MoreProstoreBean>() {

                    @Override
                    protected void success(MoreProstoreBean data) {
                        baseFinishListener.success(data);
                        baseFinishListener.hidendloading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });

    }
}
