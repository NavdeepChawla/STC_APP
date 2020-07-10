package com.mstc.mstcapp.model;

public class FeedObject {

    private String feedTitle;
    private String feedDesc;
    private String feedLink;
    private int feedPicture;

    public FeedObject(String feedTitle, String feedDesc, String feedLink, int feedPicture) {
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

    public int getFeedPicture() {
        return feedPicture;
    }

    public void setFeedPicture(int feedPicture) {
        this.feedPicture = feedPicture;
    }
}
