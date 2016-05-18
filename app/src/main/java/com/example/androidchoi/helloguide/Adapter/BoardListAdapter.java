package com.example.androidchoi.helloguide.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.ViewHolder.BoardHeaderItemViewHolder;
import com.example.androidchoi.helloguide.ViewHolder.BoardItemViewHolder;
import com.example.androidchoi.helloguide.model.BoardData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<BoardData> mItems = new ArrayList<>();

    BoardHeaderItemViewHolder.OnSpinnerSelectListener mHeaderSpinnerSelectListener;
    public void setOnSpinnerSelectListener(BoardHeaderItemViewHolder.OnSpinnerSelectListener listener) {
        mHeaderSpinnerSelectListener = listener;
    }
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_select_category, parent, false);
            return new BoardHeaderItemViewHolder(view);
        }
        else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_board_item, parent, false);
            return new BoardItemViewHolder(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BoardHeaderItemViewHolder){
            BoardHeaderItemViewHolder boardHeaderItemViewHolder = (BoardHeaderItemViewHolder)holder;
            boardHeaderItemViewHolder.setOnSpinnerClickListener(mHeaderSpinnerSelectListener);
        }
        else if(holder instanceof BoardItemViewHolder) {
            BoardItemViewHolder boardItemViewHolder = (BoardItemViewHolder)holder;
            try {
                boardItemViewHolder.setItems(mItems.get(position-1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            boardItemViewHolder.setOnItemClickListener(mItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
}
