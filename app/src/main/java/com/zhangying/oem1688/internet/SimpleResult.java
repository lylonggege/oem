package com.zhangying.oem1688.internet;

import java.io.Serializable;

public class SimpleResult implements Serializable {

    protected int statusCode;
    protected String msg;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return !(getStatusCode() == 200 || getStatusCode()==0);
    }

    public boolean isLogAgain(){
        return getStatusCode()==401||getMsg().contains("未登录")||getMsg().contains("重新登录");
    }
}
