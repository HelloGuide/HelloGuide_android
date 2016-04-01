package com.example.androidchoi.helloguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherPlaceSearchFragment extends Fragment {

    static final LatLng DEFAULT_POINT = new LatLng( 37.56, 126.97);
    private GoogleMap map;

    public OtherPlaceSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_place_search, container, false);

        getFragmentManager().findFragmentById(R.id.map);
        map = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
        Marker seoul = map.addMarker(new MarkerOptions().position(DEFAULT_POINT).title("Seoul"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_POINT, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        return view;
    }


}
