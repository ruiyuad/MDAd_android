
MDAdSDK用于Android端移动应用广告展示，在使用广告服务的同时，我们真诚希望收到大家的反馈。

## 环境支持
		最低支持安卓API 15。
	

## 集成方式
 手动集成：
1. 下载本SDK复制到项目AndroidStudio的libs目录下（如若没有则在app目录下新建一个libs目录）。
2. 在项目的guild.gradle文件里添加依赖
	![在这里插入图片描述](https://img-blog.csdnimg.cn/20191213105404979.png)
## 全局配置
- 代码混淆配置：
 如果您需要使用 proguard 混淆代码，需确保不要混淆 SDK 的代码。 请在 proguard.cfg 文件(或其他混淆文件)尾部添加如下配置:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191217210631416.png)
- 安卓P适配
	由于Android P 将禁止 App 使用所有未加密的连接，如果未对APP进行适配会可能会导致落地页加载失败。适配方法：
	在manifest的application标签中加入 android:usesCleartextTraffic="true"。
	
## 初始化



使用本SDK前请先确认已注册 AppKey 和 AppSecret. 如有疑问, 请联系我们 admin@mobiw.com 或者当前 github 账号。
1. 如已注册 AppKey 和 AppSecret, 请在应用的application中做如下初始化：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191217211101672.jpg)
2. 为更好的享受我们的广告服务，需要在AndroidManifest.xml文件里配置以下权限和provider
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191217204753691.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZm9yZW50ODg4,size_16,color_FFFFFF,t_70)
3. 在manifest文件里配置provider,并在res目录下新建xml目录，然后建立file_paths文件。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191213110525633.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZm9yZW50ODg4,size_16,color_FFFFFF,t_70)
## 广告样式
目前支持5种广告样式，分别是**横幅广告**、**信息流广告**、**开屏广告**、**浮标广告**以及**插屏广告**。每种广告可以选择接入互动广告或者非互动广告，各种广告具体效果和用法如下：
### 横幅广告
横幅广告又称为 Banner. 通常展示在 App 页面的顶部或者底部. 广告位默认比例为640：150。在使用该广告之前, 你需要申请横幅广告的广告 ID 820005. 集成横幅广告的简单示例如下:


1. 实例化控件MDSinglePicView
2. 构建广告信息并请求广告。其中广告位必填，slotType不填默认表示请求非互动广告，其他广告同理。 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20191213110856806.png)
3.  requestAd()方法会回调成功和失败两种方法。都是在子线程，其中onSuccess()中已经对线程进行切换，可以直接进行UI操作。onError()中需要开发者自行处理。
4. onSuccess()方法中显示广告。![在这里插入图片描述](https://img-blog.csdnimg.cn/20191217205101941.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZm9yZW50ODg4,size_16,color_FFFFFF,t_70)



### 信息流广告
信息流广告用于展示在信息流列表中。sdk中提供横幅广告样式有两种, 分别是上文下图模式 (宽高比为690 : 440）和左图右文模式 (宽高比为210 : 140)。 在使用该广告之前, 你需要申请信息流广告的广告 ID. 集成信息流广告的示例如下:
1. 根据需求实例化控件MDLeftPicWithTextView或者MDBottomPicWthTextView。
2. 构建广告信息并请求广告。
3. 在onSuccess()方法中调用show()方法展示广告。同理，show()方法有两个重载，可以根据需要决定是否设置监听。
4. 信息流广告提供了快速适配的方法。开发者可以在onAdShow()回调里调用setUpWithDefaultScale()方法选择默认比例快速接入。![在这里插入图片描述](https://img-blog.csdnimg.cn/20191217205332698.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZm9yZW50ODg4,size_16,color_FFFFFF,t_70)
### 开屏广告
开屏广告又通常用于 App 启动或者从一个页面过渡到另一个页面的场景中。你可以使用 MDSplash 类来实现全屏广告的展示。SDK已对不同尺寸屏幕进行了适配。 在使用该广告之前, 你需要申请全屏广告的广告 ID. 集成全屏广告的简单示例如下:
1. 实例化控件MDSinglePicView
2. 在onCreate()方法中延迟发送消息，确保即使开屏广告加载超时也能跳转到主页面。![在这里插入图片描述](https://img-blog.csdnimg.cn/20191212144156520.png)
3. 构建广告信息并请求广告。
4. 在onSuccess()方法中调用show()方法展示广告。
### 浮标广告
浮标广告通常展示在页面的边角位置，是五种广告中展示面积最小的一种。你可以使用 MDBuoyView 类来实现浮标广告的展示。SDK 中提供浮标广告尺寸宽高比为 110 : 110. 在使用该广告之前, 你需要申请浮标广告的广告 ID. 集成浮标广告的示例如下:
1. 实例化控件MDBuoyView。
2. 构建广告信息并请求广告。
3. 在onSuccess()方法中调用show()方法展示广告。
	![在这里插入图片描述](https://img-blog.csdnimg.cn/20191213112321387.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpZm9yZW50ODg4,size_16,color_FFFFFF,t_70)
### 插屏广告
插屏广告用于展示插屏广告. 通常用于 App 屏幕中间展示. 你可以使用 MDInterstitialView 类来实现插屏广告的展示. SDK中提供插屏广告尺寸宽高比为 510 : 510. 在使用该广告之前, 你需要申请插屏广告的广告 ID. 集成插屏广告的简单示例如下:
1. 实例化控件MDInterstitialView 。MDInterstitialView 是一个DialogFragment,不要在XML中添加，而是在java代码中实例化。
			mMDInterstitialView = new MDInterstitialView();
2. 构建广告信息并请求广告。 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20191213112225762.png)
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
对于接入过程中的错误码详情, 请查看错误码文档
## 版本日志
0.1.0: 接入五种常用广告.







