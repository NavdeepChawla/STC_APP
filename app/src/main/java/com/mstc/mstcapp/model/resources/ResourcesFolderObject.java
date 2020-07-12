package com.mstc.mstcapp.model.resources;

public class ResourcesFolderObject {
    String resourcesfolderTitle;
    String resourcefolderLink;

    public ResourcesFolderObject() {
    }

    public ResourcesFolderObject(String resourcesfolderTitle,String resourcefolderLink) {
        this.resourcesfolderTitle = resourcesfolderTitle;
        this.resourcefolderLink=resourcefolderLink;
    }

    public String getResourcesfolderTitle() {
        return resourcesfolderTitle;
    }

    public String getResourcefolderLink() {
        return resourcefolderLink;
    }

    public void setResourcefolderLink(String resourcefolderLink) {
        this.resourcefolderLink = resourcefolderLink;
    }

    public void setResourcesfolderTitle(String resourcesfolderTitle) {
        this.resourcesfolderTitle = resourcesfolderTitle;
    }
}
