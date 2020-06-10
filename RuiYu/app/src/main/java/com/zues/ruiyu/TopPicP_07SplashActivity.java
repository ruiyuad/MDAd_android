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
import com.zues.sdk.yq.MDDeviceHelper;
import com.zues.sdk.yq.MDLog;
import com.zues.sdk.yq.mdview.MDSplashTopPicP0_7View;

import java.lang.ref.WeakReference;

public class TopPicP_07SplashActivity extends AppCompatActivity {


    private MDSplashTopPicP0_7View mdSplashTopPicP0_7View;
    private RelativeLayout rlContainer;
    private RelativeLayout rlLogo;
    private TextView tvSkip;
    private static int countTime = 5000;
    private static int count = 4;
    //是否强制跳转到主页面
    private boolean mForceGoMain = false;
    private static final String TAG = "SplashActivity";
    MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<TopPicP_07SplashActivity> weakReference;

        MyHandler(TopPicP_07SplashActivity splashActivity) {

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
        setContentView(R.layout.activity_splash_top_pic_p07);
        initView();
        //定时执行，即使开屏广告没有加载到时间了也跳转到主页面
        myHandler.sendEmptyMessageDelayed(1, countTime);
        requestSplashAd();


    }

    private void initView() {

        tvSkip = findViewById(R.id.tv_skip);
        rlContainer = findViewById(R.id.rl_container);
        rlLogo = findViewById(R.id.rl_logo);
        tvSkip.setText("5 跳过");
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

       // 底部高度为屏幕宽度的1/3
        float screenWidth = MDDeviceHelper.getScreenWidth();
        float bottomHeight = (float) (1.0 / 3 * MDDeviceHelper.getScreenWidth());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) screenWidth, (int) (bottomHeight));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlLogo.setLayoutParams(layoutParams);

    }

    private void requestSplashAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("810002")
                .build();
        MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //广告请求失败，此处回调是在子线程，如若需要toast要先将消息发送到主线程中
                goToMain();
                MDLog.e(msg);
                Looper.prepare();
                Toast.makeText(TopPicP_07SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();

            }

            @Override
            public void onSuccess(MDAdModel adModel) {

                mdSplashTopPicP0_7View = new MDSplashTopPicP0_7View(TopPicP_07SplashActivity.this);
                mdSplashTopPicP0_7View.show(adModel, "mdsdk", R.drawable.icon_logo, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        myHandler.removeCallbacksAndMessages(null);
                        Toast.makeText(TopPicP_07SplashActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdShow() {
                        Toast.makeText(TopPicP_07SplashActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        //请求到广告后外部自动跳转逻辑取消
                        myHandler.removeMessages(1);
                        for (int i = 5; i > 0; i--) {
                            myHandler.sendEmptyMessageDelayed(0, i * 1000);
                        }
                        rlContainer.addView(mdSplashTopPicP0_7View);
                        //传-1表示自定义LOGO布局,底部高度为屏幕宽度的1/3
                        mdSplashTopPicP0_7View.setUpWithDefaultScale(-1);
                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(TopPicP_07SplashActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(TopPicP_07SplashActivity.this, MainActivity.class));
    }


}
