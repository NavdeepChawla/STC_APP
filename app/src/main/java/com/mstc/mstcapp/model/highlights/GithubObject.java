package com.mstc.mstcapp.model.highlights;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "GITHUB")
public class GithubObject {

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("link")
    private String link;

    public GithubObject(String title, String link) {
        this.title = title;
        this.link = link;
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
}
