package com.example.androidchoi.helloguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        String id = PropertyManager.getInstance().getId();
//        if (!TextUtils.isEmpty(id)) {
//            String password = PropertyManager.getInstance().getPassword();
//            NetworkManager.getInstance().login(getApplicationContext(), id, password, new NetworkManager.OnResultListener<LoginData>() {
//                @Override
//                public void onSuccess(LoginData result) {
//                    if (result.getMessage().equals(LoginFragment.MESSAGE_SUCCESS)) {
//                        User.getInstance().setUser(result.getUserId(), result.getName());
//                        goMain();
//                    } else {
//                        goLogin();
//                    }
//                }
//                @Override
//                public void onFail(int code) {
//                    Log.i("error : ", code + "");
//                    Toast.makeText(MyApplication.getContext(), "요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//                    goLogin();
//                }
//            });
//        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    goLogin();
                    goMain();
                }
            }, 1000);
//        }
    }
    Handler mHandler = new Handler(Looper.getMainLooper());
    private void goMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
//    private void goLogin() {
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
//    }
}
