package com.zhangying.oem1688.bean;

import java.util.List;

public class MineinfoBean {

    /**
     * done : true
     * msg :
     * retval : {"pageinfo":{"headtitle":"个人中心","foothide":true,"footnav":6},"mineinfo":{"user_id":0,"user_name":null,"nickname":null,"real_name":null,"shop_name":null,"store_id":0,"phone_mob":null,"user_grade":0,"user_correlation_id":0,"user_has_store":0,"user_endtime":"","portrait":"/images/liuyan_tx.jpg","curportrait":"/images/liuyan_tx.jpg","user_weixin":null,"tongbuflag":true,"itemlist":[{"spic":"/images/user_personal.png","sname":"个人信息","surl":"../personalinfo/personalinfo"},{"spic":"/images/user_zuji.png","sname":"浏览足迹","surl":"../footprint/footprint"},{"spic":"/images/user_kefu.png","sname":"在线客服","surl":"../kefu/kefu"},{"spic":"/images/user_about.png","sname":"关于代工帮","surl":"../about/about"}],"itemfactory":[{"spic":"/images/factoryCenter1.jpg","surl":""},{"spic":"/images/factoryCenter2.jpg","surl":""}],"myfollows":[{"nums":"0","spic":"","sname":"收藏店铺","surl":"../followedshops/followedshops"},{"nums":"20","spic":"","sname":"留言咨询","surl":"../mymessage/mymessage"}]}}
     */

    private boolean done;
    private String msg;
    /**
     * pageinfo : {"headtitle":"个人中心","foothide":true,"footnav":6}
     * mineinfo : {"user_id":0,"user_name":null,"nickname":null,"real_name":null,"shop_name":null,"store_id":0,"phone_mob":null,"user_grade":0,"user_correlation_id":0,"user_has_store":0,"user_endtime":"","portrait":"/images/liuyan_tx.jpg","curportrait":"/images/liuyan_tx.jpg","user_weixin":null,"tongbuflag":true,"itemlist":[{"spic":"/images/user_personal.png","sname":"个人信息","surl":"../personalinfo/personalinfo"},{"spic":"/images/user_zuji.png","sname":"浏览足迹","surl":"../footprint/footprint"},{"spic":"/images/user_kefu.png","sname":"在线客服","surl":"../kefu/kefu"},{"spic":"/images/user_about.png","sname":"关于代工帮","surl":"../about/about"}],"itemfactory":[{"spic":"/images/factoryCenter1.jpg","surl":""},{"spic":"/images/factoryCenter2.jpg","surl":""}],"myfollows":[{"nums":"0","spic":"","sname":"收藏店铺","surl":"../followedshops/followedshops"},{"nums":"20","spic":"","sname":"留言咨询","surl":"../mymessage/mymessage"}]}
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
         * headtitle : 个人中心
         * foothide : true
         * footnav : 6
         */

        private PageinfoBean pageinfo;
        /**
         * user_id : 0
         * user_name : null
         * nickname : null
         * real_name : null
         * shop_name : null
         * store_id : 0
         * phone_mob : null
         * user_grade : 0
         * user_correlation_id : 0
         * user_has_store : 0
         * user_endtime :
         * portrait : /images/liuyan_tx.jpg
         * curportrait : /images/liuyan_tx.jpg
         * user_weixin : null
         * tongbuflag : true
         * itemlist : [{"spic":"/images/user_personal.png","sname":"个人信息","surl":"../personalinfo/personalinfo"},{"spic":"/images/user_zuji.png","sname":"浏览足迹","surl":"../footprint/footprint"},{"spic":"/images/user_kefu.png","sname":"在线客服","surl":"../kefu/kefu"},{"spic":"/images/user_about.png","sname":"关于代工帮","surl":"../about/about"}]
         * itemfactory : [{"spic":"/images/factoryCenter1.jpg","surl":""},{"spic":"/images/factoryCenter2.jpg","surl":""}]
         * myfollows : [{"nums":"0","spic":"","sname":"收藏店铺","surl":"../followedshops/followedshops"},{"nums":"20","spic":"","sname":"留言咨询","surl":"../mymessage/mymessage"}]
         */

