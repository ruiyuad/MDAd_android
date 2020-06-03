
MDAdSDK用于Android端移动应用广告展示，在使用广告服务的同时，我们真诚希望收到大家的反馈。

## 环境支持
        最低支持安卓API 15。
    

## 集成方式
 手动集成：
1. 下载本SDK 压缩包内的 MDAdsdk.aar文件复制到项目AndroidStudio的libs目录下（如若没有则在app目录下新建一个libs目录）。
2. 在项目的guild.gradle文件里添加依赖
    
```
repositories {
    flatDir {
        dirs 'libs'
    }
}
 dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])
        implementation files( 'libs\\MDAdsdk.aar')
    }
```
## 全局配置
- 代码混淆配置：
 如果您需要使用 proguard 混淆代码，需确保不要混淆 SDK 的代码。 请在 proguard.cfg 文件(或其他混淆文件)尾部添加如下配置:

```
-keep @com.qihoo.SdkProtected.yqsdk.Keep class **{*;}
-keep,allowobfuscation @interface com.qihoo.SdkProtected.yqsdk.Keep
-keep class com.zues.sdk.** { *; }
```
- 安卓P适配
    由于Android P 将禁止 App 使用所有未加密的连接，如果未对APP进行适配会可能会导致落地页加载失败。适配方法：
    在manifest的application标签中加入 android:usesCleartextTraffic="true"。
    
## 初始化



使用本SDK前请先确认已注册 AppKey 和 AppSecret. 如有疑问, 请联系我们 admin@mobiw.com 或者当前 github 账号。
1. 如已注册 AppKey 和 AppSecret, 请在应用的application中做如下初始化：
```
    MDAdSdk.init(BuildConfig.APPKEY,BuildConfig.APPSECRET,this);
```
2. 为更好的享受我们的广告服务，需要在AndroidManifest.xml文件里配置以下权限和provider

```
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

    <uses-permission android:name="android.permission.INTERNET"/>

    <uses-permission android:name="android.permission.REORDER_TASKS"/>
    
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```
3. 在manifest文件里配置provider,并在res目录下新建xml目录，然后建立file_paths文件。
manifest文件里代码如下：

```
  <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
```
file_paths代码如下：
```
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path name="tt_external_root" path="." />
    <external-path name="tt_external_download" path="Download" />
    <external-files-path name="tt_external_files_download" path="Download" />
    <files-path name="tt_internal_file_download" path="Download" />
    <cache-path name="tt_internal_cache_download" path="Download" />
</paths>
```
## 广告样式
目前支持5种广告样式，分别是**横幅广告**、**信息流广告**、**开屏广告**、**浮标广告**以及**插屏广告**。各种广告具体效果和用法如下：

### 横幅广告
横幅广告又称为 Banner，通常展示在App页面的顶部或者底部。目前支持的Banner广告有三种，分别是  

1.banner单图10.67（广告ID 820001，广告位宽高比640：60） 
2.banner左图右文1.56（广告ID 820004，广告位宽高比690：100） 
3.banner单图4.26（广告位ID 820005，广告位宽高比640：150） 

开发者接入时需要申请相应广告ID，这三种banner广告详细集成过程请参考DEMO中的BannerActivity。 

