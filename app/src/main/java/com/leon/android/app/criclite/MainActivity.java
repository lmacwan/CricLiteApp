package com.leon.android.app.criclite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private boolean isShown = false;
    private boolean isWelcomeAdInitialized = false;
    private AdRequest adRequest;

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            mAdView = (AdView) findViewById(R.id.fullScreenAdView);
            adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            mAdView.loadAd(adRequest);
            isWelcomeAdInitialized = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler(Looper.getMainLooper()) ;
        handler.postDelayed(task, 1000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isShown) {
            isShown = true;
        }
    }

    @Override
    public void onPause() {
        if (isWelcomeAdInitialized && mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (isWelcomeAdInitialized && mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (isWelcomeAdInitialized && mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
