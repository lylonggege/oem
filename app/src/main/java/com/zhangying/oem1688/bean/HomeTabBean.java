package com.zhangying.oem1688.bean;

import java.util.List;

public class HomeTabBean {

    /**
     * done : true
     * msg :
     * retval : [{"fname":"首页","fid":0,"fpic":"b_sy"},{"fname":"找工厂","fid":1,"fpic":"b_qy"},{"fname":"代工品","fid":2,"fpic":"b_cp"},{"fname":"新闻","fid":4,"fpic":"b_xw"},{"fname":"我的","fid":6,"fpic":"b_wd"}]
     */

    private boolean done;
    private String msg;
    /**
     * fname : 首页
     * fid : 0
     * fpic : b_sy
     */

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
        private String fname;
        private int fid;
        private String fpic;

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getFpic() {
            return fpic;
        }

        public void setFpic(String fpic) {
            this.fpic = fpic;
        }
    }
}
