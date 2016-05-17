package com.example.androidchoi.helloguide.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Choi on 2016-05-17.
 */
public class UserInfo {
    @SerializedName("ID")
    private String email;
    @SerializedName("Name")
    private String name;
    @SerializedName("Age")
    private int age;
    @SerializedName("Gender")
    private String gender;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
