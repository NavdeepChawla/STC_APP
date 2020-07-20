package com.mstc.mstcapp.model.highlights;

import com.google.gson.annotations.SerializedName;

public class EventObject
{

    @SerializedName("title")
    private String eventTitle;
    @SerializedName("pic")
    private String eventPicture;
    @SerializedName("link")
    private String eventLink;
    @SerializedName("desc")
    private String eventDesc;

    public EventObject(String eventTitle, String eventDesc, String eventLink, String eventPicture) {
        this.eventTitle = eventTitle;
        this.eventDesc = eventDesc;
        this.eventLink = eventLink;
        this.eventPicture = eventPicture;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

}
