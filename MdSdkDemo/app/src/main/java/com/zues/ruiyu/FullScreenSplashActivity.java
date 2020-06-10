package com.zues.ruiyu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zues.sdk.yq.AdSlot;
import com.zues.sdk.yq.MDAdLoadHelper;
import com.zues.sdk.yq.MDAdLoadListener;
import com.zues.sdk.yq.MDAdModel;
import com.zues.sdk.yq.MDLog;
import com.zues.sdk.yq.mdview.MDSplashFullScreenP0_56View;

import java.lang.ref.WeakReference;

public class FullScreenSplashActivity extends AppCompatActivity {

    private MDSplashFullScreenP0_56View mdSplashFullScreenP0_56View;

    private RelativeLayout rlContainer;
    private TextView tvSkip;
    private static int countTime = 5000;
    private static int count = 4;
    //是否强制跳转到主页面
    private boolean mForceGoMain = false;
    private static final String TAG = "SplashActivity";

    int adType;
    MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<FullScreenSplashActivity> weakReference;

        MyHandler(FullScreenSplashActivity splashActivity) {

            weakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (weakReference.get() != null) {
                        if (count > 0) {
                            weakReference.get().tvSkip.setText(count + " 跳过");
                            count--;
                        } else {
                            count = 4;
                            weakReference.get().goToMain();
                        }
                    }
                    break;
                case 1:
                    if (weakReference.get() != null) {
                        weakReference.get().goToMain();
                    }
                    break;
                case 2:

                    break;
            }
        }
    }


    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
        //定时执行，即使开屏广告没有加载到时间了也跳转到主页面
        myHandler.sendEmptyMessageDelayed(1, countTime);
        requestSplashAd();

    }

    private void initView() {

        tvSkip = findViewById(R.id.tv_skip);
        rlContainer = findViewById(R.id.rl_container);
        tvSkip.setText("5 跳过");
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
    }

    private void requestSplashAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("810001")
                .build();
        MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //广告请求失败，此处回调是在子线程，如若需要toast要先将消息发送到主线程中
                goToMain();
                MDLog.e(msg);
                Looper.prepare();
                Toast.makeText(FullScreenSplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();

            }

            @Override
            public void onSuccess(MDAdModel adModel) {

                mdSplashFullScreenP0_56View = new MDSplashFullScreenP0_56View(FullScreenSplashActivity.this);
                mdSplashFullScreenP0_56View.show(FullScreenSplashActivity.this, adModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        myHandler.removeCallbacksAndMessages(null);
                        Toast.makeText(FullScreenSplashActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdShow() {

                        Toast.makeText(FullScreenSplashActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        //请求到广告后外部自动跳转逻辑取消
                        myHandler.removeMessages(1);
                        for (int i = 5; i > 0; i--) {
                            myHandler.sendEmptyMessageDelayed(0, i * 1000);
                        }
                        rlContainer.addView(mdSplashFullScreenP0_56View);

                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(FullScreenSplashActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();
                        goToMain();
                    }

                    @Override
                    public void onAdClosed() {

                    }
                });


            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }

    private void goToMain() {
        startActivity(new Intent(FullScreenSplashActivity.this, MainActivity.class));
    }


}
