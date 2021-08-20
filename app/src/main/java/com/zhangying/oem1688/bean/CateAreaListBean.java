package com.zhangying.oem1688.bean;

import java.util.List;

public class CateAreaListBean {

    /**
     * done : true
     * msg :
     * retval : {"cates":{},"def_cateid":2,"areas":[],"schannel":{"qdtitel":"你的渠道？","qdlist":[{"id":1,"sname":"品牌商"},{"id":2,"sname":"渠道商"},{"id":3,"sname":"供应链"},{"id":4,"sname":"电商"},{"id":6,"sname":"社区团购"},{"id":5,"sname":"微商"},{"id":7,"sname":"直播带货"},{"id":8,"sname":"直销会销"}]},"sytitle":"帮我找工厂-代工帮","pageinfo":{"shareTitle":"帮我找工厂-代工帮","shareCont":"","shareCover":"","shareCoverTof":"","shareTemplateId":""}}
     */

    private boolean done;
    private String msg;
    /**
     * cates : {}
     * def_cateid : 2
     * areas : []
     * schannel : {"qdtitel":"你的渠道？","qdlist":[{"id":1,"sname":"品牌商"},{"id":2,"sname":"渠道商"},{"id":3,"sname":"供应链"},{"id":4,"sname":"电商"},{"id":6,"sname":"社区团购"},{"id":5,"sname":"微商"},{"id":7,"sname":"直播带货"},{"id":8,"sname":"直销会销"}]}
     * sytitle : 帮我找工厂-代工帮
     * pageinfo : {"shareTitle":"帮我找工厂-代工帮","shareCont":"","shareCover":"","shareCoverTof":"","shareTemplateId":""}
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
        private int def_cateid;
        /**
         * qdtitel : 你的渠道？
         * qdlist : [{"id":1,"sname":"品牌商"},{"id":2,"sname":"渠道商"},{"id":3,"sname":"供应链"},{"id":4,"sname":"电商"},{"id":6,"sname":"社区团购"},{"id":5,"sname":"微商"},{"id":7,"sname":"直播带货"},{"id":8,"sname":"直销会销"}]
         */

        private SchannelBean schannel;
        private String sytitle;
        /**
         * shareTitle : 帮我找工厂-代工帮
         * shareCont :
         * shareCover :
         * shareCoverTof :
         * shareTemplateId :
         */

        private PageinfoBean pageinfo;
        private List<CompanyFactoryBean.RetvalBean.OemareaBean> areas;


        public List<catesBean> getCates() {
            return cates;
        }

        public void setCates(List<catesBean> cates) {
            this.cates = cates;
        }

        private List<catesBean> cates;

        public int getDef_cateid() {
            return def_cateid;
        }

        public void setDef_cateid(int def_cateid) {
            this.def_cateid = def_cateid;
        }

        public SchannelBean getSchannel() {
            return schannel;
        }

        public void setSchannel(SchannelBean schannel) {
            this.schannel = schannel;
        }

        public String getSytitle() {
            return sytitle;
        }

        public void setSytitle(String sytitle) {
            this.sytitle = sytitle;
        }

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public List<CompanyFactoryBean.RetvalBean.OemareaBean> getAreas() {
            return areas;
        }

        public void setAreas(List<CompanyFactoryBean.RetvalBean.OemareaBean> areas) {
            this.areas = areas;
        }

        public static class SchannelBean {
            private String qdtitel;
            /**
             * id : 1
             * sname : 品牌商
             */

            private List<QdlistBean> qdlist;

            public String getQdtitel() {
                return qdtitel;
            }

            public void setQdtitel(String qdtitel) {
                this.qdtitel = qdtitel;
            }

            public List<QdlistBean> getQdlist() {
                return qdlist;
            }

            public void setQdlist(List<QdlistBean> qdlist) {
                this.qdlist = qdlist;
            }

            public static class QdlistBean {
                private int id;
                private Boolean isBoolean;

                public Boolean getBoolean() {
                    return isBoolean;
                }

                public void setBoolean(Boolean aBoolean) {
                    isBoolean = aBoolean;
                }

                private String sname;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }
            }
        }

        public static class PageinfoBean {
            private String shareTitle;
            private String shareCont;
            private String shareCover;
            private String shareCoverTof;
            private String shareTemplateId;

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public String getShareCont() {
                return shareCont;
            }

            public void setShareCont(String shareCont) {
                this.shareCont = shareCont;
            }

            public String getShareCover() {
                return shareCover;
            }

            public void setShareCover(String shareCover) {
                this.shareCover = shareCover;
            }

            public String getShareCoverTof() {
                return shareCoverTof;
            }

            public void setShareCoverTof(String shareCoverTof) {
                this.shareCoverTof = shareCoverTof;
            }

            public String getShareTemplateId() {
                return shareTemplateId;
            }

            public void setShareTemplateId(String shareTemplateId) {
                this.shareTemplateId = shareTemplateId;
            }
        }
    }

    public static class catesBean {
        private String id;
        private String name;
        private int uid;

        public boolean isBoolean() {
            return isBoolean;
        }

        public void setBoolean(boolean aBoolean) {
            isBoolean = aBoolean;
        }

        private boolean isBoolean;
        private List<childrenBan> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public List<childrenBan> getChildren() {
            return children;
        }

        public void setChildren(List<childrenBan> children) {
            this.children = children;
        }

        public static class childrenBan {

            public boolean isBoolean() {
                return isBoolean;
            }

            public void setBoolean(boolean aBoolean) {
                isBoolean = aBoolean;
            }

            private boolean isBoolean;
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            private String name;
            private int uid;
        }
    }


}
