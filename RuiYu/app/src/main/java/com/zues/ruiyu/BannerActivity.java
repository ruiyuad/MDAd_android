package com.zues.ruiyu;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.zues.sdk.yq.AdSlot;
import com.zues.sdk.yq.MDAdLoadHelper;
import com.zues.sdk.yq.MDAdLoadListener;

import com.zues.sdk.yq.MDAdModel;
import com.zues.sdk.yq.mdview.MDBannerLeftPicP1_56View;
import com.zues.sdk.yq.mdview.MDBannerP10_67View;
import com.zues.sdk.yq.mdview.MDBannerP4_26View;

import widget.RYTitleView;

/**
 * banner单图
 */
public class BannerActivity extends AppCompatActivity {
    private RYTitleView mTitleView;
    private MDBannerP4_26View mdBannerP4_26View;
    private MDBannerP10_67View mdBannerP10_67View;
    private MDBannerLeftPicP1_56View mdBannerLeftPicP1_56View;
    private RelativeLayout rlContainer;
    private String codeId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        codeId = getIntent().getStringExtra("codeId");
        initView();

    }

    private void initView() {
        mTitleView = findViewById(R.id.ry_title_view);
        rlContainer = findViewById(R.id.rl_container);
        mTitleView.setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        requestAd();
    }

    private void requestAd() {
        //广告位宽高比640:150,slotType为广告类型
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .build();
         MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //此处回调是在子线程
                Looper.prepare();
                Toast.makeText(BannerActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel adModel) {
                switch (codeId) {
                    case "820001":
                        //注意此处Context必须传Activity，不能传其他的。
                        mdBannerP10_67View = new MDBannerP10_67View(BannerActivity.this);
                        //show方法有两个重载，可以根据需要决定是否设置监听，其他广告位同理。
                        // 这里的回调本质上也是在子线程，但是内部已经做了线程切换处理，可以直接进行UI操作。
                        mdBannerP10_67View.show(adModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("banner-单图10.67展示");
                                Toast.makeText(BannerActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                                rlContainer.addView(mdBannerP10_67View);
                                //广告位默认高比为640:150。该方法可以用于快速适配
                                mdBannerP10_67View.setUpWithDefaultScale(true, 0, 0, 0);
                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;
                    case "820004":

                        mdBannerLeftPicP1_56View = new MDBannerLeftPicP1_56View(BannerActivity.this);
                        mdBannerLeftPicP1_56View.show(adModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("banner-左图右文1.56展示");
                                rlContainer.addView(mdBannerLeftPicP1_56View);
                                mdBannerLeftPicP1_56View.setUpWithDefaultScale(true, 0, 12, 12);
                                mdBannerLeftPicP1_56View.setTitleColor("#111111");
                                mdBannerLeftPicP1_56View.setDescColor("#666666");
                                mdBannerLeftPicP1_56View.setTitleSize(16);
                                mdBannerLeftPicP1_56View.setDescSize(12);
                                mdBannerLeftPicP1_56View.setTitlePadding(12,4,0,2);
                                mdBannerLeftPicP1_56View.setDownLoadBtn(12,5,2,5,2);

                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;

                    case "820005":

                        mdBannerP4_26View = new MDBannerP4_26View(BannerActivity.this);
                        mdBannerP4_26View.show(adModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("banner-单图4.26展示");
                                rlContainer.addView(mdBannerP4_26View);
                                mdBannerP4_26View.setUpWithDefaultScale(true, 0, 0, 0);
                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
