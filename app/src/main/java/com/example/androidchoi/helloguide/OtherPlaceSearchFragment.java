package com.example.androidchoi.helloguide;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Adapter.OtherPlaceListAdapter;
import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.ViewHolder.OtherPlaceItemViewHolder;
import com.example.androidchoi.helloguide.model.PlaceList;
import com.example.androidchoi.helloguide.model.PlaceServerData;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherPlaceSearchFragment extends Fragment {

    MapView mMapView;
    RecyclerView mRecyclerView;
    OtherPlaceListAdapter mOhterPlaceListAdapter;
    private PlaceServerData mPlaceServerData;
    private MapPoint[] mPolyline_1;
    private MapPoint[] mPolyline_2;
    private MapPoint[] mPolyline_3;

    public OtherPlaceSearchFragment() {
        // Required empty public constructor
    }

    /*
    PlaceInfoActivity에서  엑스트라를 가져오지 않고 Fragment에서 직접
    엑스트라에 대한 데이터를 저장 하기 위한 메소드
    */
    public static OtherPlaceSearchFragment newInstance(PlaceServerData placeServerData) {
        Bundle args = new Bundle();
        args.putSerializable(PlaceInfoActivity.EXTRA_PLACE_SERVER_DATA, placeServerData);

        OtherPlaceSearchFragment fragment = new OtherPlaceSearchFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_place_search, container, false);

        mPlaceServerData = (PlaceServerData)getArguments().getSerializable(PlaceInfoActivity.EXTRA_PLACE_SERVER_DATA);

        // daum map 생성
        mMapView = new MapView(getActivity());
        mMapView.setDaumMapApiKey(getString(R.string.API_KEY));
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mMapView);
        settingMapView(mPlaceServerData.getLatitude(), mPlaceServerData.getLongitude());
        settingStartMarker(mPlaceServerData.getLatitude(), mPlaceServerData.getLongitude()); // 출발지 마커 표시

        // Setting RecyclerView, PlaceListAdapter
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_other_place_list);
        mOhterPlaceListAdapter = new OtherPlaceListAdapter();
        mRecyclerView.setAdapter(mOhterPlaceListAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Listing Places
        // List<PlaceServerData> placeList = new ArrayList<>();

        mPolyline_1 = new MapPoint[]{
                MapPoint.mapPointWithGeoCoord(37.579773, 126.976051),
                MapPoint.mapPointWithGeoCoord(37.579773, 126.976395),
                MapPoint.mapPointWithGeoCoord(37.579891, 126.976395),
                MapPoint.mapPointWithGeoCoord(37.579891, 126.976768),
                MapPoint.mapPointWithGeoCoord(37.580063, 126.976768),
                MapPoint.mapPointWithGeoCoord(37.580090, 126.977776),
                MapPoint.mapPointWithGeoCoord(37.580227, 126.977776),
                MapPoint.mapPointWithGeoCoord(37.580227, 126.978096),
                MapPoint.mapPointWithGeoCoord(37.580299, 126.978096)
        };

        mPolyline_2 = new MapPoint[]{
                MapPoint.mapPointWithGeoCoord(37.578575, 126.977013),
                MapPoint.mapPointWithGeoCoord(37.578970, 126.977028),
                MapPoint.mapPointWithGeoCoord(37.578967, 126.977257),
                MapPoint.mapPointWithGeoCoord(37.579182, 126.977257),
                MapPoint.mapPointWithGeoCoord(37.579161, 126.977690),
                MapPoint.mapPointWithGeoCoord(37.579723, 126.977725),
                MapPoint.mapPointWithGeoCoord(37.579723, 126.977486),
                MapPoint.mapPointWithGeoCoord(37.580093, 126.977509),
                MapPoint.mapPointWithGeoCoord(37.580097, 126.977768),
                MapPoint.mapPointWithGeoCoord(37.580215, 126.977768),
                MapPoint.mapPointWithGeoCoord(37.580215, 126.978090),
                MapPoint.mapPointWithGeoCoord(37.580299, 126.978096)
        };

        mPolyline_3 = new MapPoint[]{
                MapPoint.mapPointWithGeoCoord(37.578575, 126.977013),
                MapPoint.mapPointWithGeoCoord(37.578964, 126.977028),
                MapPoint.mapPointWithGeoCoord(37.578979, 126.976547),
                MapPoint.mapPointWithGeoCoord(37.579212, 126.976555),
                MapPoint.mapPointWithGeoCoord(37.579212, 126.976364),
                MapPoint.mapPointWithGeoCoord(37.579778, 126.976395),
                MapPoint.mapPointWithGeoCoord(37.579773, 126.976051)
        };

        // 샘플 아이템 생성
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("근정전", "근정전", "http://www.cha.go.kr/unisearch/images/national_treasure/1611701.jpg", "11", "02230000", "11", 37.578575, 126.977013));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("경회루", "경회루", "http://www.cha.go.kr/unisearch/images/national_treasure/1611724.jpg", "11", "02240000", "11", 37.579773, 126.976051));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("자경전", "자경전", "http://www.cha.go.kr/unisearch/images/treasure/1613927.jpg", "12", "08090000", "11", 37.580299, 126.978096));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("십장생 굴뚝", "십장생 굴뚝", "http://www.cha.go.kr/unisearch/images/treasure/1613945.jpg", "12", "08100000", "11", 37.580566, 126.978195));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("아미산 굴뚝", "아미산 굴뚝", "http://www.cha.go.kr/unisearch/images/treasure/1613958.jpg", "12", "08110000", "11", 37.580238, 126.976964));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("근정문 및 행각", "근정문 및 행각", "http://www.cha.go.kr/unisearch/images/treasure/1613973.jpg", "12", "08120000", "11", 37.577736, 126.976967));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("풍기대", "풍기대", "http://www.cha.go.kr/unisearch/images/treasure/1614087.jpg", "12", "08470000", "11", 37.580799, 126.976997));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("사정전", "사정전", "http://www.cha.go.kr/unisearch/images/treasure/2283192.jpg", "12", "17590000", "11", 37.579109, 126.977043));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("수정전", "수정전", "http://www.cha.go.kr/unisearch/images/treasure/2281864.jpg", "12", "17600000", "11", 37.578999, 126.975952));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("향원정", "향원정", "http://www.cha.go.kr/unisearch/images/treasure/2281891.jpg", "12", "17610000", "11", 37.582371, 126.977051));
//        mOhterPlaceListAdapter.addItems(new PlaceServerData("육상궁", "육상궁", "http://www.cha.go.kr/unisearch/images/history_site/1624577.jpg", "13", "01490000", "11", 37.585438, 126.973503));
        getOtherPlaceList();

        // 아이템 클릭 이벤트 설정
        mOhterPlaceListAdapter.setOnItemClickListener(new OtherPlaceItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showSearchDialog(position);
            }
        });

        return view;
    }


    // 서버에서 건물 목록을 가져오는 메소드
    public void getOtherPlaceList(){
        NetworkManager.getInstance().getPlaceList(new NetworkManager.OnResultListener<PlaceList>() {
            @Override
            public void onSuccess(PlaceList result) {
                mOhterPlaceListAdapter.setItems(result.getPlaceList());
            }
            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "데이터를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 지도에 경로 출력 method
    public void showRoute(String name, double lat, double lng){

        MapPOIItem existingPOIItemEnd = mMapView.findPOIItemByTag(1002);
        if (existingPOIItemEnd != null) {
            mMapView.removePOIItem(existingPOIItemEnd);
        }
        MapPolyline existingPolyline = mMapView.findPolylineByTag(2000);
        if (existingPolyline != null) {
            mMapView.removePolyline(existingPolyline);
        }

        MapPOIItem poiItemEnd = new MapPOIItem();
        poiItemEnd.setItemName("End");
        poiItemEnd.setTag(1002);
        poiItemEnd.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lng));
        poiItemEnd.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemEnd.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemEnd.setShowCalloutBalloonOnTouch(false);
        poiItemEnd.setCustomImageResourceId(R.drawable.image_marker_end);
        poiItemEnd.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(20, 4));
        mMapView.addPOIItem(poiItemEnd);

        if(mPlaceServerData.getName().equals("경회루")){
            if(name.equals("자경전")){
                drawPolyLine(mPolyline_1);
            }
            if(name.equals("근정전")){
                drawPolyLine(mPolyline_3);
            }
        }else if(mPlaceServerData.getName().equals("근정전")){
            if(name.equals("경회루")){
                drawPolyLine(mPolyline_3);
            }
            if(name.equals("자경전")){
                drawPolyLine(mPolyline_2);
            }
        }else if(mPlaceServerData.getName().equals("자경전")){
            if(name.equals("경회루")){
                drawPolyLine(mPolyline_1);
            }
            if(name.equals("근정전")){
                drawPolyLine(mPolyline_2);
            }
        }

        mMapView.moveCamera(CameraUpdateFactory.newMapPoint(MapPoint.mapPointWithGeoCoord((mPlaceServerData.getLatitude() + lat) / 2, (mPlaceServerData.getLongitude() + lng) / 2)));
        mMapView.setZoomLevel(1, true);
//        MapPointBounds mapPointBounds = new MapPointBounds(mPolyline_1);
//        int padding = 200; // px
//        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    private void drawPolyLine(MapPoint[] polyline){
        MapPolyline mapPolyline = new MapPolyline(8);
        mapPolyline.setTag(2000);
        mapPolyline.setLineColor(Color.argb(128, 0, 0, 255));
        mapPolyline.addPoints(polyline);
        mMapView.addPolyline(mapPolyline);
    }

    // map 설정 변경 method
    public void settingMapView(double lat, double lng) {
        // 중심점 변경
        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lng), true);
        // 줌 레벨 변경
        mMapView.setZoomLevel(-3, true);
        // 중심점 변경 + 줌 레벨 변경
        // mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
        // 줌 인
        mMapView.zoomIn(true);
        // 줌 아웃
        mMapView.zoomOut(true);
    }

    // map start marker 설정 method
    public void settingStartMarker(double lat, double lng){

        MapPOIItem poiItemStart = new MapPOIItem();
        poiItemStart.setItemName("Start");
        poiItemStart.setTag(1001);
        poiItemStart.setMapPoint(MapPoint.mapPointWithGeoCoord(lat,lng));
        poiItemStart.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemStart.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemStart.setShowCalloutBalloonOnTouch(false);
        poiItemStart.setCustomImageResourceId(R.drawable.image_marker_start);
        poiItemStart.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(20, 4));
        mMapView.addPOIItem(poiItemStart);

