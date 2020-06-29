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
import com.zues.sdk.yq.mdview.MDInfoFlowBottomPicP1_78View;
import com.zues.sdk.yq.mdview.MDInfoFlowLeftPicP0_67View;
import com.zues.sdk.yq.mdview.MDInfoFlowLeftPicP0_78View;
import com.zues.sdk.yq.mdview.MDInfoFlowLeftPicP1_5View;
import com.zues.sdk.yq.mdview.MDInfoFlowRightPicP0_78View;
import com.zues.sdk.yq.mdview.MDInfoFlowRightPicP1_5View;
import com.zues.sdk.yq.mdview.MDInfoFlowTopPicP1_78View;

import widget.RYTitleView;

/**
 * 左图右文信息流展示
 */
public class InfoFlowActivity extends AppCompatActivity {

    RYTitleView mTitleView;
    private RelativeLayout rlContainer;
    MDInfoFlowLeftPicP1_5View mdLeftPicWithTextViewP1_5;
    MDInfoFlowLeftPicP0_67View mdInfoFlowLeftPicP0_67View;
    MDInfoFlowLeftPicP0_78View mdLeftPicWithText0_78View;
    MDInfoFlowBottomPicP1_78View mdInfoFlowBottomPicP1_78View;
    MDInfoFlowRightPicP0_78View mdInfoFlowRightPicP0_78View;
    MDInfoFlowTopPicP1_78View mdInfoFlowTopPicP1_78View;
    MDInfoFlowRightPicP1_5View mdInfoFlowRightPicP1_5View;


