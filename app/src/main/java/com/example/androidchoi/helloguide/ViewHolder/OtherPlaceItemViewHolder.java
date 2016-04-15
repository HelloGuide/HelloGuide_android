package com.example.androidchoi.helloguide.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.androidchoi.helloguide.R;

public class OtherPlaceItemViewHolder extends RecyclerView.ViewHolder {

    //아이템 클릭 리스너
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView mTextOtherPlaceName;


    public OtherPlaceItemViewHolder(View itemView) {
        super(itemView);
        mTextOtherPlaceName = (TextView)itemView.findViewById(R.id.text_other_place_name);
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
    public void setItems(String placeName){
        mTextOtherPlaceName.setText(placeName);
    }
}
