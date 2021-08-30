package com.zhangying.oem1688.mvp.my;

import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.bean.MineinfoBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;

public class MemberInfoModelImpl implements MemberInfoModel {

    @Override
    public void getData(MemberInfoFinishListener memberInfoFinishListener) {
        HashMapSingleton.getInstance().reload();
        RemoteRepository.getInstance()
                .mineinfo(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<MineinfoBean>() {

                    @Override
                    protected void success(MineinfoBean data) {
                        memberInfoFinishListener.success(data);
                        memberInfoFinishListener.hidendloading();
                    }
                });
    }
}
