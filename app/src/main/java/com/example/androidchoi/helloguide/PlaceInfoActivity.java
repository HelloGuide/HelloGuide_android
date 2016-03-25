package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidchoi.helloguide.model.PlaceData;

public class PlaceInfoActivity extends AppCompatActivity {

    public static final String PLACEDATA = "placeData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        // 이전 activity에서 전달된 intent
        Intent intent = getIntent();
        PlaceData placeData = (PlaceData)intent.getSerializableExtra(PLACEDATA);

        Toast.makeText(PlaceInfoActivity.this, placeData.getName() +"/" +placeData.getContent(), Toast.LENGTH_SHORT).show();
    }
}
