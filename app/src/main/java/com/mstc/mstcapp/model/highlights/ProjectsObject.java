package com.mstc.mstcapp.model.highlights;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "PROJECTS")
public class ProjectsObject {

    @PrimaryKey
    @NonNull
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("contributors")
    private ArrayList<String> contributorsList;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;

    public ProjectsObject(String title, ArrayList<String> contributorsList, String link, String description) {
        this.title = title;
        this.contributorsList = contributorsList;
        this.link = link;
        this.description = description;
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

    public ArrayList<String> getContributorsList() {
        return contributorsList;
    }

    public void setContributorsList(ArrayList<String> contributorsList) {
        this.contributorsList = contributorsList;
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


}
