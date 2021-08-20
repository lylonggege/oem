package com.zhangying.oem1688.bean;

import java.util.List;

public class ListCollectBean {

    /**
     * done : true
     * msg :
     * retval : {"recordList":[{"store_id":"10065","store_name":"福建全家福食品有限公司","service":"婴幼儿休闲零食","store_logo":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_166/small_202105221036062361.jpg","state":1},{"store_id":"10385","store_name":"芜湖市雨子坊食品有限公司","service":"其他冲调饮品 麦片","store_logo":"https://m.oem1688.cn/data/files/mall/store/logo/2021/05/24/store_10385.png","state":1}],"totalPages":0}
     */

    private boolean done;
    private String msg;
    /**
     * recordList : [{"store_id":"10065","store_name":"福建全家福食品有限公司","service":"婴幼儿休闲零食","store_logo":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_166/small_202105221036062361.jpg","state":1},{"store_id":"10385","store_name":"芜湖市雨子坊食品有限公司","service":"其他冲调饮品 麦片","store_logo":"https://m.oem1688.cn/data/files/mall/store/logo/2021/05/24/store_10385.png","state":1}]
     * totalPages : 0
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
        private int totalPages;
        /**
         * store_id : 10065
         * store_name : 福建全家福食品有限公司
         * service : 婴幼儿休闲零食
         * store_logo : https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_166/small_202105221036062361.jpg
         * state : 1
         */

        private List<RecordListBean> recordList;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<RecordListBean> recordList) {
            this.recordList = recordList;
        }

        public static class RecordListBean {
            private String store_id;
            private String store_name;
            private String service;
            private String store_logo;
            private int state;

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getStore_logo() {
                return store_logo;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}
