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
import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.bean.MessageListBean;
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
import com.zhangying.oem1688.bean.ListHistoryBean;

import org.intellij.lang.annotations.Flow;

import java.util.HashMap;

import io.reactivex.Flowable;

public class RemoteRepository {

    private static RemoteRepository INSTANCE = new RemoteRepository();
    private final ApiService serverApi;

    public static RemoteRepository getInstance() {
        return INSTANCE;
    }

    private RemoteRepository() {
        serverApi = NetFactory.getRetrofit().create(ApiService.class);
    }

    public Flowable<HomeBena> gethome() {//绑定手机号
        return serverApi.gethome();
    }

    public Flowable<RecomendIndexBean> getrecomendindex(HashMap<String, Object> body) {//绑定手机号
        return serverApi.getrecomendindex(body);
    }


    public Flowable<HomeTabBean> gettabber(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.gettabber(body);
    }

    public Flowable<CompanyFactoryBean> getstorelists(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.getstorelists(body);
    }

    public Flowable<CompanyFactoryBean> getgoodslists(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.getgoodslists(body);
    }

    public Flowable<MemberInfoBean> getmemberinfo(HashMap<String, Object> body) {
        return serverApi.getmemberinfo(body);
    }

    public Flowable<AboutBean> aboutoem(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.aboutoem(body);
    }

    public Flowable<MineinfoBean> mineinfo(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.mineinfo(body);
    }

    public Flowable<OemkefuBean> oemkefu(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.oemkefu(body);
    }

    public Flowable<CateAreaListBean> cate_area_list() {
        return serverApi.cate_area_list();
    }

    public Flowable<BaseBean> demandadd(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.demandadd(body);
    }

    public Flowable<GoodsdetailBean> goodsdetail(HashMap<String, Object> body) {//首页底部Tabbar
        return serverApi.goodsdetail(body);
    }

    public Flowable<BaseBean> storecollect(HashMap<String, Object> body) {//收藏
        return serverApi.storecollect(body);
    }

    public Flowable<BaseBean> drop_collect(HashMap<String, Object> body) {//取消收藏
        return serverApi.drop_collect(body);
    }

    public Flowable<OemNewsMoreBean> oemnewsmore(HashMap<String, Object> body) {
        return serverApi.oemnewsmore(body);
    }

    public Flowable<FactoryDetailBean> get_store(HashMap<String, Object> body) {
        return serverApi.get_store(body);
    }

    public Flowable<FactoryGCateBean>get_store_gcate(HashMap<String, Object> body) {
        return serverApi.get_store_gcate(body);
    }

    public Flowable<FactoryGoodsBean>get_store_goods(HashMap<String, Object> body) {
        return serverApi.get_store_goods(body);
    }

    public Flowable<SitetopinfoBean> sitetopinfo() {
        return serverApi.sitetopinfo();
    }

    public Flowable<NewscontBean> newscont(HashMap<String, Object> body) {
        return serverApi.newscont(body);
    }

    public Flowable<ScinfoDetailBean> scinfo_detail(HashMap<String, Object> body) {
        return serverApi.scinfo_detail(body);
    }


    public Flowable<BaseBean> add_message(HashMap<String, Object> body) {
        return serverApi.add_message(body);
    }

    public Flowable<ScinfoTopBean> scinfo_top(HashMap<String, Object> body) {
        return serverApi.scinfo_top(body);
    }

    public Flowable<MoreScinfoBean> more_scinfo(HashMap<String, Object> body) {
        return serverApi.more_scinfo(body);
    }

    public Flowable<CcatesJsonBean> cates_json(HashMap<String, Object> body) {
        return serverApi.cates_json(body);
    }

    public Flowable<BaseBean> edithyoem(HashMap<String, Object> body) {
        return serverApi.edithyoem(body);
    }

    public Flowable<BaseBeancClass> gravatar(HashMap<String, Object> body) {
        return serverApi.gravatar(body);
    }

    public Flowable<WordsBean> mymessage(HashMap<String, Object> body) {
        return serverApi.mymessage(body);
    }

    public Flowable<ListCollectBean> list_collect(HashMap<String, Object> body) {
        return serverApi.list_collect(body);
    }

    public Flowable<PhoneloginBean> phonelogin(HashMap<String, Object> body) {
        return serverApi.phonelogin(body);
    }

    public Flowable<BaseBean> pwdlogin(HashMap<String, Object> body) {
        return serverApi.pwdlogin(body);
    }

    public Flowable<BaseBean> send_code(HashMap<String, Object> body) {
        return serverApi.send_code(body);
    }

    public Flowable<BaseBean> bind_mobile(HashMap<String, Object> body) {
        return serverApi.bind_mobile(body);
    }

    public Flowable<BaseBean> find_pwd(HashMap<String, Object> body) {
        return serverApi.find_pwd(body);
    }


    public Flowable<MoreProstoreBean> moreprostore(HashMap<String, Object> body) {
        return serverApi.moreprostore(body);
    }

    public Flowable<HomeGoodsBean> home_goods_more(HashMap<String, Object> body) {
        return serverApi.home_goods_more(body);
    }

    public Flowable<ListHistoryBean> list_history(HashMap<String, Object> body) {
        return serverApi.list_history(body);
    }

    public Flowable<MyRightBean> myright(HashMap<String, Object> body) {
        return serverApi.myright(body);
    }

    public Flowable<MessageListBean> message_list(HashMap<String, Object> body) {
        return serverApi.message_list(body);
    }

    public Flowable<BaseBean> ajax_logout(HashMap<String, Object> body) {
        return serverApi.ajax_logout(body);
    }

    public Flowable<BaseBean> drop_history(HashMap<String, Object> body) {
        return serverApi.drop_history(body);
    }

}
