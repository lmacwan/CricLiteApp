package com.leon.android.app.criclite;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private boolean isShown = false;
    private boolean isWelcomeAdInitialized = false;
    private Handler mHomeActivityHandler;
    private long mHomeActivityMillis = 6000;

    private AdView mAdView;
    private AdRequest adRequest;
    private ProgressBar mProgressBar;


    TimerTask adTask = new TimerTask() {
        @Override
        public void run() {
            mAdView = (AdView) findViewById(R.id.fullScreenAdView);
            adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            mAdView.loadAd(adRequest);
            isWelcomeAdInitialized = true;

            mHomeActivityHandler = new Handler();
            mHomeActivityHandler.postDelayed(homeActivityTask, mHomeActivityMillis);
        }
    };

    TimerTask homeActivityTask = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.fullScreenProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler(Looper.getMainLooper()) ;
        handler.postDelayed(adTask, 500);
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
