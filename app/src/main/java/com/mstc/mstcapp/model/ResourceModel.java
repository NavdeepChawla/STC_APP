package com.mstc.mstcapp.model;

public class ResourceModel {

    private String title;
    private int image;
    private String domain;

    public ResourceModel(String domain, String title, int image) {
        this.domain = domain;
        this.title = title;
        this.image = image;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
