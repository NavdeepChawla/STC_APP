package com.mstc.mstcapp.model;

public class ResourceModel {
    private final String domain;
    private final int drawable;

    public ResourceModel(String domain, int drawable) {
        this.domain = domain;
        this.drawable = drawable;
    }

    public String getDomain() {
        return domain;
    }

    public int getDrawable() {
        return drawable;
    }
}

