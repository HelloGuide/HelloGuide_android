package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Manager.NetworkManager;
import com.example.androidchoi.helloguide.Manager.PropertyManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String id = PropertyManager.getInstance().getId();
        if (!TextUtils.isEmpty(id)) {
            String password = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().login(id, password, new NetworkManager.OnResultListener<String>() {
                @Override
                public void onSuccess(String result) {
                    if (result.equals(LoginFragment.MESSAGE_SUCCESS)) {
                        goMain();
                    } else {
                        goLogin();
                    }
                }

                @Override
                public void onFail(String error) {
                    Log.i("error : ", error);
                    Toast.makeText(MyApplication.getContext(), "로그인 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    goLogin();
                }
            });
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goLogin();
//                    goMain();
                }
            }, 1000);
        }
    }
    Handler mHandler = new Handler(Looper.getMainLooper());
    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void goLogin() {
        startActivity(new Intent(this, LogInActivity.class));
        finish();
    }
}
