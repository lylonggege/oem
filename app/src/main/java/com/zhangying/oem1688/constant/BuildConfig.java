package com.zhangying.oem1688.constant;

import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.TokenUtils;

public class BuildConfig {
    public static String URL = "http://m.oem1688.cn/";
    public static String URL_PRIVACY = URL + "?app=news&act=notice&id=45";    //用户协议
    public static String URL_AGREEMENT = URL + "?app=news&act=notice&id=44";    //隐私政策
    public static String MD5_SPLIC = "";
    public static boolean debug = false;
    //区分选择的是工厂还是产品  6工厂  7产品
    public static String COMPANY_FACTORY_TYPE = "COMPANY_FACTORY_TYPE";
    public static String CATEBID = "CATEBID";
    public static String CATESID = "CATESID";
    public static String DAIGONGPINGLEI = "DAIGONGPINGLEI"; //代工品类标记

    public static String WX_APPID = "wx309c1651b07c82f7";

    public static String url = (System.currentTimeMillis() / 1000) + TokenUtils.getToken() + "&^%$RSTUih09135ZST)(*";
    public static String md5Str = MD5Util.getMD5Str(url);
    public static String PARAMETERS = "&ly=app&timestamp=" + (System.currentTimeMillis() / 1000) + "&token=" + TokenUtils.getToken() + "@sign=" + md5Str;

    //进入登录activity的标记
    public static int PRODUCT_ENTER_TYPE = 1;  //产品进入
    public static int FACTORY_ENTER_TYPE = 2;  //公司进入
    public static int UPDATE_MYFRAGMNET_ENTER_TYPE = 3;  //刷新我的界面

}