        private MineinfoBean2 mineinfo;

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public MineinfoBean2 getMineinfo() {
            return mineinfo;
        }

        public void setMineinfo(MineinfoBean2 mineinfo) {
            this.mineinfo = mineinfo;
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

        public static class MineinfoBean2 {
            private int user_id;
            private String user_name;
            private String nickname;
            private String real_name;
            private String shop_name;
            private int store_id;
            private String phone_mob;
            private int user_grade;
            private int user_correlation_id;
            private int user_has_store;
            private String user_endtime;
            private String portrait;
            private String curportrait;
            private String user_weixin;
            private boolean tongbuflag;
            /**
             * spic : /images/user_personal.png
             * sname : 个人信息
             * surl : ../personalinfo/personalinfo
             */

            private List<ItemlistBean> itemlist;
            /**
             * spic : /images/factoryCenter1.jpg
             * surl :
             */

            private List<ItemfactoryBean> itemfactory;
            /**
             * nums : 0
             * spic :
             * sname : 收藏店铺
             * surl : ../followedshops/followedshops
             */

            private List<MyfollowsBean> myfollows;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getPhone_mob() {
                return phone_mob;
            }

            public void setPhone_mob(String phone_mob) {
                this.phone_mob = phone_mob;
            }

            public int getUser_grade() {
                return user_grade;
            }

            public void setUser_grade(int user_grade) {
                this.user_grade = user_grade;
            }

            public int getUser_correlation_id() {
                return user_correlation_id;
            }

            public void setUser_correlation_id(int user_correlation_id) {
                this.user_correlation_id = user_correlation_id;
            }

            public int getUser_has_store() {
                return user_has_store;
            }

            public void setUser_has_store(int user_has_store) {
                this.user_has_store = user_has_store;
            }

            public String getUser_endtime() {
                return user_endtime;
            }

            public void setUser_endtime(String user_endtime) {
                this.user_endtime = user_endtime;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getCurportrait() {
                return curportrait;
            }

            public void setCurportrait(String curportrait) {
                this.curportrait = curportrait;
            }

            public String getUser_weixin() {
                return user_weixin;
            }

            public void setUser_weixin(String user_weixin) {
                this.user_weixin = user_weixin;
            }

            public boolean isTongbuflag() {
                return tongbuflag;
            }

            public void setTongbuflag(boolean tongbuflag) {
                this.tongbuflag = tongbuflag;
            }

            public List<ItemlistBean> getItemlist() {
                return itemlist;
            }

            public void setItemlist(List<ItemlistBean> itemlist) {
                this.itemlist = itemlist;
            }

            public List<ItemfactoryBean> getItemfactory() {
                return itemfactory;
            }

            public void setItemfactory(List<ItemfactoryBean> itemfactory) {
                this.itemfactory = itemfactory;
            }

            public List<MyfollowsBean> getMyfollows() {
                return myfollows;
            }

            public void setMyfollows(List<MyfollowsBean> myfollows) {
                this.myfollows = myfollows;
            }

            public static class ItemlistBean {
                private String spic;
                private String sname;
                private String surl;

                public String getSpic() {
                    return spic;
                }

                public void setSpic(String spic) {
                    this.spic = spic;
                }

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getSurl() {
                    return surl;
                }

                public void setSurl(String surl) {
                    this.surl = surl;
                }
            }

            public static class ItemfactoryBean {
                private String spic;
                private String surl;

                public String getSpic() {
                    return spic;
                }

                public void setSpic(String spic) {
                    this.spic = spic;
                }

                public String getSurl() {
                    return surl;
                }

                public void setSurl(String surl) {
                    this.surl = surl;
                }
            }

            public static class MyfollowsBean {
                private String nums;
                private String spic;
                private String sname;
                private String surl;

                public String getStype() {
                    return stype;
                }

                public void setStype(String stype) {
                    this.stype = stype;
                }

                private String stype;

                public String getNums() {
                    return nums;
                }

                public void setNums(String nums) {
                    this.nums = nums;
                }

                public String getSpic() {
                    return spic;
                }

                public void setSpic(String spic) {
                    this.spic = spic;
                }

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getSurl() {
                    return surl;
                }

                public void setSurl(String surl) {
                    this.surl = surl;
                }
            }
        }
    }
}
