package com.zues.ruiyu;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.zues.sdk.yq.AdSlot;
import com.zues.sdk.yq.MDAdLoadHelper;
import com.zues.sdk.yq.MDAdLoadListener;

import com.zues.sdk.yq.MDAdModel;
import com.zues.sdk.yq.MDBuoyView;
import com.zues.sdk.yq.MDLog;


import widget.RYTitleView;

/**
 * 浮标广告
 */
public class BuoyAdActivity extends AppCompatActivity {

    private MDBuoyView mBuoyView;
    private RYTitleView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bouy_ad);
        initView();

    }

    private void initView() {
        mBuoyView = findViewById(R.id.ry_buoy_view);
        titleView = findViewById(R.id.ry_title_view);
        titleView.setTitleText("浮标广告展示");

        titleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      requestAd();
    }


    private void requestAd(){
        Log.e("TAG","start request");
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("860001")
                .build();
        //注意下边两个接口回调都是发生在子线程中的，不能直接进行UI操作。
        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //此处回调是在子线程，如若需要toast要先将消息发送到主线程中
                Looper.prepare();
                Toast.makeText(BuoyAdActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel MDAdModel) {
                //设置广告关闭按钮是否可见，默认可见
                mBuoyView.setCancelable(true);
                mBuoyView.show(BuoyAdActivity.this, MDAdModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(BuoyAdActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow() {
                        MDLog.e(System.currentTimeMillis()+"onAdShow");
                        Toast.makeText(BuoyAdActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                        //mBuoyViewInteraction.setUpWithDefaultScale(true,300);
                       // tvInteraciton.setText("互动,宽高比："+MDAdModel.getImageWidth()+":"+MDAdModel.getImageHeight());
                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(BuoyAdActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(BuoyAdActivity.this, "广告被关闭", Toast.LENGTH_SHORT).show();
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
