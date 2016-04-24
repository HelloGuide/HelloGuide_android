package com.example.androidchoi.helloguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.Adapter.OtherPlaceListAdapter;
import com.example.androidchoi.helloguide.ViewHolder.OtherPlaceItemViewHolder;
import com.example.androidchoi.helloguide.model.PlaceServerData;

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
        settingMapView();
        settingStartMarker(); // 출발지 마커 표시

        // Setting RecyclerView, PlaceListAdapter
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_other_place_list);
        mOhterPlaceListAdapter = new OtherPlaceListAdapter();
        mRecyclerView.setAdapter(mOhterPlaceListAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Listing Places
        // List<PlaceServerData> placeList = new ArrayList<>();

        // 샘플 아이템 생성
        mOhterPlaceListAdapter.addItems(new PlaceServerData("근정전", "근정전", "", "11",  "02230000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("경회루", "경회루", "", "11",  "02240000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("자경전", "자경전", "", "12",  "08090000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("십장생 굴뚝", "십장생 굴뚝", "", "12",  "08100000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("아미산 굴뚝", "아미산 굴뚝", "", "12",  "08110000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("근정문 및 행각", "근정문 및 행각", "", "12",  "08120000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("풍기대", "풍기대", "", "12",  "08470000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("사정전", "사정전", "", "12",  "17590000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("수정전", "수정전", "", "12",  "17600000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("향원정", "향원정", "", "12",  "17610000", "11"));
        mOhterPlaceListAdapter.addItems(new PlaceServerData("육상궁", "육상궁", "", "13",  "01490000", "11"));

        // 아이템 클릭 이벤트 설정
        mOhterPlaceListAdapter.setOnItemClickListener(new OtherPlaceItemViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 1. 지도 경로 출력
                showRoute();
                // 2. 선택 위치 정보 서버에 전달
            }
        });

        return view;
    }

    // 지도에 경로 출력 method
    public void showRoute(){

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
        poiItemEnd.setMapPoint(MapPoint.mapPointWithGeoCoord(37.578575, 126.977036));
        poiItemEnd.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemEnd.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemEnd.setShowCalloutBalloonOnTouch(false);
        poiItemEnd.setCustomImageResourceId(R.drawable.image_marker_end);
        poiItemEnd.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemEnd);

//        MapPolyline polyline2 = new MapPolyline(21);
//        polyline2.setTag(2000);
//        polyline2.setLineColor(Color.argb(128, 0, 0, 255));
//        polyline2.addPoints(mPolyline2Points);
//        mMapView.addPolyline(polyline2);
//
//        MapPointBounds mapPointBounds = new MapPointBounds(mPolyline2Points);
//        int padding = 200; // px
//        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

    }

    // map 설정 변경 method
    public void settingMapView() {
        // 중심점 변경
        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.579617, 126.977448), true);
        // 줌 레벨 변경
        mMapView.setZoomLevel(2, true);
        // 중심점 변경 + 줌 레벨 변경
        // mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
        // 줌 인
        mMapView.zoomIn(true);
        // 줌 아웃
        mMapView.zoomOut(true);
    }

    // map start marker 설정 method
    public void settingStartMarker(){

        MapPOIItem poiItemStart = new MapPOIItem();
        poiItemStart.setItemName("Start");
        poiItemStart.setTag(1001);
        poiItemStart.setMapPoint(MapPoint.mapPointWithGeoCoord(37.579769,126.976036));
        poiItemStart.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItemStart.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        poiItemStart.setShowCalloutBalloonOnTouch(false);
        poiItemStart.setCustomImageResourceId(R.drawable.image_marker_start);
        poiItemStart.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
        mMapView.addPOIItem(poiItemStart);

//        MapPOIItem marker = new MapPOIItem();
//        marker.setItemName(getString(R.string.gyeongbokgung));
//        marker.setTag(0);
//        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.577705, 126.977036));
//        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        mMapView.addPOIItem(marker);
    }


}
