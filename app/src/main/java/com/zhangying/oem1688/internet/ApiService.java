package com.zhangying.oem1688.internet;

import com.zhangying.oem1688.bean.AboutBean;
import com.zhangying.oem1688.bean.BaseBean;
import com.zhangying.oem1688.bean.BaseBeancClass;
import com.zhangying.oem1688.bean.CateAreaListBean;
import com.zhangying.oem1688.bean.CcatesJsonBean;
import com.zhangying.oem1688.bean.CompanyFactoryBean;
import com.zhangying.oem1688.bean.FactoryDetailBean;
import com.zhangying.oem1688.bean.FactoryGCateBean;
import com.zhangying.oem1688.bean.FactoryGoodsBean;
import com.zhangying.oem1688.bean.GoodsdetailBean;
import com.zhangying.oem1688.bean.HomeBena;
import com.zhangying.oem1688.bean.HomeGoodsBean;
import com.zhangying.oem1688.bean.HomeTabBean;
import com.zhangying.oem1688.bean.ListCollectBean;
import com.zhangying.oem1688.bean.ListHistoryBean;
import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.bean.MessageListBean;
import com.zhangying.oem1688.bean.MessagePrivBean;
import com.zhangying.oem1688.bean.MessageViewBean;
import com.zhangying.oem1688.bean.MineinfoBean;
import com.zhangying.oem1688.bean.MoreProstoreBean;
import com.zhangying.oem1688.bean.MoreScinfoBean;
import com.zhangying.oem1688.bean.MyRightBean;
import com.zhangying.oem1688.bean.NewscontBean;
import com.zhangying.oem1688.bean.OemNewsMoreBean;
import com.zhangying.oem1688.bean.OemkefuBean;
import com.zhangying.oem1688.bean.PhoneloginBean;
import com.zhangying.oem1688.bean.RecomendIndexBean;
import com.zhangying.oem1688.bean.ScinfoDetailBean;
import com.zhangying.oem1688.bean.ScinfoTopBean;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.bean.WordsBean;
import com.zhangying.oem1688.constant.BuildConfig;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {


    @GET("?app=xcxindex&act=index&ly=app")
        // App首页数据返回(接口数据参照页面从上至下的顺序)
    Flowable<HomeBena> gethome();

    //s首页推荐
    @GET("?app=default&act=recomend_index")
    Flowable<RecomendIndexBean> getrecomendindex(@QueryMap HashMap<String, Object> paramsMap);

    //首页底部Tabbar数据
    @GET("?app=xcxindex&act=footinfo")
    Flowable<HomeTabBean> gettabber(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory - 代工品
    @GET("?app=xcxindex&act=goodslists")
    Flowable<CompanyFactoryBean> getgoodslists(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory - 找工厂
    @GET("?app=xcxindex&act=storelists")
    Flowable<CompanyFactoryBean> getstorelists(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory -  个人信息
    @GET("?app=member&act=getmemberinfo&ly=app")
    Flowable<MemberInfoBean> getmemberinfo(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory -  关于代工帮
    @GET("?app=xcxindex&act=aboutoem")
    Flowable<AboutBean> aboutoem(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory -  我的首页
    @GET("?app=member&act=mineinfo")
    Flowable<MineinfoBean> mineinfo(@QueryMap HashMap<String, Object> paramsMap);

    //获取Factory -  在线客服
    @GET("?app=xcxindex&act=oemkefu")
    Flowable<OemkefuBean> oemkefu(@QueryMap HashMap<String, Object> paramsMap);

    //帮忙找工厂页面初始化
    @GET("?app=xcxindex&act=cate_area_list&ly=app")
    Flowable<CateAreaListBean> cate_area_list();

    //发布承接代工信息或帮忙找工厂信息
    @FormUrlEncoded
    @POST("?app=default&act=demandadd&ly=app")
    Flowable<BaseBean> demandadd(@FieldMap HashMap<String, Object> hashMap);

    //获取产品详情
    @GET("?app=xcxindex&act=goodsdetail")
    Flowable<GoodsdetailBean> goodsdetail(@QueryMap HashMap<String, Object> hashMap);

    // 收藏店铺
    @FormUrlEncoded
    @POST("?app=default&act=storecollect&ly=app")
    Flowable<BaseBean> storecollect(@FieldMap HashMap<String, Object> hashMap);

    //退出登录
    @GET("?member&act=ajax_logout")
    Flowable<BaseBean> logout(@FieldMap HashMap<String, Object> hashMap);

    // 取消收藏店铺
    @FormUrlEncoded
    @POST("?app=default&act=drop_collect&ly=app")
    Flowable<BaseBean> drop_collect(@FieldMap HashMap<String, Object> hashMap);

    //动态列表
    @GET("?app=xcxindex&act=oemnewsmore")
    Flowable<OemNewsMoreBean> oemnewsmore(@QueryMap HashMap<String, Object> hashMap);

    //获取公司详情数据-公司首页
    @GET("?app=xcxindex&act=get_store")
    Flowable<FactoryDetailBean> get_store(@QueryMap HashMap<String, Object> hashMap);

    //获取公司产品分类-公司首页
    @GET("?app=xcxindex&act=store_gcate")
    Flowable<FactoryGCateBean> get_store_gcate(@QueryMap HashMap<String, Object> hashMap);

    //获取公司产品列表-公司首页
    @GET("?app=store&act=ajax_goodsmore")
    Flowable<FactoryGoodsBean> get_store_goods(@QueryMap HashMap<String, Object> hashMap);

    //首页顶部导航右侧快捷导航数据
    @GET("?app=xcxindex&act=sitetopinfo&ly=app")
    Flowable<SitetopinfoBean> sitetopinfo();

    //新闻详情
    @FormUrlEncoded
    @POST("?app=xcxindex&act=newscont")
    Flowable<NewscontBean> newscont(@FieldMap HashMap<String, Object> hashMap);

    //需求详情
    @GET("?app=xcxindex&act=scinfo_detail")
    Flowable<ScinfoDetailBean> scinfo_detail(@QueryMap HashMap<String, Object> hashMap);

    //留言
    @FormUrlEncoded
    @POST("?app=default&act=add_message&ly=app")
    Flowable<BaseBean> add_message(@FieldMap HashMap<String, Object> hashMap);

    //留言
    @FormUrlEncoded
    @POST("?app=xcxindex&act=scinfo_top")
    Flowable<ScinfoTopBean> scinfo_top(@FieldMap HashMap<String, Object> hashMap);

    //我的发布、发布列表数据集合
    @GET("?app=default&act=more_scinfo")
    Flowable<MoreScinfoBean> more_scinfo(@QueryMap HashMap<String, Object> hashMap);


    //Demand - 承接代工【发布】页面初始化
    @FormUrlEncoded
    @POST("?app=default&act=cates_json")
    Flowable<CcatesJsonBean> cates_json(@FieldMap HashMap<String, Object> hashMap);

    //编辑个人信息
    @FormUrlEncoded
    @POST("?app=member&act=edithyoem&ly=app")
    Flowable<BaseBean> edithyoem(@FieldMap HashMap<String, Object> hashMap);

    //上传头像
    @FormUrlEncoded
    @POST("?app=uploadpic&act=gravatar")
    Flowable<BaseBeancClass> gravatar(@FieldMap HashMap<String, Object> hash你Map);

    //我的留言
    @GET("?app=member&act=mymessage")
    Flowable<WordsBean> mymessage(@QueryMap HashMap<String, Object> hashMap);


    //我的收藏
    @GET("?app=default&act=list_collect&ly=app")
    Flowable<ListCollectBean> list_collect(@QueryMap HashMap<String, Object> hashMap);

    //Login - 手机号登录
    @FormUrlEncoded
    @POST("?app=member&act=phonelogin")
    Flowable<PhoneloginBean> phonelogin(@FieldMap HashMap<String, Object> hashMap);

    //Login - 账号密码登录
    @FormUrlEncoded
    @POST("?app=member&act=pwdlogin")
    Flowable<BaseBean> pwdlogin(@FieldMap HashMap<String, Object> hashMap);

    //Login - 发送验证码
    @FormUrlEncoded
    @POST("?app=sms&act=send_code")
    Flowable<BaseBean> send_code(@FieldMap HashMap<String, Object> hashMap);

    //Login -  绑定手机号
    @FormUrlEncoded
    @POST("?app=member&act=bind_mobile")
    Flowable<BaseBean> bind_mobile(@FieldMap HashMap<String, Object> hashMap);

    //Login -  找回密码
    @FormUrlEncoded
    @POST("?app=member&act=find_pwd")
    Flowable<BaseBean> find_pwd(@FieldMap HashMap<String, Object> hashMap);

    //Factory - 工厂或产品分页
    @GET("?app=xcxindex&act=moreprostore")
    Flowable<MoreProstoreBean> moreprostore(@QueryMap HashMap<String, Object> hashMap);

    //首页 - 加载更多商品
    @GET("?app=xcxindex&act=goods_more")
    Flowable<HomeGoodsBean> home_goods_more(@QueryMap HashMap<String, Object> hashMap);

    //Member - 浏览足迹
    @GET("?app=default&act=list_history&ly=app")
    Flowable<ListHistoryBean> list_history(@QueryMap HashMap<String, Object> hashMap);

    // 会员权益
    @FormUrlEncoded
    @POST("?app=xcxindex&act=myright")
    Flowable<MyRightBean> myright(@FieldMap HashMap<String, Object> hashMap);

    //Member - 公司咨询(贴牌商库)
    @GET("?app=member&act=message_list")
    Flowable<MessageListBean> message_list(@QueryMap HashMap<String, Object> hashMap);

    //Member - 公司咨询(贴牌商库) - 页面权限
    @GET("?app=xcxindex&act=wodely")
    Flowable<MessagePrivBean> message_priv(@QueryMap HashMap<String, Object> hashMap);

    //Member - 公司咨询(贴牌商库) - 详情
    @GET("?app=member&act=message_view")
    Flowable<MessageViewBean> message_view(@QueryMap HashMap<String, Object> hashMap);

    // Login - 退出登录
    @FormUrlEncoded
    @POST("?app=member&act=ajax_logout")
    Flowable<BaseBean> ajax_logout(@FieldMap HashMap<String, Object> hashMap);

    //  删除浏览足迹
    @FormUrlEncoded
    @POST("?app=default&act=drop_history")
    Flowable<BaseBean> drop_history(@FieldMap HashMap<String, Object> hashMap);


}
