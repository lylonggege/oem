package com.zhangying.oem1688.mvp.home;

import android.util.Log;

import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.HomeTabBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;

import java.util.List;

public class TabberModel implements TabberModl {

    @Override
    public void showtabber(TabberFinishListener tabberFinishListener) {
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
        RemoteRepository.getInstance()
                .gettabber(HashMapSingleton.getInstance())
                .subscribeWith(new DefaultDisposableSubscriber<HomeTabBean>() {

                    @Override
                    protected void success(HomeTabBean data) {
                        Log.e("hhahah", "success: " + data.getRetval().size());
                        tabberFinishListener.success(data);
                        tabberFinishListener.hidenloading();
                    }

                    @Override
                    protected void failure(String errMsg) {
                        super.failure(errMsg);
                        Log.e("hhahah", "errMsg: " + errMsg);
                    }
                });
    }
}
