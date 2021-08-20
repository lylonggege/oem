package com.zhangying.oem1688.mvp.factory;

public interface CompamyFactoryModel {
    void getdata(int company_factory_type,int catebid, int catesid, CompanyFactoryFinishListener companyFactoryFinishListener);
}
