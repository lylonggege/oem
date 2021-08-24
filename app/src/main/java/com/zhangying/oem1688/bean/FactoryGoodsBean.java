package com.zhangying.oem1688.bean;

import java.util.List;

public class FactoryGoodsBean {
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
        public List<GoodsBean> getGoodsList() {
            return recordList;
        }

        public void setGoodsList(List<GoodsBean> goodsList) {
            this.recordList = goodsList;
        }

        private List<GoodsBean> recordList;

        public static class GoodsBean{
            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGpic() {
                return gpic;
            }

            public void setGpic(String gpic) {
                this.gpic = gpic;
            }

            private String gid;
            private String gname;
            private String gpic;
        }
    }
}
