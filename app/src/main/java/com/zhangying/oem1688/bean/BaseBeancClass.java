package com.zhangying.oem1688.bean;

public class BaseBeancClass {
    private boolean done;
    private String msg;

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

    public retvalBean getRetval() {
        return retval;
    }

    public void setRetval(retvalBean retval) {
        this.retval = retval;
    }

    private retvalBean retval;
    public class retvalBean{
        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        private String path;
    }
}
