package com.zhangying.oem1688.bean;

import java.util.List;

public class ListHistoryBean {

    /**
     * done : true
     * msg :
     * retval : {"recordList":[{"item_id":"10065","item_name":"福建全家福食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_64/small_202105221041041715.jpg","add_time":"2021-05-24","hid":"8790","checked":0},{"item_id":"10385","item_name":"芜湖市雨子坊食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/24/goods_121/202105240958414057.jpg","add_time":"2021-05-24","hid":"8789","checked":0},{"item_id":"2969","item_name":"山东中舜雅康生物科技有限公司","item_cover":"https://m.oem1688.cn/data/system/default_goods_image.gif","add_time":"2021-05-22","hid":"6542","checked":0},{"item_id":"3998","item_name":"广东燕岭生命科技股份有限公司","item_cover":"https://m.oem1688.cn/data/system/default_goods_image.gif","add_time":"2021-05-22","hid":"6541","checked":0},{"item_id":"9933","item_name":"纽伊儿（上海）食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/21/goods_36/small_202105211053563574.jpg","add_time":"2021-05-22","hid":"6508","checked":0},{"item_id":"9938","item_name":"杭州索契科技有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/20/goods_175/small_202105201752559878.jpg","add_time":"2021-05-22","hid":"6507","checked":0},{"item_id":"6313","item_name":"青州市顺丰食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/27/goods_131/small_202104271402116397.jpg","add_time":"2021-05-22","hid":"6506","checked":0},{"item_id":"358","item_name":"江西三诚实业有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/01/goods_131/202104010852117716.jpg","add_time":"2021-05-21","hid":"6480","checked":0},{"item_id":"6194","item_name":"江西众仁食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/26/goods_182/202104261736223382.jpg","add_time":"2021-05-21","hid":"6479","checked":0},{"item_id":"6405","item_name":"江西诺泰生物科技有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/10/goods_163/small_202105101502433892.jpg","add_time":"2021-05-21","hid":"6478","checked":0}],"totalPages":2,"totalCount":"20"}
     */

    private boolean done;
    private String msg;
    /**
     * recordList : [{"item_id":"10065","item_name":"福建全家福食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_64/small_202105221041041715.jpg","add_time":"2021-05-24","hid":"8790","checked":0},{"item_id":"10385","item_name":"芜湖市雨子坊食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/24/goods_121/202105240958414057.jpg","add_time":"2021-05-24","hid":"8789","checked":0},{"item_id":"2969","item_name":"山东中舜雅康生物科技有限公司","item_cover":"https://m.oem1688.cn/data/system/default_goods_image.gif","add_time":"2021-05-22","hid":"6542","checked":0},{"item_id":"3998","item_name":"广东燕岭生命科技股份有限公司","item_cover":"https://m.oem1688.cn/data/system/default_goods_image.gif","add_time":"2021-05-22","hid":"6541","checked":0},{"item_id":"9933","item_name":"纽伊儿（上海）食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/21/goods_36/small_202105211053563574.jpg","add_time":"2021-05-22","hid":"6508","checked":0},{"item_id":"9938","item_name":"杭州索契科技有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/20/goods_175/small_202105201752559878.jpg","add_time":"2021-05-22","hid":"6507","checked":0},{"item_id":"6313","item_name":"青州市顺丰食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/27/goods_131/small_202104271402116397.jpg","add_time":"2021-05-22","hid":"6506","checked":0},{"item_id":"358","item_name":"江西三诚实业有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/01/goods_131/202104010852117716.jpg","add_time":"2021-05-21","hid":"6480","checked":0},{"item_id":"6194","item_name":"江西众仁食品有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/04/26/goods_182/202104261736223382.jpg","add_time":"2021-05-21","hid":"6479","checked":0},{"item_id":"6405","item_name":"江西诺泰生物科技有限公司","item_cover":"https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/10/goods_163/small_202105101502433892.jpg","add_time":"2021-05-21","hid":"6478","checked":0}]
     * totalPages : 2
     * totalCount : 20
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
        private String totalCount;
        /**
         * item_id : 10065
         * item_name : 福建全家福食品有限公司
         * item_cover : https://m.oem1688.cn/data/files/mall/goods/goods_image/2021/05/22/goods_64/small_202105221041041715.jpg
         * add_time : 2021-05-24
         * hid : 8790
         * checked : 0
         */

        private List<RecordListBean> recordList;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public List<RecordListBean> getRecordList() {
            return recordList;
        }

        public void setRecordList(List<RecordListBean> recordList) {
            this.recordList = recordList;
        }

        public static class RecordListBean {
            private String item_id;
            private String item_name;
            private String item_cover;
            private String add_time;
            private String hid;
            private int checked;


            public boolean isIschecked() {
                return ischecked;
            }

            public void setIschecked(boolean ischecked) {
                this.ischecked = ischecked;
            }

            private boolean ischecked;

            public boolean isIschecked_bg() {
                return ischecked_bg;
            }

            public void setIschecked_bg(boolean ischecked_bg) {
                this.ischecked_bg = ischecked_bg;
            }

            private boolean ischecked_bg;

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_cover() {
                return item_cover;
            }

            public void setItem_cover(String item_cover) {
                this.item_cover = item_cover;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getHid() {
                return hid;
            }

            public void setHid(String hid) {
                this.hid = hid;
            }

            public int getChecked() {
                return checked;
            }

            public void setChecked(int checked) {
                this.checked = checked;
            }
        }
    }
}
