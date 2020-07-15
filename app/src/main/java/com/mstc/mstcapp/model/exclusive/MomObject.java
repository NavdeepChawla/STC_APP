package com.mstc.mstcapp.model.exclusive;

public class MomObject {
    private String momContent;
    private String momTitle;

    public MomObject() {
    }

    public MomObject(String momContent, String momTitle) {
        this.momContent = momContent;
        this.momTitle = momTitle;
    }

    public String getMomContent() {
        return momContent;
    }

    public void setMomContent(String momContent) {
        this.momContent = momContent;
    }

    public String getMomTitle() {
        return momTitle;
    }

    public void setMomTitle(String momTitle) {
        this.momTitle = momTitle;
    }
}
