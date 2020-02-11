
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
## 广告样式
目前支持5种广告样式，分别是**横幅广告**、**信息流广告**、**开屏广告**、**浮标广告**以及**插屏广告**。各种广告具体效果和用法如下：
### 横幅广告
横幅广告又称为 Banner. 通常展示在 App 页面的顶部或者底部. 广告位默认比例为640：150。在使用该广告之前, 你需要申请横幅广告的广告 ID 820005. 集成横幅广告的简单示例如下:


1. 实例化控件MDSinglePicView
2. 构建广告信息并请求广告。其中广告位必填，其他广告同理。
```
private void requestAd() {
        //广告位宽高比640:150,slotType为广告类型，传3请求非互动广告，传4请求互动广告
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("820005")
                .build();
 new MDAdLoadHelper().requestAd(adSlot, new MDAdLoadHelper.AdRequestListener() {


            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(MDAdModel mdAdModel) {

            }
        });
  ```
3.  requestAd()方法会回调成功和失败两种方法。都是在子线程，其中onSuccess()中已经对线程进行切换，可以直接进行UI操作。onError()中需要开发者自行处理。
4. onSuccess()方法中显示广告。
```
 			@Override
            public void onSuccess(MDAdModel adModel) {
                //show方法有两个重载，可以根据需要决定是否设置监听，其他广告位同理。
                // 这里的回调本质上也是在子线程，但是内部已经做了线程切换处理，可以直接进行UI操作。
                mSinglePicView.show(BannerSinglePicActivity.this, adModel, new MDAdLoadListener() {
                    @Override
                    public void onAdClicked() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告被点击", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdShow() {
                        Toast.makeText(BannerSinglePicActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                        //广告位默认高比为640:150。该方法可以用于快速适配
                        mSinglePicView.setUpWithDefaultScale(true, 0,0,0);
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
```


### 信息流广告
信息流广告用于展示在信息流列表中。sdk中提供横幅广告样式有两种, 分别是上文下图模式 (宽高比为690 : 440）和左图右文模式 (宽高比为210 : 140)。 在使用该广告之前, 你需要申请信息流广告的广告 ID. 集成信息流广告的示例如下:
1. 根据需求实例化控件MDLeftPicWithTextView或者MDBottomPicWthTextView。
2. 构建广告信息并请求广告。
3. 在onSuccess()方法中调用show()方法展示广告。同理，show()方法有两个重载，可以根据需要决定是否设置监听。
4. 信息流广告提供了快速适配的方法。开发者可以在onAdShow()回调里调用setUpWithDefaultScale()方法选择默认比例快速接入。
~~~
	   @Override
                    public void onAdShow() {
                        mBottomPicWithTextView.setUpWithDefaultScale(true,0,12,12);
                        Toast.makeText(InfoFlowBottomPicActivity.this, "广告展示中", Toast.LENGTH_SHORT).show();
                    }
~~~
 5.setUpWithDefaultScale()方法介绍（其他广告位同理）
~~~
/**
     * 广告位快速适配
     *
     * @param isSetUpWithDefaultScale true表示按照默认比例适配。false表示采用XML中定义的宽高。
     * @param width                   自定义的广告位宽度，高度将会根据比例自适应，为0时表示全屏。
     */
    public void setUpWithDefaultScale(boolean isSetUpWithDefaultScale, int width, int paddingLeft, int paddingRight) {
	
~~~
### 开屏广告
开屏广告又通常用于 App 启动或者从一个页面过渡到另一个页面的场景中。你可以使用 MDSplash 类来实现全屏广告的展示。SDK已对不同尺寸屏幕进行了适配。 在使用该广告之前, 你需要申请全屏广告的广告 ID. 集成全屏广告的简单示例如下:
1. 实例化控件MDSinglePicView
2. 在onCreate()方法中延迟发送消息，确保即使开屏广告加载超时也能跳转到主页面。
~~~
	myHandler.sendEmptyMessageDelayed(1, countTime);
~~~
3. 构建广告信息并请求广告。
4. 在onSuccess()方法中调用show()方法展示广告。
### 浮标广告
浮标广告通常展示在页面的边角位置，是五种广告中展示面积最小的一种。你可以使用 MDBuoyView 类来实现浮标广告的展示。SDK 中提供浮标广告尺寸宽高比为 110 : 110. 在使用该广告之前, 你需要申请浮标广告的广告 ID. 集成浮标广告的示例如下:
1. 实例化控件MDBuoyView。
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
	~~~
### 插屏广告
插屏广告用于展示插屏广告. 通常用于 App 屏幕中间展示. 你可以使用 MDInterstitialView 类来实现插屏广告的展示. SDK中提供插屏广告尺寸宽高比为 510 : 510. 在使用该广告之前, 你需要申请插屏广告的广告 ID. 集成插屏广告的简单示例如下:
1. 实例化控件MDInterstitialView 。MDInterstitialView 是一个DialogFragment,不要在XML中添加，而是在java代码中实例化。
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
## 错误码
对于接入过程中的错误码详情, 请查看[错误码文档](https://github.com/ruiyuad/MDAd_android/edit/master/README.md)
## 版本日志
0.1.1: 优化广告展示样式





Q: 广告样式是否可以自定义?
A: 可以. 开发者只需传入需要自定义广告的 ID, 即可获取该广告的图片、标题和广告描述来实现广告UI的自定义。

Q: 接入时出现了 40006, 50001 错误返回. 请问这是什么错误?
A: 下面分别介绍两个错误码含义:

    40006 是当前广告位已经关闭. 该错误一般发生后台重复配置删除同一广告位, 进行广告展示前, 请确定对应的广告位是否开启.
    50001 表示当前广告位广告无法提供.
## 错误码
对于接入过程中的错误码详情, 请查看错误码文档
## 版本日志
0.1.0: 接入五种常用广告.







