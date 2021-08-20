package com.zhangying.oem1688.bean;

public class MemberInfoBean {

    /**
     * done : true
     * msg :
     * retval : {"user_id":5611,"user_name":"OEM2021042515084996","nickname":"龙","real_name":"李彦龙","shop_name":"","store_id":0,"phone_mob":"15038382020","user_grade":0,"user_correlation_id":0,"user_has_store":0,"user_endtime":"","portrait":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqKBP4UXlpDR5aVkKW4lTem9sRg3WZeExgnAuIdz3y5e72j3ZvWl9kfUqZicpuHRYgGJvna9OoZKA/132","curportrait":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqKBP4UXlpDR5aVkKW4lTem9sRg3WZeExgnAuIdz3y5e72j3ZvWl9kfUqZicpuHRYgGJvna9OoZKA/132","user_weixin":"","phonesq":false}
     */

    private boolean done;
    private String msg;
    /**
     * user_id : 5611
     * user_name : OEM2021042515084996
     * nickname : 龙
     * real_name : 李彦龙
     * shop_name :
     * store_id : 0
     * phone_mob : 15038382020
     * user_grade : 0
     * user_correlation_id : 0
     * user_has_store : 0
     * user_endtime :
     * portrait : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqKBP4UXlpDR5aVkKW4lTem9sRg3WZeExgnAuIdz3y5e72j3ZvWl9kfUqZicpuHRYgGJvna9OoZKA/132
     * curportrait : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKqKBP4UXlpDR5aVkKW4lTem9sRg3WZeExgnAuIdz3y5e72j3ZvWl9kfUqZicpuHRYgGJvna9OoZKA/132
     * user_weixin :
     * phonesq : false
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
        private boolean phonesq;

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

        public boolean isPhonesq() {
            return phonesq;
        }

        public void setPhonesq(boolean phonesq) {
            this.phonesq = phonesq;
        }
    }
}
