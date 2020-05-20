package com.zues.ruiyu;



import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;



/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvBannerOnlyPic;
    TextView tvBannerLeftPic;
    TextView tvInfoFlowLeftPic;
    TextView tvInfoFlowRightPic;
    TextView tvInfoFlowBottomPic;
    TextView tvBuoyAd;
    TextView tvSplashNormal;
    TextView tvInterstitialPic;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvBannerOnlyPic = findViewById(R.id.tv_banner_only_pic);
        tvBannerLeftPic = findViewById(R.id.tv_banner_left_pic);
        tvInfoFlowLeftPic = findViewById(R.id.tv_info_flow_left_pic);
        tvInfoFlowRightPic = findViewById(R.id.tv_info_flow_right_pic);
        tvInfoFlowBottomPic = findViewById(R.id.tv_info_flow_bottom_pic);
        tvSplashNormal = findViewById(R.id.tv_splash_normal);
        tvBuoyAd = findViewById(R.id.tv_buoy_ad);
        tvInterstitialPic = findViewById(R.id.tv_interstitial);
        tvBuoyAd.setOnClickListener(this);
        tvBannerOnlyPic.setOnClickListener(this);
        tvBannerLeftPic.setOnClickListener(this);
        tvInfoFlowLeftPic.setOnClickListener(this);
        tvInfoFlowRightPic.setOnClickListener(this);
        tvInfoFlowBottomPic.setOnClickListener(this);
        tvInterstitialPic.setOnClickListener(this);
        tvSplashNormal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_banner_only_pic:
                startActivity(new Intent(MainActivity.this, BannerSinglePicActivity.class));
                break;
            case R.id.tv_info_flow_left_pic:
                startActivity(new Intent(MainActivity.this,InfoFlowLeftPicActivity.class));
                break;
            case R.id.tv_buoy_ad:
                startActivity(new Intent(MainActivity.this,BuoyAdActivity.class));
                break;

            case R.id.tv_splash_normal:
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_info_flow_bottom_pic:
                startActivity(new Intent(MainActivity.this, InfoFlowBottomPicActivity.class));
                break;
            case R.id.tv_interstitial:
                Intent intent2 = new Intent(MainActivity.this,InterstitialActivity.class);
                startActivity(intent2);
                break;


        }
    }
}