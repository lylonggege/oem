package com.zhangying.oem1688.bean;

import java.util.List;

public class MessagePrivBean {
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
        public ViewLimitBean viewquantitys;
        public ViewLimitBean getViewquantitys() { return viewquantitys; }
        public void setViewquantitys(ViewLimitBean viewquantitys) { this.viewquantitys = viewquantitys; }

        private PageInfoBean pageinfo;
        public PageInfoBean getPageinfo() { return pageinfo; }
        public void setPageinfo(PageInfoBean pageinfo) { this.pageinfo = pageinfo; }

        public static class PageInfoBean
        {
            private Boolean isexpire;
            private String errorinfo;
            private List<QueryCateBean> gcatelist;
            private String headtitle;

            public String getHeadtitle() { return headtitle; }
            public void setHeadtitle(String headtitle) { this.headtitle = headtitle; }

            public List<QueryCateBean> getGcatelist() { return gcatelist; }
            public void setGcatelist(List<QueryCateBean> gcatelist) { this.gcatelist = gcatelist; }

            public Boolean getIsexpire() { return isexpire; }
            public void setIsexpire(Boolean isexpire) { this.isexpire = isexpire; }

            public String getErrorinfo() { return errorinfo; }
            public void setErrorinfo(String errorinfo) { this.errorinfo = errorinfo; }
        }

        public static class ViewLimitBean {
            public String getDaynums() {
                return daynums;
            }

            public void setDaynums(String daynums) {
                this.daynums = daynums;
            }

            public String getAmnums() {
                return amnums;
            }

            public void setAmnums(String amnums) {
                this.amnums = amnums;
            }

            public String getPmnums() {
                return pmnums;
            }

            public void setPmnums(String pmnums) {
                this.pmnums = pmnums;
            }

            public String getKknums() {
                return kknums;
            }

            public void setKknums(String kknums) {
                this.kknums = kknums;
            }

            public String getYknums() {
                return yknums;
            }

            public void setYknums(String yknums) {
                this.yknums = yknums;
            }

            public String getWknums() {
                return wknums;
            }

            public void setWknums(String wknums) {
                this.wknums = wknums;
            }

            private String daynums;
            private String amnums;
            private String pmnums;
            private String kknums;
            private String yknums;
            private String wknums;
        }

        public static class QueryCateBean {
            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }

            private String cateid;
            private String catename;
        }
    }
}
