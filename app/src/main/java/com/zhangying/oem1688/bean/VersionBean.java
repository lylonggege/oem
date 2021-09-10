package com.zhangying.oem1688.bean;

public class VersionBean {

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
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        private String version;

        public String getUrl() {return url; }
        public void setUrl(String url) { this.url = url; }
        private String url;

    }
}
