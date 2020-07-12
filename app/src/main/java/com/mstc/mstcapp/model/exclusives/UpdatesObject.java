package com.mstc.mstcapp.model.exclusives;

public class UpdatesObject {
    private String updatesContent;
    private String updatesTitle;

    public UpdatesObject(String updatesContent,  String updatesTitle) {
        this.updatesContent = updatesContent;
        this.updatesTitle = updatesTitle;
    }

    public UpdatesObject() {
    }

    public String getUpdatesContent() {
        return updatesContent;
    }

    public void setUpdatesContent(String updatesContent) {
        this.updatesContent = updatesContent;
    }

    public String getUpdatesTitle() {
        return updatesTitle;
    }

    public void setUpdatesTitle(String updatesTitle) {
        this.updatesTitle = updatesTitle;
    }
}
