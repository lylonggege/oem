package com.zhangying.oem1688.bean;

public class PhoneloginBean {


    /**
     * done : true
     * msg :
     * retval : {"id":"15","user_name":"20210726123456","store_id":"20","nickname":"我的","shop_name":"河南掌婴电子商务有限公司","portrait":"","phone_mob":"15036361256","token":"1259were123ddkkkdll"}
     */

    private boolean done;
    private String msg;
    /**
     * id : 15
     * user_name : 20210726123456
     * store_id : 20
     * nickname : 我的
     * shop_name : 河南掌婴电子商务有限公司
     * portrait :
     * phone_mob : 15036361256
     * token : 1259were123ddkkkdll
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
        private String user_name;
        private String store_id;
        private String nickname;
        private String shop_name;
        private String portrait;
        private String phone_mob;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getPhone_mob() {
            return phone_mob;
        }

        public void setPhone_mob(String phone_mob) {
            this.phone_mob = phone_mob;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
