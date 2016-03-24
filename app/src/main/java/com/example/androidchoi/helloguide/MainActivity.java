package com.example.androidchoi.helloguide;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.androidchoi.helloguide.model.PlaceData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PlaceListAdapter mPlaceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(),android.R.color.white));
        setSupportActionBar(toolbar);

        // Setting RecyclerView, PlaceListAdapter
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView_place_list);
        mPlaceListAdapter = new PlaceListAdapter();
        mRecyclerView.setAdapter(mPlaceListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Listing Places
        List<PlaceData> placeList = new ArrayList<>();
        // 5개 샘플 아이템 생성
        for(int i = 0; i<5; i++){
            PlaceData placeData = new PlaceData();
            placeList.add(placeData);
        }
        mPlaceListAdapter.setItems(placeList);
    }
}
