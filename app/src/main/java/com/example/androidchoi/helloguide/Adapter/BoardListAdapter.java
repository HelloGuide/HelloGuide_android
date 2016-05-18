package com.example.androidchoi.helloguide.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.ViewHolder.BoardItemViewHolder;
import com.example.androidchoi.helloguide.model.BoardData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardItemViewHolder> {

    List<BoardData> mItems = new ArrayList<>();

    BoardItemViewHolder.OnItemClickListener mItemClickListener;
    public void setOnItemClickListener(BoardItemViewHolder.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // BoardItem get 메소드
    public BoardData getItem(int position){
        return mItems.get(position);
    }

    // BoardItem 개별 추가 메소드 (Sample Data용)
    public void addItems(BoardData boardData){
        mItems.add(boardData);
        notifyDataSetChanged();
    }

    // BoardItem item 추가 메소드
    public void setItems(List<BoardData> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public BoardItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_board_item, null);
        return new BoardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardItemViewHolder holder, int position) {
        try {
            holder.setItems(mItems.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.setOnItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
