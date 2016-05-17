package com.example.androidchoi.helloguide.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-05-17.
 */
public class BoardData {
    @SerializedName("no")
    private int number;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("date")
    private String mDate;
    @SerializedName("content")
    private String mContent;
    @SerializedName("cate")
    private String mCategory;

    public int getNumber() {
        return number;
    }
    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getContent() {
        return mContent;
    }

    public String getCategory() {
        return mCategory;
    }
}
