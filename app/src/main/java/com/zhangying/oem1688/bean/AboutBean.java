package com.zhangying.oem1688.bean;

import java.util.List;

public class AboutBean {


    /**
     * done : true
     * msg :
     * retval : {"pageinfo":{"headtitle":"代工帮介绍","foothide":true,"footnav":7},"sbanner":[{"ad_id":"38","ad_link":"https://m.oem1688.cn//index.php","ad_logo":"https://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""},{"ad_id":"39","ad_link":"https://m.oem1688.cn//index.php","ad_logo":"https://m.oem1688.cn/data/files/mall/ad/20210422/logo_39_1619085906.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""}],"aboutoem":"<p class='h1'>关于代工帮<\/p><p class='p'>代工帮专注于OEM,ODM,贴牌代加工领域企业服务,致力于打造代加工网络大数据平台。<\/p><p class='p'>代工帮,提供全国各行业OEM代加工贴牌信息,涵盖美妆日化、营养食品代加工,饮料酒水OEM代加工,餐饮生鲜、母婴用品代加工,服装、小商品、玩具代加工等;为所有生产加工企业和寻求代加工贴牌的客户提供一个专业的OEM代加工服务平台。<\/p>"}
     */

    private boolean done;
    private String msg;
    /**
     * pageinfo : {"headtitle":"代工帮介绍","foothide":true,"footnav":7}
     * sbanner : [{"ad_id":"38","ad_link":"https://m.oem1688.cn//index.php","ad_logo":"https://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""},{"ad_id":"39","ad_link":"https://m.oem1688.cn//index.php","ad_logo":"https://m.oem1688.cn/data/files/mall/ad/20210422/logo_39_1619085906.jpg","ad_name":"代工帮 贴牌代工大平台","ad_type":"1","link_type":0,"link_id":0,"ad_url":""}]
     * aboutoem : <p class='h1'>关于代工帮</p><p class='p'>代工帮专注于OEM,ODM,贴牌代加工领域企业服务,致力于打造代加工网络大数据平台。</p><p class='p'>代工帮,提供全国各行业OEM代加工贴牌信息,涵盖美妆日化、营养食品代加工,饮料酒水OEM代加工,餐饮生鲜、母婴用品代加工,服装、小商品、玩具代加工等;为所有生产加工企业和寻求代加工贴牌的客户提供一个专业的OEM代加工服务平台。</p>
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
         * headtitle : 代工帮介绍
         * foothide : true
         * footnav : 7
         */

        private PageinfoBean pageinfo;
        private String aboutoem;
        /**
         * ad_id : 38
         * ad_link : https://m.oem1688.cn//index.php
         * ad_logo : https://m.oem1688.cn/data/files/mall/ad/20210520/logo_38_1621478651.jpg
         * ad_name : 代工帮 贴牌代工大平台
         * ad_type : 1
         * link_type : 0
         * link_id : 0
         * ad_url :
         */

        private List<SbannerBean> sbanner;

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public String getAboutoem() {
            return aboutoem;
        }

        public void setAboutoem(String aboutoem) {
            this.aboutoem = aboutoem;
        }

        public List<SbannerBean> getSbanner() {
            return sbanner;
        }

        public void setSbanner(List<SbannerBean> sbanner) {
            this.sbanner = sbanner;
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

        public static class SbannerBean {
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
    }
}
