package com.example.androidchoi.helloguide.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.model.PlaceServerData;

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

    public PlaceItemViewHolder(View itemView) {
        super(itemView);
        mTextPlaceName = (TextView)itemView.findViewById(R.id.text_place_name);
        mTextPlaceContent = (TextView)itemView.findViewById(R.id.text_place_content);
        mImagePlace = (ImageView)itemView.findViewById(R.id.image_place_thumb);
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
    public void setItems(PlaceServerData placeServerData){
        mTextPlaceName.setText(placeServerData.getName());
        mTextPlaceContent.setText(placeServerData.getSimpleContent());
        Glide.with(MyApplication.getContext())
                .load(placeServerData.getImageUrl())
                .override(320, 240)
                .centerCrop()
                .placeholder(R.drawable.image_place_default)
                .thumbnail(0.3f)
                .into(mImagePlace);
//        mImagePlace.setImageResource(placeData.getImageReSources());
    }
}
