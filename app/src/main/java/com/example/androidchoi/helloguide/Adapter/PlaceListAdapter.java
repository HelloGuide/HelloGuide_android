package com.example.androidchoi.helloguide.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.ViewHolder.PlaceItemViewHolder;
import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.model.PlaceServerData;

import java.util.ArrayList;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceItemViewHolder> {

    List<PlaceServerData> mItems = new ArrayList<>();

    PlaceItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(PlaceItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // PlaceData get 메소드
    public PlaceServerData getItem(int position){
        return mItems.get(position);
    }

    // PlaceItem 개별 추가 메소드 (Sample Data용)
    public void addItems(PlaceServerData placeServerData){
        mItems.add(placeServerData);
        notifyDataSetChanged();
    }

    // PlaceList item 추가 메소드
    public void setItems(List<PlaceServerData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public PlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place_item, null);
        return new PlaceItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceItemViewHolder holder, int position) {
        holder.setItems(mItems.get(position));
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
