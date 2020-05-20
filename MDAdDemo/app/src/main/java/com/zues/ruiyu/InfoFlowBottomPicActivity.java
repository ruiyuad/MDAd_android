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
import com.zues.sdk.yq.MDBottomPicWthTextView;

import widget.RYTitleView;

/**
 * 信息流上文下图
 */
public class InfoFlowBottomPicActivity extends AppCompatActivity {
    private MDBottomPicWthTextView mBottomPicWithTextView;
    private RYTitleView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ry_info_flow_top_pic_activity);
        initView();
        requestAd();
    }

    private void initView() {

        titleView = findViewById(R.id.ry_title_view);
        mBottomPicWithTextView = findViewById(R.id.ry_bottom_pic_with_text_view);
        titleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitleText("信息流上文下图展示");
    }

    private void requestAd() {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("850006")
                .build();

        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                Looper.prepare();
                Toast.makeText(InfoFlowBottomPicActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel adModel) {

                mBottomPicWithTextView.show(InfoFlowBottomPicActivity.this, adModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(InfoFlowBottomPicActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow() {
                        mBottomPicWithTextView.setUpWithDefaultScale(true, 0, 12, 12);
                        Toast.makeText(InfoFlowBottomPicActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRenderFailed() {
                        Toast.makeText(InfoFlowBottomPicActivity.this, "广告加载失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(InfoFlowBottomPicActivity.this, "广告被关闭", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }



}
