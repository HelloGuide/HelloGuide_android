package com.example.androidchoi.helloguide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.helloguide.model.PlaceData;

public class PlaceItemViewHolder extends RecyclerView.ViewHolder {

    TextView mTextPlaceName;
    TextView mTextPlaceContent;
    ImageView mImagePlace;

    public PlaceItemViewHolder(View itemView) {
        super(itemView);
        mTextPlaceName = (TextView)itemView.findViewById(R.id.text_place_name);
        mTextPlaceContent = (TextView)itemView.findViewById(R.id.text_place_content);
        mImagePlace = (ImageView)itemView.findViewById(R.id.image_place);
    }

    public void setItems(PlaceData placeData){
        mTextPlaceName.setText(placeData.getName());
        mTextPlaceContent.setText(placeData.getContent());
//        mImagePlace.setImageResource(placeData.getImageReSources());
    }
}
