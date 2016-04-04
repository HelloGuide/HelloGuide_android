package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.androidchoi.helloguide.Adapter.PlaceListAdapter;
import com.example.androidchoi.helloguide.model.PlaceServerData;

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
        // List<PlaceServerData> placeList = new ArrayList<>();

        // 샘플 아이템 생성
        mPlaceListAdapter.addItems(new PlaceServerData("근정전", "근정전", "", "11",  "02230000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("경회루", "경회루", "", "11",  "02240000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("자경전", "자경전", "", "12",  "08090000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("십장생 굴뚝", "십장생 굴뚝", "", "12",  "08100000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("아미산 굴뚝", "아미산 굴뚝", "", "12",  "08110000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("근정문 및 행각", "근정문 및 행각", "", "12",  "08120000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("풍기대", "풍기대", "", "12",  "08470000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("사정전", "사정전", "", "12",  "17590000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("수정전", "수정전", "", "12",  "17600000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("향원정", "향원정", "", "12",  "17610000", "11"));
        mPlaceListAdapter.addItems(new PlaceServerData("육상궁", "육상궁", "", "13",  "01490000", "11"));

        // 서버에서 Data 요청
        // mPlaceListAdapter.setItems(placeList);

        // 아이템 클릭 이벤트 설정
        mPlaceListAdapter.setOnItemClickListener(new PlaceItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 해당 Place 정보 화면 이동 구현
                // PlaceData객체를 Extra로 담아 intent call
                Intent intent = new Intent(MainActivity.this, PlaceInfoActivity.class);
                intent.putExtra(PlaceInfoActivity.PLACEDATA, mPlaceListAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
