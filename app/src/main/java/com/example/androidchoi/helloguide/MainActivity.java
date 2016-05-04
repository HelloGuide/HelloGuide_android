package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Adapter.PlaceListAdapter;
import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.ViewHolder.PlaceItemViewHolder;
import com.example.androidchoi.helloguide.model.PlaceList;
import com.example.androidchoi.helloguide.model.PlaceServerData;

public class MainActivity extends AppCompatActivity {

    //This is a default proximity uuid of the RECO
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true; // Reco 비콘만 스캔 할 경우 true
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true; // 백그라운드 ranging timeout을 설정
    /*
    일부 안드로이드 기기에서 BLE 장치들을 스캔할 때, 한 번만 스캔 후 스캔하지 않는 버그를 위한 설정 변수
    setDiscontinuousScan() 메소드를 이용함. 기본값은 false, 특정 장치에 대해 true로 설정 권장
    */
    public static final boolean DISCONTINUOUS_SCAN = false;


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

        // 샘플 아이템 생성
        mPlaceListAdapter.addItems(new PlaceServerData("근정전", "근정전", "", "11",  "02230000", "11", 37.578575, 126.977013));
        mPlaceListAdapter.addItems(new PlaceServerData("경회루", "경회루", "", "11",  "02240000", "11", 37.579773, 126.976051));
        mPlaceListAdapter.addItems(new PlaceServerData("자경전", "자경전", "", "12",  "08090000", "11", 37.580299, 126.978096));
        mPlaceListAdapter.addItems(new PlaceServerData("십장생 굴뚝", "십장생 굴뚝", "", "12",  "08100000", "11", 37.580566, 126.978195));
        mPlaceListAdapter.addItems(new PlaceServerData("아미산 굴뚝", "아미산 굴뚝", "", "12",  "08110000", "11", 37.580238, 126.976964));
        mPlaceListAdapter.addItems(new PlaceServerData("근정문 및 행각", "근정문 및 행각", "", "12",  "08120000", "11", 37.577736, 126.976967));
        mPlaceListAdapter.addItems(new PlaceServerData("풍기대", "풍기대", "", "12",  "08470000", "11", 37.580799, 126.976997));
        mPlaceListAdapter.addItems(new PlaceServerData("사정전", "사정전", "", "12",  "17590000", "11", 37.579109, 126.977043));
        mPlaceListAdapter.addItems(new PlaceServerData("수정전", "수정전", "", "12",  "17600000", "11", 37.578999, 126.975952));
        mPlaceListAdapter.addItems(new PlaceServerData("향원정", "향원정", "", "12",  "17610000", "11", 37.582371, 126.977051));
        mPlaceListAdapter.addItems(new PlaceServerData("육상궁", "육상궁", "", "13",  "01490000", "11", 37.585438, 126.973503));

//        getPlaceList(); // 서버에서 건물 목록 요청

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

    // 서버에서 건물 목록을 가져오는 메소드
    public void getPlaceList(){
        NetworkManager.getInstance().getPlaceList(MainActivity.this, new NetworkManager.OnResultListener<PlaceList>() {
            @Override
            public void onSuccess(PlaceList result) {
                mPlaceListAdapter.setItems(result.getPlaceList());
            }
            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "데이터를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
