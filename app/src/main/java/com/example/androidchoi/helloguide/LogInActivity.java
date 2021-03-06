package com.example.androidchoi.helloguide;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.login_container, new LoginFragment()).commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.onOptionsItemSelected(item);
    }
}
