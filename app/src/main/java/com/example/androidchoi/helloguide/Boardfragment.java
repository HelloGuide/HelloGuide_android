package com.example.androidchoi.helloguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Adapter.BoardListAdapter;
import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.model.BoardList;

public class Boardfragment extends Fragment {
    private static final String ARG_CATEGORY = "category";

    private String mCategory;
    RecyclerView mRecyclerView;
    BoardListAdapter mBoardListAdapter;

    public static Boardfragment newInstance(String category) {
        Boardfragment fragment = new Boardfragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public Boardfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_boardfragment, container, false);

        // Setting RecyclerView, BoardListAdapter
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerView_board_list);
        mBoardListAdapter = new BoardListAdapter();
        mRecyclerView.setAdapter(mBoardListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        getBoardList(); // 게시글 목록 불러오기
        return view;
    }

    // 게시글 목록 불러오는 method
    public void getBoardList(){
        NetworkManager.getInstance().getBoardList("1", " ", new NetworkManager.OnResultListener<BoardList>() {
            @Override
            public void onSuccess(BoardList result) {
                mBoardListAdapter.setItems(result.getBoardDatas());
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "데이터를 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
