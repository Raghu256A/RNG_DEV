package com.rng_dev_info.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rng_dev_info.BaseActivity;
import com.rng_dev_info.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    ProgressBar progressBar=null;
    TextView tv_APPLICATION_VERSION;
    Timer progress_time=null;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        progress_time = new Timer();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(R.drawable.progress_bar_bg);
        progressBar.isShown();
        tv_APPLICATION_VERSION = findViewById(R.id.version_tv);
        tv_APPLICATION_VERSION.setText("Version : v0.0.1");
        progress_time.scheduleAtFixedRate(timer, 0, 600);
    }
    TimerTask timer =new TimerTask() {
        @Override
        public void run() {
            count = (count + 10) % 101;

            if (count == 100) {
                startActivity();
                progress_time.cancel();
                finish();
            }


        }
    };

    private void startActivity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}