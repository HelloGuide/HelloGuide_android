package com.example.androidchoi.helloguide;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    public static final boolean DISCONTINUOUS_SCAN = true;

    private static final int REQUEST_ENABLE_BT = 1;

    RecyclerView mRecyclerView;
    PlaceListAdapter mPlaceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //사용자가 블루투스를 켜도록 요청.
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }

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
        mPlaceListAdapter.addItems(new PlaceServerData("근정전", "경복궁 근정전은 조선시대 법궁인 경복궁의 중심 건물로, 신하들이 임금에게 새해 인사를 드리거나 국가의식을 거행하고 외국 사신을 맞이하던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611701.jpg", "11",  "02230000", "11", 37.578575, 126.977013));
        mPlaceListAdapter.addItems(new PlaceServerData("경회루", "경복궁 근정전 서북쪽 연못 안에 세운 경회루는, 나라에 경사가 있거나 사신이 왔을 때 연회를 베풀던 곳이다.", "http://www.cha.go.kr/unisearch/images/national_treasure/1611724.jpg", "11",  "02240000", "11", 37.579773, 126.976051));
        mPlaceListAdapter.addItems(new PlaceServerData("자경전", "자경전은 1867년 경복궁을 다시 지으면서 자미당 터에 고종의 양어머니인 조대비(신정왕후)를 위해 지은 대비전이다.", "http://www.cha.go.kr/unisearch/images/treasure/1613927.jpg", "12",  "08090000", "11", 37.580299, 126.978096));
        mPlaceListAdapter.addItems(new PlaceServerData("십장생 굴뚝", "십장생 굴뚝은 자경전 뒷담의 한 면을 돌출시켜 만든 것이다", "http://www.cha.go.kr/unisearch/images/treasure/1613945.jpg", "12",  "08100000", "11", 37.580566, 126.978195));
        mPlaceListAdapter.addItems(new PlaceServerData("아미산 굴뚝", "경복궁 아미산 굴뚝은 왕비의 생활공간인 교태전 온돌방 밑을 통과하여 연기가 나가는 굴뚝이다.", "http://www.cha.go.kr/unisearch/images/treasure/1613958.jpg", "12",  "08110000", "11", 37.580238, 126.976964));
        mPlaceListAdapter.addItems(new PlaceServerData("근정문 및 행각", "근정문은 경복궁의 중심 건물인 근정전의 남문으로 좌우에 행각이 둘러싸고 있다.", "http://www.cha.go.kr/unisearch/images/treasure/1613973.jpg", "12",  "08120000", "11", 37.577736, 126.976967));
        mPlaceListAdapter.addItems(new PlaceServerData("풍기대", "풍기대는 조선시대 바람의 세기와 방향을 재는데 사용했던 것이다.", "http://www.cha.go.kr/unisearch/images/treasure/1614087.jpg", "12",  "08470000", "11", 37.580799, 126.976997));
        mPlaceListAdapter.addItems(new PlaceServerData("사정전", "사정전은 왕이 평상시 거처하며 정사를 보살피던 곳으로 근정전에서 뒤편으로 사정문을 지나면 정면에 위치한다.", "http://www.cha.go.kr/unisearch/images/treasure/2283192.jpg", "12",  "17590000", "11", 37.579109, 126.977043));
        mPlaceListAdapter.addItems(new PlaceServerData("수정전", "수정전은 근정전 서측에 있는 건물로써, 북쪽으로는 경회루가 자리 잡고 있다.", "http://www.cha.go.kr/unisearch/images/treasure/2281864.jpg", "12",  "17600000", "11", 37.578999, 126.975952));
        mPlaceListAdapter.addItems(new PlaceServerData("향원정", "향원정은 경복궁 북쪽 후원에 있는 향원지 내의 가운데 섬 위에 건립된 육각형의 정자이다.", "http://www.cha.go.kr/unisearch/images/treasure/2281891.jpg", "12",  "17610000", "11", 37.582371, 126.977051));
        mPlaceListAdapter.addItems(new PlaceServerData("육상궁", "육상궁은 영조의 생모이며 숙종의 후궁인 숙빈 최씨의 신위를 모신 사당이다.", "http://www.cha.go.kr/unisearch/images/history_site/1624577.jpg", "13", "01490000", "11", 37.585438, 126.973503));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
