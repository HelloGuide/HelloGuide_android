package com.example.androidchoi.helloguide.model;

/**
 * Created by Choi on 2016-05-17.
 */
public class User {
    private String email;
    private String name;
    private int age;
    private String gender;

    private static User instance;
    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

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

    public void setUser(String email, String name, int age, String gender){
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
