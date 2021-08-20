package com.zhangying.oem1688.bean;

import java.util.List;

public class ScinfoDetailBean {


    /**
     * done : true
     * msg :
     * retval : {"id":"770","content":"南宫市净柔日用品厂成立于2019年10月，是一家专注于日用棉品设计、研发及销售的企业。公司自成立以来，以净柔洁面巾为主体多条产品线集中生产，以诚信赢天下，以质量赢客户的核心经营理念获得业界的认可。 本厂生产的洁面巾均以全黏胶水刺无纺布为原料，确保产品安全，并且支持OEM 可贴牌代","compname":"南宫市净柔日用品厂","name":"李金苍","phone":"18603191397","add_time":"2021-07-08 04:07:13","images":[{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_9421.jpg","w":1920,"h":1080},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_1248.jpg","w":800,"h":800},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_6815.jpg","w":1080,"h":1920},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_5420.jpg","w":540,"h":960}],"store_id":"0","portrait":"http://m.oem1688.cn/assets/images/liuyan_tx.jpg","stel":"186****1397","title":"南宫市净柔日用品厂成立于..."}
     */

    private boolean done;
    private String msg;
    /**
     * id : 770
     * content : 南宫市净柔日用品厂成立于2019年10月，是一家专注于日用棉品设计、研发及销售的企业。公司自成立以来，以净柔洁面巾为主体多条产品线集中生产，以诚信赢天下，以质量赢客户的核心经营理念获得业界的认可。 本厂生产的洁面巾均以全黏胶水刺无纺布为原料，确保产品安全，并且支持OEM 可贴牌代
     * compname : 南宫市净柔日用品厂
     * name : 李金苍
     * phone : 18603191397
     * add_time : 2021-07-08 04:07:13
     * images : [{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_9421.jpg","w":1920,"h":1080},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_1248.jpg","w":800,"h":800},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_6815.jpg","w":1080,"h":1920},{"url":"http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_5420.jpg","w":540,"h":960}]
     * store_id : 0
     * portrait : http://m.oem1688.cn/assets/images/liuyan_tx.jpg
     * stel : 186****1397
     * title : 南宫市净柔日用品厂成立于...
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
        private String id;
        private String content;
        private String compname;
        private String name;
        private String phone;
        private String add_time;
        private String store_id;
        private String portrait;
        private String stel;
        private String title;
        /**
         * url : http://m.oem1688.cn/data/files/applet/2021/070812/20210708120113_9421.jpg
         * w : 1920
         * h : 1080
         */

        private List<ImagesBean> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCompname() {
            return compname;
        }

        public void setCompname(String compname) {
            this.compname = compname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getStel() {
            return stel;
        }

        public void setStel(String stel) {
            this.stel = stel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            private String url;
            private int w;
            private int h;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }
        }
    }
}
