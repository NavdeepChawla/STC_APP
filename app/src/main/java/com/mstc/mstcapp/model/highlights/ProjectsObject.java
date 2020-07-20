package com.mstc.mstcapp.model.highlights;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectsObject {
    @SerializedName("title")
    private String title;
    @SerializedName("contributors")
    private List<String> contributors = null;
    @SerializedName("link")
    private String link;
    @SerializedName("desc")
    private String desc;

    public ProjectsObject(String title, List<String> contributors, String link, String desc) {
        this.title = title;
        this.contributors = contributors;
        this.link = link;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