banner单图10.67和banner单图4.26集成过程类似，本SDK提供了相应的控件（MDBannerP10_67View和MDBannerP4_26View），下边以banner单图10.67为例简单介绍集成步骤： 
1.实例化控件
~~~
//注意此处的Context必须传Activity，不能传其他的。
mdBannerP10_67View = new MDBannerP10_67View(BannerActivity.this);
~~~
2.构建广告信息,传入广告ID。
```
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("820005")
                .build();
```
3. 使用MDAdLoadHelper.getInstance().requestAd()方法请求广告。该方法会回调成功和失败两种状态，都是回调在子线程，如果需要UI操作请务必先切换线程。其他各个广告位同理。
~~~
 MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {

            @Override
            public void onError(int i, String s) {
                //注意回调是在子线程，不能直接toast
                Looper.prepare();
                Toast.makeText(BannerActivity.this, msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onSuccess(MDAdModel mdAdModel) {

            }
        });
~~~
4. 在onSuccess()方法中调用show()方法显示广告并在onAdShow()回调中进行广告位UI适配。
```
            @Override
            public void onSuccess(MDAdModel adModel) {
               // 这里的回调本质上也是在子线程，但是内部已经做了线程切换处理，可以直接进行UI操作。
                        mdBannerP10_67View.show(adModel, new MDAdLoadListener() {
                            @Override
                            public void onAdClicked() {

                            }

                            @Override
                            public void onAdShow() {
                                Toast.makeText(BannerActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                                rlContainer.addView(mdBannerP10_67View);
                                
                                //该方法用于快速适配，必须要在addView()之后调用。
                                mdBannerP10_67View.setUpWithDefaultScale(true, 0, 0, 0);
                            }

                            @Override
                            public void onRenderFailed() {

                            }

                            @Override
                            public void onAdClosed() {

                            }
                        });
```
 5.setUpWithDefaultScale()方法介绍：
~~~
/**
     * 广告位快速适配 
     * 注意此方法目前只支持父布局为LinearLayout、RelativeLayout和FrameLayout
     *
     * @param isSetUpWithDefaultScale true表示按照默认比例适配。false表示采用XML中定义的宽高。
     * @param width                   自定义的广告位宽度，单位px,高度将会根据比例自适应，宽度为0时表示全屏。
     * @param paddingLeft             单位dp,下同
     * @param paddingRight 
     */
    public void setUpWithDefaultScale(boolean isSetUpWithDefaultScale, int width, int paddingLeft, int paddingRight) {
    
~~~
banner左图右文1.56与上述两个banner广告集成步骤基本相同，但是多了几个UI适配的方法，详细集成流程请参考DEMO，此处仅对增加的几个UI适配方法做介绍：
UI适配示范如下：
~~~
       @Override
    public void onAdShow() {
        //注意先要调用addView()方法把控件添加到容器中。
        rlContainer.addView(mdBannerLeftPicP1_56View);
        mdBannerLeftPicP1_56View.setUpWithDefaultScale(true, 0, 12, 12);
        //我们为所有左右图文系列的广告view都增加了可以设置文字大小颜色和边距的方法，开发者可以按需自行调用。
        mdBannerLeftPicP1_56View.setTitleColor("#111111");
        mdBannerLeftPicP1_56View.setDescColor("#666666");
        mdBannerLeftPicP1_56View.setTitleSize(16);
        mdBannerLeftPicP1_56View.setDescSize(13);
        mdBannerLeftPicP1_56View.setTitlePadding(12,4,0,2);
        mdBannerLeftPicP1_56View.setDownLoadBtn(11,5,2,5,2);
    }

~~~
设置文字padding方法如下，设置下载按钮的方法如下。其他左右图文的广告位同理。
~~~
    /**
     * padding设置，单位dp
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setTitlePadding(int left, int top, int right, int bottom) {...}


        /**
     * 设置下载按钮的字体和padding。
     * @param size   字体大小，单位sp
     * @param left   单位dp
     * @param top
     * @param right
     * @param bottom
     */
    public void setDownLoadBtn(float size, int left, int top, int right, int bottom) {...}


~~~

### 信息流广告
信息流广告用于展示在信息流列表中，sdk中提供了多种信息流广告样式，具体分类如下：
1.信息流左图右文0.67（广告ID850002，广告位宽高比690：290，对应view为MDInfoFlowLeftPicP0_67View）
2.信息流左图右文0.78（广告ID850008，广告位宽高比690：290，对应view为MDInfoFlowLeftPicP0_78View）
3.信息流左图右文1.5（广告ID850010，广告位宽高比640：168，对应view为MDInfoFlowLeftPicP1_5View）
4.信息流左文右图0.78（广告ID850007，广告位宽高比690：290，对应view为MDInfoFlowRightPicP0_78View）
5.信息流左文右图1.5（广告ID850011，广告位宽高比640：168，对应view为MDInfoFlowRightPicP1_5View）
6.信息流上图下文1.78（广告ID850009，广告位宽高比690：440，对应view为MDInfoFlowTopPicP1_78View）
6.信息流上文下图1.78（广告ID850006，广告位宽高比690：440，对应view为MDInfoFlowBottomPicP1_78View）

开发者接入时需要申请相应广告ID，信息流广告详细集成过程请参考DEMO中的InfoFlowActivity,集成步骤如下：
#### 左右图文系列(以信息流左图右文0.67为例)：
    1.实例化控件
    ~~~
        //注意此处Context必须传Activity，不能传其他的。
       mdInfoFlowLeftPicP0_67View = new MDInfoFlowLeftPicP0_67View(InfoFlowActivity.this); 
    ~~~
    2.构建广告信息,传入广告ID。
```
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("820005")
                .build();
  ```
    3.使用MDAdLoadHelper.getInstance().requestAd()方法请求广告
    4. 在onSuccess()方法中调用show()方法显示广告并在onAdShow()回调中进行广告位UI适配。
```
         @Override
        public void onAdShow() {
        //先调用addView()再进行UI适配。
        rlContainer.addView(mdInfoFlowLeftPicP0_67View);
        mdInfoFlowLeftPicP0_67View.setUpWithDefaultScale(true, 0, 12, 12);
        //文字的颜色尺寸和位置可按需微调。
        mdInfoFlowLeftPicP0_67View.setTitleColor("#111111");
        mdInfoFlowLeftPicP0_67View.setTitleSize(24);
        mdInfoFlowLeftPicP0_67View.setTitlePadding(12,6,0,6);
        mdInfoFlowLeftPicP0_67View.setDescColor("#666666");
        mdInfoFlowLeftPicP0_67View.setDescSize(15);
        mdInfoFlowLeftPicP0_67View.setDescPadding(12, 0, 0, 0);
        mdInfoFlowLeftPicP0_67View.setDownLoadBtn(11,5,2,5,2);

        }
``` 
    5.setUpWithDefaultScale()等UI适配方法请参考banner广告中的介绍
#### 上下图文系列：
    上下图文系列基本集成步骤与左右图文系列相同。不过上下图文系列内置了MDAutoFitHeightTextView，文字大小会根据广告位的大小自行适配，开发者只需决定广告位宽度即可。

### 开屏广告
开屏广告又通常用于 App 启动或者从一个页面过渡到另一个页面的场景中。sdk中提供了全屏（广告ID810001）和上图下logo（广告ID810002）两种样式。开发者在接入时需要申请相应广告ID。
集成开屏广告单图0.56详细流程请参考DEMO中的FullScreenSplashActivity,步骤如下:
1. 实例化控件MDSplashFullScreenP0_56View
~~~
    //注意此处Context必须传Activity，不能传其他的。
    mdSplashFullScreenP0_56View = new MDSplashFullScreenP0_56View(FullScreenSplashActivity.this);
~~~
2. 在onCreate()方法中延迟发送消息，确保即使开屏广告加载超时也能跳转到主页面。
~~~
    myHandler.sendEmptyMessageDelayed(1, countTime);
~~~
3. 构建广告信息并请求广告。
~~~
    AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("810001")
                .build();
    MDAdLoadHelper.getInstance().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {});
~~~
4. 在onSuccess()方法中调用show()方法展示广告。各个回调处需要处理自动跳转相关逻辑，代码如下：
~~~
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
~~~
集成开屏广告上图下logo0.7过程与之类似，详细流程请参考DEMO中的TopPicP_07SplashActivity,这里对注意事项进行重点描述:
1.开屏广告上图下logo的底部logo部分需要开发者自定义，logo部分高度为屏幕高度的1/3，需要在onCreate()中进行设置。
~~~
     // 底部高度为屏幕宽度的1/3
        float screenWidth = MDDeviceHelper.getScreenWidth();
        float bottomHeight = (float) (1.0 / 3 * MDDeviceHelper.getScreenWidth());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) screenWidth, (int) (bottomHeight));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlLogo.setLayoutParams(layoutParams);
