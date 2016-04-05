package com.example.androidchoi.helloguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.model.PlaceData;
import com.example.androidchoi.helloguide.model.PlaceServerData;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceContentFragment extends Fragment {

    public static final String EXTRA_PLACE_SERVER_DATA = "place_server_data";

    TextView mTextName;
    TextView mTextContent;
    ImageView mImagePlace;
    private PlaceServerData mPlaceServerData;

    public PlaceContentFragment() {
        // Required empty public constructor
    }

    /*
    PlaceInfoActivity에서  엑스트라를 가져오지 않고 Fragment에서 직접
    엑스트라에 대한 데이터를 저장 하기 위한 메소드
    */
    public static PlaceContentFragment newInstance(PlaceServerData placeServerData) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PLACE_SERVER_DATA, placeServerData);

        PlaceContentFragment fragment = new PlaceContentFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_content, container, false);
        mTextName = (TextView)view.findViewById(R.id.text_place_name);
        mTextContent = (TextView)view.findViewById(R.id.text_place_content);
        mImagePlace = (ImageView)view.findViewById(R.id.image_place);

        mPlaceServerData = (PlaceServerData)getArguments().getSerializable(EXTRA_PLACE_SERVER_DATA);
        getPlaceDetailInfo();

        return view;
    }

    // 상세 정보 요청 메소드 (API)
    public void getPlaceDetailInfo(){
        NetworkManager.getInstance().getPlaceInfo(getActivity(), mPlaceServerData.getCcbaKdcd(),
                mPlaceServerData.getCcbaCtcd(), mPlaceServerData.getCcbaAsno(), new NetworkManager.OnResultListener<PlaceData>() {
                    @Override
                    public void onSuccess(PlaceData result) {
                        setViewContent(result);
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
    }

    // view content set 메소드
    public void setViewContent(PlaceData data){
        mTextName.setText(data.getName());
        mTextContent.setText(data.getContent());
        Glide.with(getActivity())
                .load(data.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.image_place_default)
                .thumbnail(0.3f)
                .into(mImagePlace);


    }
}
