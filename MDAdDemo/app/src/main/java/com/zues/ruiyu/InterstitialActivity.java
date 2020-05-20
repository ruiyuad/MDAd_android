package com.zues.ruiyu;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.zues.sdk.yq.AdSlot;
import com.zues.sdk.yq.MDAdLoadHelper;
import com.zues.sdk.yq.MDAdLoadListener;
import com.zues.sdk.yq.MDAdModel;
import com.zues.sdk.yq.MDInterstitialView;

import widget.RYTitleView;

public class InterstitialActivity extends AppCompatActivity {

    private RYTitleView titleView;
    MDInterstitialView mMDInterstitialView;
    int type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        initView();
        requestAd();
    }

    private void initView() {
        titleView = findViewById(R.id.ry_title_view);
        mMDInterstitialView = new MDInterstitialView();
        titleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitleText("插屏广告展示");
    }





    private void requestAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("840001")
                .build();
        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {

            @Override
            public void onError(int code, String msg) {
                Looper.prepare();
                Toast.makeText(InterstitialActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel adModel) {
                mMDInterstitialView.show(getFragmentManager(),InterstitialActivity.this, adModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(InterstitialActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow() {

                        Toast.makeText(InterstitialActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        //PicView已经对比例做了适配。第二个参数可以自己设置
                        //mMDInterstitialView.setUpWithDefaultScale(true, 0);

                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(InterstitialActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(InterstitialActivity.this, "广告被关闭", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

}
