package com.example.androidchoi.helloguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherPlaceSearchFragment extends Fragment {

    MapView mMapView;

    public OtherPlaceSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_place_search, container, false);

        // daum map 생성
        mMapView = new MapView(getActivity());
        mMapView.setDaumMapApiKey(getString(R.string.API_KEY));
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mMapView);
        settingMapView();
        settingMapMarker();
        return view;
    }

    // map 설정 변경 method
    public void settingMapView() {
        // 중심점 변경
        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.577705, 126.977036), true);
        // 줌 레벨 변경
        mMapView.setZoomLevel(1, true);
        // 중심점 변경 + 줌 레벨 변경
        // mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.41, 126.52), 9, true);
        // 줌 인
        mMapView.zoomIn(true);
        // 줌 아웃
        mMapView.zoomOut(true);
    }

    // map marker 설정 method
    public void settingMapMarker(){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(getString(R.string.gyeongbokgung));
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.577705, 126.977036));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mMapView.addPOIItem(marker);
    }


}
