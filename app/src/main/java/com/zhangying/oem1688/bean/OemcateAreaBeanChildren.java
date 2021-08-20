package com.zhangying.oem1688.bean;

public class OemcateAreaBeanChildren {
    private int id;

    public int getId() {
        return id;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    private Boolean isSelect;
    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    private int parent_id;

    public int getParent_position() {
        return parent_position;
    }

    public void setParent_position(int parent_position) {
        this.parent_position = parent_position;
    }

    private int parent_position;


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
