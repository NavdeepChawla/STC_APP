package com.mstc.mstcapp.model.explore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "BOARD")
public class BoardMemberModel {

    @SerializedName("image")
    private final String image;
    @SerializedName("name")
    private final String name;
    @SerializedName("position")
    private final String position;
    @SerializedName("linkedIn")
    private final String link;
    @SerializedName("phrase")
    private final String phrase;

    @SerializedName("_id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    public BoardMemberModel(String image, String name, String position, String phrase, String link) {
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
