package com.example.androidchoi.helloguide.model;

import java.io.Serializable;

/**
 * Created by Choi on 2016-03-24.
 */
public class PlaceData implements Serializable {
    private String mName;
    private String mContent;
    private int mImageReSources;

    public PlaceData(String name, String content) {
        mName = name;
        mContent = content;
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
