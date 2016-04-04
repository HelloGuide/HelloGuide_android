package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        // 해당 위치 이름으로 toolbar title 변경
        toolbar.setTitle(placeData.getName());
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
        setSupportActionBar(toolbar);
        // home 버튼 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        // 탭 설정
        mPlaceInfoPagerAdapter = new PlaceInfoPagerAdapter(getSupportFragmentManager());
        mPlaceInfoPagerAdapter.addFragment(new PlaceContentFragment());
        mPlaceInfoPagerAdapter.addFragment(new OtherPlaceSearchFragment());
        mPlaceInfoPagerAdapter.setTabList(new String[]{getString(R.string.place_info), getString(R.string.place_search)}); // 탭 이름 설정

        // 뷰페이저 설정
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPlaceInfoPagerAdapter);
        mTabLayout = (TabLayout)findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
