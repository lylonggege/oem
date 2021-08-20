package com.zhangying.oem1688.mvp.factory;

import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.singleton.HashMapSingleton;

public class CompanyFactoryModelImpl implements CompamyFactoryModel {

    @Override
    public void getdata(int company_factory_type, int catebid, int catesid, CompanyFactoryFinishListener companyFactoryFinishListener) {
        //请求数据
        HashMapSingleton.getInstance().clear();
        HashMapSingleton.getInstance().put("ly", "app");
        HashMapSingleton.getInstance().put("catebid", catebid);
        HashMapSingleton.getInstance().put("catesid", catesid);
        if (company_factory_type == 7) {
            RemoteRepository.getInstance()
                    .getgoodslists(HashMapSingleton.getInstance())
                    .subscribeWith(new DefaultDisposableSubscriber<CompanyFactoryBean>() {

                        @Override
                        protected void success(CompanyFactoryBean data) {
                            companyFactoryFinishListener.success(data);
                            companyFactoryFinishListener.hidenloading();
                        }
                    });
        } else if (company_factory_type == 6){
            RemoteRepository.getInstance()
                    .getstorelists(HashMapSingleton.getInstance())
                    .subscribeWith(new DefaultDisposableSubscriber<CompanyFactoryBean>() {
                        @Override
                        protected void success(CompanyFactoryBean data) {
                            companyFactoryFinishListener.success(data);
                            companyFactoryFinishListener.hidenloading();
                        }
                    });
        }
    }
}
