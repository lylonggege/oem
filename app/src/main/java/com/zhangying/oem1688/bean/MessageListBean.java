package com.zhangying.oem1688.bean;

import java.util.List;

public class MessageListBean {

    /**
     * done : true
     * msg :
     * retval : [{"agent_id":621,"store_id":1060,"s_name":"韦林宇","s_areatype":"广东广州","add_time":"2021-07-17","s_view_ys":"red","s_view_txt":"未读","m_store_id":4007,"s_view_nums":0,"s_view_numtxt":"次"},{"agent_id":620,"store_id":50,"s_name":"张海军","s_areatype":"内蒙古呼伦贝尔","add_time":"2021-07-17","s_view_ys":"red","s_view_txt":"未读","m_store_id":4007,"s_view_nums":0,"s_view_numtxt":"次"}]
     */

    private boolean done;
    private String msg;
    /**
     * agent_id : 621
     * store_id : 1060
     * s_name : 韦林宇
     * s_areatype : 广东广州
     * add_time : 2021-07-17
     * s_view_ys : red
     * s_view_txt : 未读
     * m_store_id : 4007
     * s_view_nums : 0
     * s_view_numtxt : 次
     */

    private List<RetvalBean> retval;

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

    public List<RetvalBean> getRetval() {
        return retval;
    }

    public void setRetval(List<RetvalBean> retval) {
        this.retval = retval;
    }

    public static class RetvalBean {
        private int agent_id;
        private int store_id;
        private String s_name;
        private String s_areatype;
        private String add_time;
        private String s_view_ys;
        private String s_view_txt;
        private int m_store_id;
        private int s_view_nums;
        private String s_view_numtxt;

        public int getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(int agent_id) {
            this.agent_id = agent_id;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getS_areatype() {
            return s_areatype;
        }

        public void setS_areatype(String s_areatype) {
            this.s_areatype = s_areatype;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getS_view_ys() {
            return s_view_ys;
        }

        public void setS_view_ys(String s_view_ys) {
            this.s_view_ys = s_view_ys;
        }

        public String getS_view_txt() {
            return s_view_txt;
        }

        public void setS_view_txt(String s_view_txt) {
            this.s_view_txt = s_view_txt;
        }

        public int getM_store_id() {
            return m_store_id;
        }

        public void setM_store_id(int m_store_id) {
            this.m_store_id = m_store_id;
        }

        public int getS_view_nums() {
            return s_view_nums;
        }

        public void setS_view_nums(int s_view_nums) {
            this.s_view_nums = s_view_nums;
        }

        public String getS_view_numtxt() {
            return s_view_numtxt;
        }

        public void setS_view_numtxt(String s_view_numtxt) {
            this.s_view_numtxt = s_view_numtxt;
        }
    }
}
