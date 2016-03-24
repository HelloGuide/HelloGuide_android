package com.example.androidchoi.helloguide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceItemViewHolder> {

    List<Objects> mItems = new ArrayList<>();

    @Override
    public PlaceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place_item, null);
        return new PlaceItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
