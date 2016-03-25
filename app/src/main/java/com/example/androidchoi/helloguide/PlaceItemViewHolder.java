package com.example.androidchoi.helloguide;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidchoi.helloguide.model.PlaceData;

public class PlaceItemViewHolder extends RecyclerView.ViewHolder {

    //아이템 클릭 리스너
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView mTextPlaceName;
    TextView mTextPlaceContent;
    ImageView mImagePlace;
    PlaceData mPlaceData;

    public PlaceItemViewHolder(View itemView) {
        super(itemView);
        mTextPlaceName = (TextView)itemView.findViewById(R.id.text_place_name);
        mTextPlaceContent = (TextView)itemView.findViewById(R.id.text_place_content);
        mImagePlace = (ImageView)itemView.findViewById(R.id.image_place);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    // View 세팅 메소드
    public void setItems(PlaceData placeData){
        mTextPlaceName.setText(placeData.getName());
        mTextPlaceContent.setText(placeData.getContent());
//        mImagePlace.setImageResource(placeData.getImageReSources());
    }
}
