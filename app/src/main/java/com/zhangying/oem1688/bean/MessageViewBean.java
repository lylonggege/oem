package com.zhangying.oem1688.bean;

public class MessageViewBean {
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
        private String s_yixiang;
        private String s_name;
        private String s_areatype;
        private String s_identity;
        private String s_mobile;

        public String getS_yixiang() {
            return s_yixiang;
        }

        public void setS_yixiang(String s_yixiang) {
            this.s_yixiang = s_yixiang;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getS_areatype() {
            return s_areatype;
        }

        public void setS_areatype(String s_areatype) {
            this.s_areatype = s_areatype;
        }

        public String getS_identity() {
            return s_identity;
        }

        public void setS_identity(String s_identity) {
            this.s_identity = s_identity;
        }

        public String getS_mobile() {
            return s_mobile;
        }

        public void setS_mobile(String s_mobile) {
            this.s_mobile = s_mobile;
        }
    }
}
