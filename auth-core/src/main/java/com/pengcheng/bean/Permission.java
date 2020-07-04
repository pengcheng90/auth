package com.pengcheng.bean;

/**
 * @Author: pengcheng
 * @Date: 2020/7/4 12:10
 */
public class Permission {
    private String id;
    private String name;
    private String urlPattern;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", urlPattern='" + urlPattern + '\'' +
                '}';
    }
}
