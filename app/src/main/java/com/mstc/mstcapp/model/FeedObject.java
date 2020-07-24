package com.mstc.mstcapp.model;

import com.google.gson.annotations.SerializedName;

public class FeedObject {

    @SerializedName("title")
    private String feedTitle;

    @SerializedName("desc")
    private String feedDesc;

    @SerializedName("link")
    private String feedLink;

    @SerializedName("pic")
    private String feedPicture;

    public FeedObject(String feedTitle, String feedDesc, String feedLink, String feedPicture) {
        this.feedTitle = feedTitle;
        this.feedDesc = feedDesc;
        this.feedLink = feedLink;
        this.feedPicture = feedPicture;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedDesc() {
        return feedDesc;
    }

    public void setFeedDesc(String feedDesc) {
        this.feedDesc = feedDesc;
    }

    public String getFeedLink() {
        return feedLink;
    }

    public void setFeedLink(String feedLink) {
        this.feedLink = feedLink;
    }

    public String getFeedPicture() {
        return feedPicture;
    }

    public void setFeedPicture(String feedPicture) {
        this.feedPicture = feedPicture;
    }
}
