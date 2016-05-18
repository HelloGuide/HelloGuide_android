package com.example.androidchoi.helloguide;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class WritePostActivity extends AppCompatActivity {

    EditText mEditTextTitle;
    EditText mEditTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
        setSupportActionBar(toolbar);


        mEditTextTitle = (EditText)findViewById(R.id.editText_post_title);
        mEditTextContent = (EditText)findViewById(R.id.editText_post_content);

        //제목 EditText에 자동 포커스 및 키보드 세팅
        mEditTextTitle.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditTextTitle, InputMethodManager.SHOW_IMPLICIT);
    }
}
