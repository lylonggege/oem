package com.zhangying.oem1688.bean;

/**
 * type Evenbus跳转的类型 6/7代表为跳转到产品、工厂的的类型接收
 */
public class EvenBusBean {
    private int type;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    private String content;
}
