package com.bjpowernode.CRM.settings.domain;

public class DicType {
    //数据字典类型,一种类型对应多个值
    //code唯一且不能重复，非空，所以当主键就用了
    private String code;
    private String name;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
