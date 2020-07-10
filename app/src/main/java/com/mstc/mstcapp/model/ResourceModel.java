package com.mstc.mstcapp.model;

public class ResourceModel {

    private String resourceTitle;
    private int resourcePicture;

    public ResourceModel(String resourceTitle, int resourcePicture) {
        this.resourceTitle = resourceTitle;
        this.resourcePicture = resourcePicture;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public int getResourcePicture() {
        return resourcePicture;
    }

    public void setResourcePicture(int resourcePicture) {
        this.resourcePicture = resourcePicture;
    }
}
