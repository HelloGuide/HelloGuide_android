package com.example.androidchoi.helloguide.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choi on 2016-04-04.
 */

// Json Data 가져오는 데 필요한 PlaceServerData Container
public class PlaceList {

    @SerializedName("places")
    List<PlaceServerData> placeList = new ArrayList<>();
    public List<PlaceServerData> getPlaceList() {
        return placeList;
    }
}
