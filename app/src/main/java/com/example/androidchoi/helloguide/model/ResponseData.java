package com.example.androidchoi.helloguide.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-05-17.
 */
public class ResponseData {
    private String message;
    @SerializedName("memInfo")
    private UserInfo mUserInfo;

    public String getMessage() {
        return message;
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }
}
