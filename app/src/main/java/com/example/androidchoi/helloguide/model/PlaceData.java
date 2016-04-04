package com.example.androidchoi.helloguide.model;

import com.begentgroup.xmlparser.SerializedName;

/**
 * Created by Choi on 2016-03-24.
 */
public class PlaceData{

    @SerializedName("ccbaMnm1")
    private String name;
    private String imageUrl;
    private String content;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContent() {
        return content;
    }

    public PlaceData() {
        name = "null";
        content = "null";
        imageUrl = "http://";
    }
}
