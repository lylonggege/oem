package com.zhangying.oem1688.bean;

import java.util.List;

public class ScinfoTopBean {

    /**
     * done : true
     * msg :
     * retval : {"pageinfo":{"shareTitle":"代加工信息发布,代工贴牌信息需求发布中心-代工帮","shareCont":"","shareCover":"","shareCoverTof":"","shareTemplateId":""},"banners":[{"ad_id":"38","ad_link":"http://m.oem1688.cn//index.php","ad_logo":"http://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""},{"ad_id":"39","ad_link":"http://m.oem1688.cn//index.php","ad_logo":"http://m.oem1688.cn/data/files/mall/ad/20210422/logo_39_1619085906.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""}],"cates":[{"cateid":"0","catename":"全部"},{"cateid":2,"catename":"大健康"},{"cateid":1,"catename":"化妆品"},{"cateid":5,"catename":"孕婴童"},{"cateid":3,"catename":"快消品"},{"cateid":4,"catename":"酒水"},{"cateid":1203,"catename":"调味品"},{"cateid":25743,"catename":"宠品"},{"cateid":54844,"catename":"农资"}],"def_cateid":0,"enbrands":0,"def_type":2,"sytitle":"工厂承接代加工-代工帮"}
     */

    private boolean done;
    private String msg;
    /**
     * pageinfo : {"shareTitle":"代加工信息发布,代工贴牌信息需求发布中心-代工帮","shareCont":"","shareCover":"","shareCoverTof":"","shareTemplateId":""}
     * banners : [{"ad_id":"38","ad_link":"http://m.oem1688.cn//index.php","ad_logo":"http://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""},{"ad_id":"39","ad_link":"http://m.oem1688.cn//index.php","ad_logo":"http://m.oem1688.cn/data/files/mall/ad/20210422/logo_39_1619085906.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""}]
     * cates : [{"cateid":"0","catename":"全部"},{"cateid":2,"catename":"大健康"},{"cateid":1,"catename":"化妆品"},{"cateid":5,"catename":"孕婴童"},{"cateid":3,"catename":"快消品"},{"cateid":4,"catename":"酒水"},{"cateid":1203,"catename":"调味品"},{"cateid":25743,"catename":"宠品"},{"cateid":54844,"catename":"农资"}]
     * def_cateid : 0
     * enbrands : 0
     * def_type : 2
     * sytitle : 工厂承接代加工-代工帮
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
         * shareTitle : 代加工信息发布,代工贴牌信息需求发布中心-代工帮
         * shareCont :
         * shareCover :
         * shareCoverTof :
         * shareTemplateId :
         */

        private PageinfoBean pageinfo;
        private int def_cateid;
        private int enbrands;
        private int def_type;
        private String sytitle;
        /**
         * ad_id : 38
         * ad_link : http://m.oem1688.cn//index.php
         * ad_logo : http://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg
         * ad_name : 代工帮 贴牌代工大平台
         * ad_type : 1
         * link_type : 0
         * link_id : 0
         * ad_url :
         */

        private List<BannersBean> banners;
        /**
         * cateid : 0
         * catename : 全部
         */

        private List<CatesBean> cates;

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public int getDef_cateid() {
            return def_cateid;
        }

        public void setDef_cateid(int def_cateid) {
            this.def_cateid = def_cateid;
        }

        public int getEnbrands() {
            return enbrands;
        }

        public void setEnbrands(int enbrands) {
            this.enbrands = enbrands;
        }

        public int getDef_type() {
            return def_type;
        }

        public void setDef_type(int def_type) {
            this.def_type = def_type;
        }

        public String getSytitle() {
            return sytitle;
        }

        public void setSytitle(String sytitle) {
            this.sytitle = sytitle;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<CatesBean> getCates() {
            return cates;
        }

        public void setCates(List<CatesBean> cates) {
            this.cates = cates;
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

        public static class BannersBean {
            private String ad_id;
            private String ad_link;
            private String ad_logo;
            private String ad_name;
            private String ad_type;
            private int link_type;
            private int link_id;
            private String ad_url;

            public String getAd_id() {
                return ad_id;
            }

            public void setAd_id(String ad_id) {
                this.ad_id = ad_id;
            }

            public String getAd_link() {
                return ad_link;
            }

            public void setAd_link(String ad_link) {
                this.ad_link = ad_link;
            }

            public String getAd_logo() {
                return ad_logo;
            }

            public void setAd_logo(String ad_logo) {
                this.ad_logo = ad_logo;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public String getAd_type() {
                return ad_type;
            }

            public void setAd_type(String ad_type) {
                this.ad_type = ad_type;
            }

            public int getLink_type() {
                return link_type;
            }

            public void setLink_type(int link_type) {
                this.link_type = link_type;
            }

            public int getLink_id() {
                return link_id;
            }

            public void setLink_id(int link_id) {
                this.link_id = link_id;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }
        }

        public static class CatesBean {
            private String cateid;
            private String catename;

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
        }
    }
}
