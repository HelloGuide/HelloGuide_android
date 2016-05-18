package com.example.androidchoi.helloguide.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.androidchoi.helloguide.R;
import com.example.androidchoi.helloguide.model.BoardData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardItemViewHolder extends RecyclerView.ViewHolder {

    //아이템 클릭 리스너
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    TextView mTextTitle;
    TextView mTextContent;
    TextView mTextDate;
    TextView mTextAuthor;


    public BoardItemViewHolder(View itemView) {
        super(itemView);
        mTextTitle = (TextView)itemView.findViewById(R.id.text_board_title);
        mTextContent = (TextView)itemView.findViewById(R.id.text_board_content);
        mTextDate = (TextView)itemView.findViewById(R.id.text_board_date);
        mTextAuthor = (TextView)itemView.findViewById(R.id.text_board_author);

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
    public void setItems(BoardData boardData) throws ParseException {
        mTextTitle.setText(boardData.getTitle());
        mTextContent.setText(boardData.getContent());
        mTextAuthor.setText(boardData.getAuthor());
        mTextDate.setText(changeDateFormat(boardData.getDate()));
    }

    public String changeDateFormat(String dateString) throws ParseException {
        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm";
        final String NEW_FORMAT = "yyyy.MM.dd HH:mm";

        String oldDateString = dateString;
        String newDateString;

        SimpleDateFormat dateFormat = new SimpleDateFormat(OLD_FORMAT);
        Date date = dateFormat.parse(oldDateString);
        dateFormat.applyPattern(NEW_FORMAT);
        newDateString = dateFormat.format(date);
        return newDateString;
    }
}
