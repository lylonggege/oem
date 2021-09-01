package com.zhangying.oem1688.bean;

import java.util.List;

public class WordsBean {

    /**
     * done : true
     * msg :
     * retval : {"pageinfo":{"headtitle":"我的留言","foothide":true,"footnav":6},"mineagent":{"pageCount":10,"showLoading":false,"goods":[]}}
     */

    private boolean done;
    private String msg;
    /**
     * pageinfo : {"headtitle":"我的留言","foothide":true,"footnav":6}
     * mineagent : {"pageCount":10,"showLoading":false,"goods":[]}
     */

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
        /**
         * headtitle : 我的留言
         * foothide : true
         * footnav : 6
         */

        private PageinfoBean pageinfo;
        /**
         * pageCount : 10
         * showLoading : false
         * goods : []
         */

        private MineagentBean mineagent;

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public MineagentBean getMineagent() {
            return mineagent;
        }

        public void setMineagent(MineagentBean mineagent) {
            this.mineagent = mineagent;
        }

        public static class PageinfoBean {
            private String headtitle;
            private boolean foothide;
            private int footnav;

            public String getHeadtitle() {
                return headtitle;
            }

            public void setHeadtitle(String headtitle) {
                this.headtitle = headtitle;
            }

            public boolean isFoothide() {
                return foothide;
            }

            public void setFoothide(boolean foothide) {
                this.foothide = foothide;
            }

            public int getFootnav() {
                return footnav;
            }

            public void setFootnav(int footnav) {
                this.footnav = footnav;
            }
        }

        public static class MineagentBean {
            private int pageCount;
            private boolean showLoading;
            private List<GoodsBena> goods;

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public boolean isShowLoading() {
                return showLoading;
            }

            public void setShowLoading(boolean showLoading) {
                this.showLoading = showLoading;
            }

            public List<GoodsBena> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBena> goods) {
                this.goods = goods;
            }
        }
    }

    public static class GoodsBena {
        private String stitle;
        private String addtime;
        private String icheck;  //审核状态(0：未审、1：已审)

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getIcheck() {
            return icheck;
        }

        public void setIcheck(String icheck) {
            this.icheck = icheck;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSmobile() {
            return smobile;
        }

        public void setSmobile(String smobile) {
            this.smobile = smobile;
        }

        public String getSarea() {
            return sarea;
        }

        public void setSarea(String sarea) {
            this.sarea = sarea;
        }

        public String getSiid() {
            return siid;
        }

        public void setSiid(String siid) {
            this.siid = siid;
        }

        private String sname;
        private String smobile;
        private String sarea;
        private String siid;

    }
}
