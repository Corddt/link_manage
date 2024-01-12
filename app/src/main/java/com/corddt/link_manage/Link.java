package com.corddt.link_manage;

public class Link {
    private int id; // 添加 id 属性
    private String name;
    private String url;

    // 构造函数用于创建新链接，不需要 id
    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // 构造函数用于从数据库获取链接，包含 id
    public Link(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // getter 和 setter 方法...
}
