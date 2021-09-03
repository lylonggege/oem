package com.zhangying.oem1688.bean;

public class PrivacyBean {
    private boolean done;
    private String msg;
    private RetvalBean retval;

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

    public RetvalBean getRetval() {
        return retval;
    }
    public void setRetval(RetvalBean retval) {
        this.retval = retval;
    }

    public static class RetvalBean {
        private String info;

        public String getInfo() { return info; }
        public void setInfo(String info) { this.info = info; }
    }
}
