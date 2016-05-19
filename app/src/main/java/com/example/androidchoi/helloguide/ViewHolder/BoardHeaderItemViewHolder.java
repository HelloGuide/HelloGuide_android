package com.example.androidchoi.helloguide.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.R;

public class BoardHeaderItemViewHolder extends RecyclerView.ViewHolder {

    //아이템 클릭 리스너
    public interface OnSpinnerSelectListener {
        public void onSpinnerSelect(String category);
    }
    OnSpinnerSelectListener mListener;
    public void setOnSpinnerClickListener(OnSpinnerSelectListener listener) {
        mListener = listener;
    }

    Spinner mSpinnerCategory;
    ArrayAdapter<String> mArrayAdapters;

    public BoardHeaderItemViewHolder(View itemView) {
        super(itemView);

        initArrayAdapter();
        mSpinnerCategory = (Spinner)itemView.findViewById(R.id.spinner_category_select);
        mSpinnerCategory.setAdapter(mArrayAdapters);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = (String) mSpinnerCategory.getSelectedItem();
                if (mListener != null && position != RecyclerView.NO_POSITION) {
                    mListener.onSpinnerSelect(category);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // 게시판 설정 메소드
    public void setItems(String category){
        if(mArrayAdapters.getPosition(category) != -1) {
            mSpinnerCategory.setSelection(mArrayAdapters.getPosition(category));
        }
    }

    // Spinner 세팅 메소드
    public void initArrayAdapter(){
        mArrayAdapters = new ArrayAdapter<String>(MyApplication.getContext(), R.layout.spinner_header_item_card);
        String[] stringArray = MyApplication.getContext().getResources().getStringArray(R.array.place_category);
        mArrayAdapters.setDropDownViewResource(R.layout.spinner_dropdown_item_card);
        mArrayAdapters.addAll(stringArray);
    }
}