    String codeId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ry_info_flow_left_pic_activity);
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
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .build();
        MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
            @Override
            public void onError(int code, String msg) {
                //此处回调是在子线程，如若需要toast要先将消息发送到主线程中
                Looper.prepare();
                Toast.makeText(InfoFlowActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel mModel) {
                switch (codeId) {
                    case "850002":
                        mdInfoFlowLeftPicP0_67View = new MDInfoFlowLeftPicP0_67View(InfoFlowActivity.this);
                        mdInfoFlowLeftPicP0_67View.show(mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-左图右文0.67展示");
                                rlContainer.addView(mdInfoFlowLeftPicP0_67View);
                                mdInfoFlowLeftPicP0_67View.setUpWithDefaultScale(true, 0, 12, 12);

                                //文字的颜色尺寸和位置可按需调。
                                mdInfoFlowLeftPicP0_67View.setTitleColor("#2D2D2D");
                                mdInfoFlowLeftPicP0_67View.setTitleSize(24);
                                mdInfoFlowLeftPicP0_67View.setTitlePadding(12,24,0,6);
                                mdInfoFlowLeftPicP0_67View.setDescColor("#2D2D2D");
                                mdInfoFlowLeftPicP0_67View.setDescSize(15);
                                mdInfoFlowLeftPicP0_67View.setDescPadding(12, 0, 0, 0);
                                mdInfoFlowLeftPicP0_67View.setDownLoadBtn(12,5,2,5,2);

                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;
                    case "850006":

                        mdInfoFlowBottomPicP1_78View = new MDInfoFlowBottomPicP1_78View(InfoFlowActivity.this);
                        mdInfoFlowBottomPicP1_78View.show(mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-上文下图1.78展示");
                                rlContainer.addView(mdInfoFlowBottomPicP1_78View);
                                mdInfoFlowBottomPicP1_78View.setUpWithDefaultScale(true, 0, 12, 12);
                                mdInfoFlowBottomPicP1_78View.setTitleSize(16);

                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;
                    case "850007":

                        mdInfoFlowRightPicP0_78View = new MDInfoFlowRightPicP0_78View(InfoFlowActivity.this);
                        mdInfoFlowRightPicP0_78View.show(mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-左文右图0.78展示");
                                rlContainer.addView(mdInfoFlowRightPicP0_78View);
                                mdInfoFlowRightPicP0_78View.setUpWithDefaultScale(true, 0, 12, 12);


                                //文字的颜色尺寸和位置可按需调。
                                mdInfoFlowRightPicP0_78View.setTitleColor("#111111");
                                mdInfoFlowRightPicP0_78View.setTitleSize(18);
                                mdInfoFlowRightPicP0_78View.setTitlePadding(6,24,0,6);
                                mdInfoFlowRightPicP0_78View.setDescColor("#666666");
                                mdInfoFlowRightPicP0_78View.setDescSize(15);
                                mdInfoFlowRightPicP0_78View.setDescPadding(6, 0, 0, 0);


                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
                        break;
                    case "850008":

                        mdLeftPicWithText0_78View = new MDInfoFlowLeftPicP0_78View(InfoFlowActivity.this);
                        mdLeftPicWithText0_78View.show( mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-左图右文0.78展示");
                                mdLeftPicWithText0_78View.setCancelable(true);
                                rlContainer.addView(mdLeftPicWithText0_78View);
                                mdLeftPicWithText0_78View.setUpWithDefaultScale(true, 0, 12, 12);

                                //文字的颜色尺寸和位置可按需调。
                                mdLeftPicWithText0_78View.setTitleColor("#111111");
                                mdLeftPicWithText0_78View.setTitleSize(18);
                                mdLeftPicWithText0_78View.setDescSize(15);
                                mdLeftPicWithText0_78View.setDescPadding(12, 0, 0, 0);

                                Toast.makeText(InfoFlowActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRenderFailed() {
                                Toast.makeText(InfoFlowActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdClosed() {
                                Toast.makeText(InfoFlowActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "850009":

                        mdInfoFlowTopPicP1_78View = new MDInfoFlowTopPicP1_78View(InfoFlowActivity.this);
                        mdInfoFlowTopPicP1_78View.show(mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-上图下文1.78展示");
                                mdInfoFlowTopPicP1_78View.setCancelable(true);
                                rlContainer.addView(mdInfoFlowTopPicP1_78View);
                                mdInfoFlowTopPicP1_78View.setUpWithDefaultScale(true, 0, 12, 12);
                                mdInfoFlowTopPicP1_78View.setTitleSize(16);
                                mdInfoFlowTopPicP1_78View.setDownLoadBtn(11,5,2,5,2);
                                Toast.makeText(InfoFlowActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRenderFailed() {
                                Toast.makeText(InfoFlowActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdClosed() {
                                Toast.makeText(InfoFlowActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "850010":

                        mdLeftPicWithTextViewP1_5 = new MDInfoFlowLeftPicP1_5View(InfoFlowActivity.this);
                        mdLeftPicWithTextViewP1_5.show( mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-左图右文1.5展示");
                                mdLeftPicWithTextViewP1_5.setCancelable(true);
                                rlContainer.addView(mdLeftPicWithTextViewP1_5);
                                mdLeftPicWithTextViewP1_5.setUpWithDefaultScale(true, 0, 12, 12);
                                mdLeftPicWithTextViewP1_5.setTitleColor("#111111");
                                mdLeftPicWithTextViewP1_5.setTitleSize(16);
                                mdLeftPicWithTextViewP1_5.setTitlePadding(12,10,0,6);
                                mdLeftPicWithTextViewP1_5.setDescColor("#666666");
                                mdLeftPicWithTextViewP1_5.setDescSize(12);
                                mdLeftPicWithTextViewP1_5.setDescPadding(12, 0, 0, 0);

                                Toast.makeText(InfoFlowActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRenderFailed() {
                                Toast.makeText(InfoFlowActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdClosed() {
                                Toast.makeText(InfoFlowActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "850011":

                        mdInfoFlowRightPicP1_5View = new MDInfoFlowRightPicP1_5View(InfoFlowActivity.this);
                        mdInfoFlowRightPicP1_5View.show(mModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {
                                Toast.makeText(InfoFlowActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdShow() {
                                mTitleView.setTitleText("信息流-左文右图1.5展示");
                                mdInfoFlowRightPicP1_5View.setCancelable(true);
                                rlContainer.addView(mdInfoFlowRightPicP1_5View);
                                mdInfoFlowRightPicP1_5View.setUpWithDefaultScale(true, 0, 12, 12);
                                mdInfoFlowRightPicP1_5View.setTitleColor("#111111");
                                mdInfoFlowRightPicP1_5View.setTitleSize(16);
                                mdInfoFlowRightPicP1_5View.setTitlePadding(6,10,0,6);
                                mdInfoFlowRightPicP1_5View.setDescColor("#666666");
                                mdInfoFlowRightPicP1_5View.setDescSize(12);
                                mdInfoFlowRightPicP1_5View.setDescPadding(6, 0, 0, 0);

                                Toast.makeText(InfoFlowActivity.this, "广告展示", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRenderFailed() {
                                Toast.makeText(InfoFlowActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdClosed() {
                                Toast.makeText(InfoFlowActivity.this, "广告关闭", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    default:
                        break;
                }

            }

        });
    }


}
