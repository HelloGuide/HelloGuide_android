package com.example.androidchoi.helloguide.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-05-17.
 */
public class BoardList {
    @SerializedName("boardDatas")
    List<BoardData> mBoardDatas = new ArrayList<>();

    public List<BoardData> getBoardDatas() {
        return mBoardDatas;
    }
}