~~~
2.logo之上就是我们的广告位。由于logo和广告位的高度都是动态计算的，所以MDSplashTopPicP0_7View的父容器需要预留足够的高度。建议开发者按照DEMO中的xml布局即可。
3.成功请求到广告之后，在onAdShow()回调中调用setUpWithDefaultScale()方法来设置我们的广告位的高度。
~~~
       @Override
                    public void onAdShow() {
                        Toast.makeText(TopPicP_07SplashActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        //请求到广告后外部自动跳转逻辑取消
                        myHandler.removeMessages(1);
                        for (int i = 5; i > 0; i--) {
                            myHandler.sendEmptyMessageDelayed(0, i * 1000);
                        }
                        rlContainer.addView(mdSplashTopPicP0_7View);
                        //此处参数传-1，同样该方法需要在addView()之后调用。
                        mdSplashTopPicP0_7View.setUpWithDefaultScale(-1);
                    }

~~~
    


### 浮标广告
浮标广告通常展示在页面的边角位置，是五种广告中展示面积最小的一种。你可以使用MDBuoyP1View控件来实现浮标广告的展示。SDK中提供浮标广告尺寸宽高比为 110 : 110。 在使用该广告之前, 你需要申请浮标广告的广告 ID. 浮标广告步骤如下:
1. 实例化控件MDBuoyView。
    ~~~
    mBuoyView = findViewById(R.id.ry_buoy_view);
    ~~~
2. 构建广告信息并请求广告。
3. 在onSuccess()方法中调用show()方法展示广告。
    ~~~
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
                        //设置cancel按钮比例
                        mBuoyView.setIvCancelWeight(0.2);
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
    ~~~

### 插屏广告
插屏广告用于展示插屏广告. 通常用于 App 屏幕中间展示. 你可以使用 MDInterstitialView 类来实现插屏广告的展示. SDK中提供插屏广告尺寸宽高比为 510 : 510. 在使用该广告之前, 你需要申请插屏广告的广告 ID. 集成插屏广告的简单示例如下:
1. 实例化控件MDInterstitialView 。MDInterstitialView 是一个DialogFragment,不要在XML中添加，必须在java代码中实例化。
```
    mMDInterstitialView = new MDInterstitialView();
```
2. 构建广告信息并请求广告。 
 ```
AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("840001")
                .build();
        new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {
```
3. 在onSuccess()方法中调用show()方法展示广告。
## 常见问题

Q: 加载广告为什么不显示?

A: 请检查对应广告 ID 是否正确.


Q: 为什么出现部分广告点击后没有发生跳转?

A: rootViewController 未设置. rootViewController 用来弹出落地页或者执行广告跳转操作.


Q: 广告样式是否可以自定义?

A: 可以. 开发者只需传入需要自定义广告的 ID, 即可获取该广告的图片、标题和广告描述来实现广告UI的自定义。


Q: 接入时出现了 40006, 50001 错误返回. 请问这是什么错误?

A: 下面分别介绍两个错误码含义:

    40006 是当前广告位已经关闭. 该错误一般发生后台重复配置删除同一广告位, 进行广告展示前, 请确定对应的广告位是否开启.
    50001 表示当前广告位广告无法提供.

Q: 接入时出现couldn't find "libjagu_sdk_yqsdkProtected.so"等类似错误怎么回事？

A：跟AndroidStudio编译器有关，尝试clean整个项目，然后gradle中删除对本aar的引用，再clean项目，再重新引用。


## 错误码
对于接入过程中的错误码详情, 请查看[错误码文档](https://github.com/ruiyuad/MDAd_android/blob/master/%E9%94%99%E8%AF%AF%E7%A0%81.md)
## 版本日志
0.1.0: 接入五种常用广告。 

0.1.1: 优化广告展示样式。

0.1.2：自定义view构造方法优化





