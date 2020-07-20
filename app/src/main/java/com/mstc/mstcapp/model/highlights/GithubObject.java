package com.mstc.mstcapp.model.highlights;

import com.google.gson.annotations.SerializedName;

public class GithubObject {
    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;

    public GithubObject(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
