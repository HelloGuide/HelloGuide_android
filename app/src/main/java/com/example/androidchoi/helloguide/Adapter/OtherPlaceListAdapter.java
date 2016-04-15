package com.example.androidchoi.helloguide.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.ViewHolder.OtherPlaceItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class OtherPlaceListAdapter extends RecyclerView.Adapter<OtherPlaceItemViewHolder> {

    List<String> mItems = new ArrayList<>();

    OtherPlaceItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(OtherPlaceItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // PlaceData get 메소드
    public String getItem(int position){
        return mItems.get(position);
    }

    // PlaceItem 개별 추가 메소드 (Sample Data용)
    public void addItems(String placeName){
        mItems.add(placeName);
        notifyDataSetChanged();
    }

    // PlaceList item 추가 메소드
    public void setItems(List<String> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public OtherPlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_place_item, null);
        return new OtherPlaceItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OtherPlaceItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
