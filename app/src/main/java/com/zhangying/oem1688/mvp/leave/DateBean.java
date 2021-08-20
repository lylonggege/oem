package com.zhangying.oem1688.mvp.leave;

public class DateBean {
    private String name;
    private String phone;
    private String lycomId;

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

    public String getLycomId() {
        return lycomId;
    }

    //公司编号.
    public void setLycomId(String lycomId) {
        this.lycomId = lycomId;
    }

    public String getLycate() {
        return lycate;
    }
    //选择的代工品类，多个以英文逗号连接.
    public void setLycate(String lycate) {
        this.lycate = lycate;
    }

    public String getLylm() {
        return lylm;
    }
    //留言栏目(goods：产品、store：公司、news：新闻、scinfo：需求).
    public void setLylm(String lylm) {
        this.lylm = lylm;
    }

    public String getId() {
        return id;
    }

    //浏览栏目编号(产品编号、公司编号、新闻编号、需求编号).
    public void setId(String id) {
        this.id = id;
    }

    public String getLyagent() {
        return lyagent;
    }
     //留言终端，默认值7.
    public void setLyagent(String lyagent) {
        this.lyagent = lyagent;
    }

    private String lycate;
    private String lylm;
    private String id;
    private String lyagent;
}
