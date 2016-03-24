package com.example.androidchoi.helloguide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.model.PlaceData;

import java.util.ArrayList;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceItemViewHolder> {

    List<PlaceData> mItems = new ArrayList<>();

    PlaceItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(PlaceItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // PlaceList item 추가 메소드
    public void setItems(List<PlaceData> items){
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
