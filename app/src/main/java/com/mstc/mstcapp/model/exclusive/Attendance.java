package com.mstc.mstcapp.model.exclusive;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "ATTENDANCE")
public class Attendance {

    @PrimaryKey
    @SerializedName("id")
    @NonNull
    public String id;

    @SerializedName("content")
    private ArrayList<String> content;

    @SerializedName("title")
    private String title;

    public Attendance(@NonNull String id, ArrayList<String> content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
