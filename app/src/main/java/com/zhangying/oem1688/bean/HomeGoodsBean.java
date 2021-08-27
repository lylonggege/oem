package com.zhangying.oem1688.bean;

import java.util.List;

public class HomeGoodsBean {
    private boolean done;
    private String msg;
    private List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> retval;

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean>getRetval() {
        return retval;
    }
    public void setRetval(List<HomeBena.RetvalBean.SgoodsListBean.GoodsBean> retval) {
        this.retval = retval;
    }
}
