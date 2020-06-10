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
    TextView tvSplashPicOnlyP0_56;
    TextView tvSplashTopPicP0_56;
    TextView tvBannerP4_26;
    TextView tvBannerLeftPicP1_56;
    TextView tvBannerP10_67;

    TextView tvInterstitialPic;
    TextView tvInfoFlowLeftPicP0_67;
    TextView tvInfoFlowBottomPicP1_78;
    TextView tvInfoFlowRightPicP0_78;
    TextView tvInfoFlowLeftPicP1_5;
    TextView tvInfoFlowTopPicP1_78;
    TextView tvInfoFlowRightPicP1_5;


    TextView tvBuoyAd;


    TextView tvInfoFlowLeftPic0_78;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvSplashPicOnlyP0_56 = findViewById(R.id.tv_splash_normal);
        tvSplashTopPicP0_56 = findViewById(R.id.tv_splash_top_pic_p0_7);
        tvBannerP4_26 = findViewById(R.id.tv_banner_p4_26);
        tvBannerLeftPicP1_56 = findViewById(R.id.tv_banner_left_pic_p1_56);
        tvBannerP10_67 = findViewById(R.id.tv_banner_p10_67);
        tvInterstitialPic = findViewById(R.id.tv_interstitial);
        tvInfoFlowLeftPicP0_67 = findViewById(R.id.tv_info_flow_left_pic_p0_67);
        tvInfoFlowBottomPicP1_78 = findViewById(R.id.tv_info_flow_bottom_pic_p1_78);
        tvInfoFlowRightPicP0_78 = findViewById(R.id.tv_info_flow_right_pic_p0_78);
        tvInfoFlowLeftPic0_78 = findViewById(R.id.tv_info_flow_left_pic_0_78);
        tvInfoFlowLeftPicP1_5 = findViewById(R.id.tv_info_flow_left_pic_p1_5);
        tvInfoFlowTopPicP1_78 = findViewById(R.id.tv_info_flow_top_pic_1_78);
        tvInfoFlowRightPicP1_5 = findViewById(R.id.tv_info_flow_right_pic_p1_5);
        tvBuoyAd = findViewById(R.id.tv_buoy_ad);
        tvSplashTopPicP0_56.setOnClickListener(this);
        tvBuoyAd.setOnClickListener(this);
        tvBannerLeftPicP1_56.setOnClickListener(this);
        tvBannerP4_26.setOnClickListener(this);
        tvBannerP10_67.setOnClickListener(this);
        tvInterstitialPic.setOnClickListener(this);
        tvInfoFlowLeftPicP0_67.setOnClickListener(this);
        tvInfoFlowLeftPicP1_5.setOnClickListener(this);
        tvInfoFlowTopPicP1_78.setOnClickListener(this);
        tvInfoFlowLeftPic0_78.setOnClickListener(this);
        tvInfoFlowBottomPicP1_78.setOnClickListener(this);
        tvInfoFlowRightPicP0_78.setOnClickListener(this);
        tvInfoFlowRightPicP1_5.setOnClickListener(this);
        tvSplashPicOnlyP0_56.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_splash_normal:
                startActivity(new Intent(MainActivity.this, FullScreenSplashActivity.class));
                break;
            case R.id.tv_splash_top_pic_p0_7:
                startActivity(new Intent(MainActivity.this, TopPicP_07SplashActivity.class));
                break;
            case R.id.tv_banner_p10_67:
                Intent intentBannerP10_67 = new Intent(MainActivity.this, BannerActivity.class);
                intentBannerP10_67.putExtra("codeId", "820001");
                startActivity(intentBannerP10_67);
                break;
            case R.id.tv_banner_left_pic_p1_56:
                Intent intentBannerLeftPicP1_56 = new Intent(MainActivity.this, BannerActivity.class);
                intentBannerLeftPicP1_56.putExtra("codeId", "820004");
                startActivity(intentBannerLeftPicP1_56);
                break;
            case R.id.tv_banner_p4_26:
                Intent intentBannerP4_26 = new Intent(MainActivity.this, BannerActivity.class);
                intentBannerP4_26.putExtra("codeId", "820005");
                startActivity(intentBannerP4_26);
                break;
            case R.id.tv_interstitial:
                Intent intent2 = new Intent(MainActivity.this, InterstitialActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_info_flow_left_pic_p0_67:
                Intent intentInfoFlowLeftPicP0_67 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowLeftPicP0_67.putExtra("codeId", "850002");
                startActivity(intentInfoFlowLeftPicP0_67);
                break;
            case R.id.tv_info_flow_bottom_pic_p1_78:
                Intent intentInfoFlowBottomPicP1_78 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowBottomPicP1_78.putExtra("codeId", "850006");
                startActivity(intentInfoFlowBottomPicP1_78);
                break;
            case R.id.tv_info_flow_right_pic_p0_78:
                Intent intentInfoFlowRightPicP0_78 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowRightPicP0_78.putExtra("codeId", "850007");
                startActivity(intentInfoFlowRightPicP0_78);
                break;
            case R.id.tv_info_flow_left_pic_0_78:
                Intent intent1 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intent1.putExtra("codeId", "850008");
                startActivity(intent1);
                break;
            case R.id.tv_info_flow_top_pic_1_78:
                Intent intentInfoFlowTopPicP1_5 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowTopPicP1_5.putExtra("codeId", "850009");
                startActivity(intentInfoFlowTopPicP1_5);
                break;
            case R.id.tv_info_flow_left_pic_p1_5:
                Intent intentInfoFlowLeftPicP1_5 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowLeftPicP1_5.putExtra("codeId", "850010");
                startActivity(intentInfoFlowLeftPicP1_5);
                break;
            case R.id.tv_info_flow_right_pic_p1_5:
                Intent intentInfoFlowRightPicP1_5 = new Intent(MainActivity.this, InfoFlowActivity.class);
                intentInfoFlowRightPicP1_5.putExtra("codeId", "850011");
                startActivity(intentInfoFlowRightPicP1_5);
                break;
            case R.id.tv_buoy_ad:
                startActivity(new Intent(MainActivity.this, BuoyAdActivity.class));
                break;


        }
    }
}