package com.zhangying.oem1688.bean;

import java.util.List;

public class FactoryGCateBean {
    private boolean done;
    private String msg;
    private List<RetvalBean> retval;

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

    public List<RetvalBean> getRetval() {
        return retval;
    }

    public void setRetval(List<RetvalBean> retval) {
        this.retval = retval;
    }

    public static class RetvalBean {
        private String id;
        private String value;
        private boolean isboolean;

        public boolean isIsboolean() {
            return isboolean;
        }
        public void setIsboolean(boolean isboolean) {
            this.isboolean = isboolean;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
