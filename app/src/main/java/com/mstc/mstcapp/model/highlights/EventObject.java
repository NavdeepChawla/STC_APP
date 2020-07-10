package com.mstc.mstcapp.model.highlights;

public class EventObject
{

    private String eventTitle;
    private String eventDesc;
    private String eventLink;
    private int eventPicture;

    public EventObject(String eventTitle, String eventDesc, String eventLink, int eventPicture) {
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

    public int getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(int eventPicture) {
        this.eventPicture = eventPicture;
    }

}
