package com.example.androidchoi.helloguide;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.model.ResponseData;

public class WritePostActivity extends AppCompatActivity {

    EditText mEditTextTitle;
    EditText mEditTextContent;
    String mCategory;

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

        // 터치 인터셉터
        FrameLayout touchInterceptor = (FrameLayout)findViewById(R.id.touchInterceptor);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mEditTextTitle.isFocused()) {
                        Rect outRect = new Rect();
                        mEditTextTitle.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditTextTitle.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }else if (mEditTextContent.isFocused()) {
                        Rect outRect = new Rect();
                        mEditTextContent.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            mEditTextContent.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });
    }

    public void writePost(String title, String content, String category){
        NetworkManager.getInstance().writePost(title, content, category, new NetworkManager.OnResultListener<ResponseData>() {
            @Override
            public void onSuccess(ResponseData result) {
                if(result.getMessage().equals("insert success")){
                    setResult(RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFail(String error) {
                Log.i("error : ", error);
                Toast.makeText(MyApplication.getContext(), "게시글 작성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            writePost(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mCategory);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
