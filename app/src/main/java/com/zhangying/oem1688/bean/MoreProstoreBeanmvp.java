package com.zhangying.oem1688.bean;

public class MoreProstoreBeanmvp {
    private String ly;  //调用来源，默认app.
    private String page;  //当前页码.
    private String catebid; //大分类编号.
    private String catesid;  //小分类编号.
    private String areabid;  //省份编号.
    private String areasid;  //城市编号.
    private String kw;   //查询关键词.
    private String itype;  //类型(0：产品、1：工厂).

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getCatebid() {
        return catebid == null ? "0" : catebid;
    }

    public void setCatebid(String catebid) {
        this.catebid = catebid;
    }

    public String getCatesid() {
        return catesid == null ? "0" : catesid;
    }

    public void setCatesid(String catesid) {
        this.catesid = catesid;
    }

    public String getAreabid() {
        return areabid == null ? "0" : areabid;
    }

    public void setAreabid(String areabid) {
        this.areabid = areabid;
    }

    public String getAreasid() {
        return areasid == null ? "0" : areasid;
    }

    public void setAreasid(String areasid) {
        this.areasid = areasid;
    }

    public String getKw() {
        return kw == null ? "" : kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getItype() {
        return itype;
    }

    public void setItype(String itype) {
        this.itype = itype;
    }
}
