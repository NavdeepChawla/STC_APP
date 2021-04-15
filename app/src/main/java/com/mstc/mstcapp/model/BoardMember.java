package com.mstc.mstcapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "BOARD")
public class BoardMember {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @SerializedName("image")
    String image;
    @SerializedName("name")
    String name;
    @SerializedName("position")
    String position;
    @SerializedName("link")

    String link;

    public BoardMember(String image, String name, String position, String link) {
        this.image = image;
        this.name = name;
        this.position = position;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
