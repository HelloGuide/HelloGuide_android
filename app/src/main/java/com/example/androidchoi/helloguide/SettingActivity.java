package com.example.androidchoi.helloguide;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.androidchoi.helloguide.Manager.MyApplication;
import com.example.androidchoi.helloguide.Service.RecoBackgroundMonitoringService;

public class SettingActivity extends AppCompatActivity {

    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Setting Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_setting);
        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white));
        setSupportActionBar(toolbar);
        // home 버튼 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()
                .setHomeAsUpIndicator(R.drawable.icon_back);

        // Beacon Background Monitoring 설정을 위한 ToggleButton
        mToggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("SettingActivity", "onMonitoringToggleButtonClicked on to off");
                    Intent intent = new Intent(MyApplication.getContext(), RecoBackgroundMonitoringService.class);
                    startService(intent);
                } else {
                    Log.i("SettingActivity", "onMonitoringToggleButtonClicked off to on");
                    stopService(new Intent(MyApplication.getContext(), RecoBackgroundMonitoringService.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.isBackgroundMonitoringServiceRunning(this)) {
            mToggleButton.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private boolean isBackgroundMonitoringServiceRunning(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo runningService : am.getRunningServices(Integer.MAX_VALUE)) {
            if(RecoBackgroundMonitoringService.class.getName().equals(runningService.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
