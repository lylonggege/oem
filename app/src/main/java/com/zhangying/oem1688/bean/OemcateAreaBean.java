package com.zhangying.oem1688.bean;

import java.util.List;

public class OemcateAreaBean {
    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    private boolean aBoolean;
    private int id;

    public int getId() {
        return id;
    }

    public List<OemcateAreaBeanChildren> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<OemcateAreaBeanChildren> childrenList) {
        this.childrenList = childrenList;
    }

    private List<OemcateAreaBeanChildren> childrenList;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
