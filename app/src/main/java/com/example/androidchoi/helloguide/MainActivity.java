package com.example.androidchoi.helloguide;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

    }
}
