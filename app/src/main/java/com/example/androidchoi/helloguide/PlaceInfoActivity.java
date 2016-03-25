package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Adapter.PlaceInfoPagerAdapter;
import com.example.androidchoi.helloguide.model.PlaceData;

public class PlaceInfoActivity extends AppCompatActivity {

    public static final String PLACEDATA = "placeData";

    PlaceInfoPagerAdapter mPlaceInfoPagerAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        // 이전 activity에서 전달된 intent
        Intent intent = getIntent();
        PlaceData placeData = (PlaceData)intent.getSerializableExtra(PLACEDATA);

        // 뷰페이저 설정
//        mPlaceInfoPagerAdapter = new PlaceInfoPagerAdapter(getSupportFragmentManager(), fragments);
//        mPlaceInfoPagerAdapter.setTabList(new String[]{"정보", "위치 검색"});
//        mViewPager = (ViewPager)findViewById(R.id.view_pager);
//        mViewPager.setAdapter(mPlaceInfoPagerAdapter);
//        mTabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
//        mTabLayout.setupWithViewPager(mViewPager);
        Toast.makeText(PlaceInfoActivity.this, placeData.getName() +"/" +placeData.getContent(), Toast.LENGTH_SHORT).show();
    }
}
