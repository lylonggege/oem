package com.zhangying.oem1688.bean;

import java.util.List;

public class CatesBean {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<GlistBean> getGlist() {
        return glist;
    }

    public void setGlist(List<GlistBean> glist) {
        this.glist = glist;
    }

    private String value;
    private List<GlistBean> glist;

    public class GlistBean{
        public String getDefault_image() {
            return default_image;
        }

        public void setDefault_image(String default_image) {
            this.default_image = default_image;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        private String default_image;
    private String goods_id;
    }
}
