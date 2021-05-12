package com.mstc.mstcapp.model.resources;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "RESOURCES")

public class Resource {

    @SerializedName("title")
    String title;
    @SerializedName("link")
    String link;
    @SerializedName("description")
    String description;
    @SerializedName("domain")
    String domain;

    @PrimaryKey
    @NonNull
    @SerializedName("_id")

    private String id;

    public Resource(String title, String link, String description, String domain) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.domain = domain;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
