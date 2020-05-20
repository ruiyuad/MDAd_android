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
import com.zues.sdk.yq.MDLeftPicWithTextView;

import widget.RYTitleView;

/**
 * 左图右文信息流展示
 */
public class InfoFlowLeftPicActivity extends AppCompatActivity {

    RYTitleView mTitleView;
    private MDLeftPicWithTextView mPicWithTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ry_info_flow_left_pic_activity);
        initView();

    }

    private void initView() {

        mTitleView = findViewById(R.id.ry_title_view);
        mPicWithTextView = findViewById(R.id.ry_pic_with_text_view);

        mTitleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestAd();
    }

    private void requestAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("850010")
                .build();
        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //此处回调是在子线程，如若需要toast要先将消息发送到主线程中
                Looper.prepare();
                Toast.makeText(InfoFlowLeftPicActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel MDAdModel) {
                mPicWithTextView.show(InfoFlowLeftPicActivity.this, MDAdModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(InfoFlowLeftPicActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow() {
                        mPicWithTextView.setCancelable(true);
                        mPicWithTextView.setUpWithDefaultScale(true,0,12,12);
                        Toast.makeText(InfoFlowLeftPicActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(InfoFlowLeftPicActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(InfoFlowLeftPicActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }




}
