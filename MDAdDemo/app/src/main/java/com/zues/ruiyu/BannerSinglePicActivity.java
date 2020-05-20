package com.zues.ruiyu;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zues.sdk.yq.AdSlot;
import com.zues.sdk.yq.MDAdLoadHelper;
import com.zues.sdk.yq.MDAdLoadListener;

import com.zues.sdk.yq.MDAdModel;
import com.zues.sdk.yq.MDSinglePicView;

import widget.RYTitleView;

/**
 * banner单图
 */
public class BannerSinglePicActivity extends AppCompatActivity {
    private RYTitleView mTitleView;
    private MDSinglePicView mSinglePicView;
    private RelativeLayout rlContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        initView();

    }

    private void initView() {
        mTitleView = findViewById(R.id.ry_title_view);
        mSinglePicView = findViewById(R.id.ry_single_pic_view);
        //xml中添加或者java中动态添加view皆可
        mSinglePicView = new MDSinglePicView(BannerSinglePicActivity.this,null);
        rlContainer = findViewById(R.id.rl_container);
        mTitleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setTitleText("banner广告展示");
        requestAd();
    }

    private void requestAd() {
        //广告位宽高比640:150,slotType为广告类型，传3请求非互动广告，传4请求互动广告
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("820005")
                .build();
        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //此处回调是在子线程
                Looper.prepare();
                Toast.makeText(BannerSinglePicActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel adModel) {

                //show方法有两个重载，可以根据需要决定是否设置监听，其他广告位同理。
                //此回调是在子线程，不能直接addView。而show()方法中做了线程切换处理，可以直接调用addView()方法添加view
                mSinglePicView.show(BannerSinglePicActivity.this, adModel, new MDAdLoadListener() {
                    @Override
                    public void onAdShow() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        rlContainer.addView(mSinglePicView);
                        //注意如果是在java代码中动态添加view,那么快速适配方法需要在addView()之后调用
                        mSinglePicView.setUpWithDefaultScale(true, 0,0,0);
                    }


                    @Override
                    public void onAdClicked() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告被关闭", Toast.LENGTH_SHORT).show();
                    }
                });








            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
