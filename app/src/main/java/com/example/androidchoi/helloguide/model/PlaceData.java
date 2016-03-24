package com.example.androidchoi.helloguide.model;

/**
 * Created by Choi on 2016-03-24.
 */
public class PlaceData {
    private String mName;
    private String mContent;
    private int mImageReSources;

    public PlaceData() {
        mName = "Sample";
        mContent = "Sample";
        mImageReSources = -1;
    }

    public String getName() {
        return mName;
    }

    public String getContent() {
        return mContent;
    }

    public int getImageReSources() {
        return mImageReSources;
    }
}
