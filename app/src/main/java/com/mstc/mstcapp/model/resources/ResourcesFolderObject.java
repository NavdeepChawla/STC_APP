package com.mstc.mstcapp.model.resources;

import com.google.gson.annotations.SerializedName;

public class ResourcesFolderObject {
    @SerializedName("title")
    String resourcesfolderTitle;
    @SerializedName("link")
    String resourcefolderLink;
    @SerializedName("desc")
    String resourcefolderDesc;

    public String getResourcefolderDesc() {
        return resourcefolderDesc;
    }

    public void setResourcefolderDesc(String resourcefolderDesc) {
        this.resourcefolderDesc = resourcefolderDesc;
    }

    public ResourcesFolderObject() {
    }

    public ResourcesFolderObject(String resourcesfolderTitle,String resourcefolderLink,String resourcefolderDesc) {
        this.resourcesfolderTitle = resourcesfolderTitle;
        this.resourcefolderLink=resourcefolderLink;
        this.resourcefolderDesc=resourcefolderDesc;
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