//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName(getString(R.string.gyeongbokgung));
//        marker.setTag(0);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.577705, 126.977036));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        mMapView.addPOIItem(marker);
    }

    // 위치 선택시 다이얼로그 생성 메소드
    public void showSearchDialog(final int position) {
        final SearchDialogFragment dialog = new SearchDialogFragment();
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
        SearchDialogFragment.ButtonEventListener listener = new SearchDialogFragment.ButtonEventListener() {
            @Override
            public void onYesEvent() {
                // 1. 지도 경로 출력
                PlaceServerData otherPlaceServerData = mOhterPlaceListAdapter.getItem(position);
                showRoute(otherPlaceServerData.getName(), otherPlaceServerData.getLatitude(), otherPlaceServerData.getLongitude());
                // 2. 선택 위치 정보 서버에 전달
                NetworkManager.getInstance().GetOtherPlace(mPlaceServerData.getLatitude(), mPlaceServerData.getLongitude()
                        , otherPlaceServerData.getLatitude(), otherPlaceServerData.getLongitude(), otherPlaceServerData.getEnName()
                        , new NetworkManager.OnResultListener<String>() {
                            @Override
                            public void onSuccess(String result) {

                            }

                            @Override
                            public void onFail(String error) {
                                Log.i("error : ", error);
                                Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

                // 다이얼로그 종료
                dialog.dismiss();
            }

            @Override
            public void onNoEvent() {
                // 다이얼로그 종료
                dialog.dismiss();
            }
        };
        dialog.setButtonEventListener(listener);
    }
}
