package com.mstc.mstcapp.model.explore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "BOARD")
public class BoardMember {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @SerializedName("image")
    private final String image;
    @SerializedName("name")
    private final String name;
    @SerializedName("position")
    private final String position;
    @SerializedName("link")
    private final String link;
    @SerializedName("phrase")
    private final String phrase;

    public BoardMember(String image, String name, String position, String phrase, String link) {
        this.image = image;
        this.name = name;
        this.position = position;
        this.phrase = phrase;
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

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getLink() {
        return link;
    }

    public String getPhrase() {
        return phrase;
    }
}
