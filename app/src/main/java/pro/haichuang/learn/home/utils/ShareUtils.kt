package pro.haichuang.learn.home.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.alipay.sdk.app.PayTask
import com.tencent.connect.share.QQShare
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.vondear.rxtool.RxImageTool
import com.vondear.rxtool.RxTool
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants


object ShareUtils {
    val wxApi by lazy {
        WXAPIFactory.createWXAPI(RxTool.getContext(), "wx9287620517c416a8", true).apply {
            registerApp("wx9287620517c416a8")
        }
    }

    val qqApi by lazy { Tencent.createInstance("1108180837", RxTool.getContext()) }

    fun shareToQQ(context: Activity, title: String, url: String, content: String) {
        val bundle = Bundle()
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url)
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title)
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg")
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, content)
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "升学之家")
        qqApi.shareToQQ(context, bundle, null)
    }

    fun shareToWx(context: Activity, title: String, url: String, content: String) {

        //初始化一个WXWebpageObject，填写url
        val webpage = WXWebpageObject()
        webpage.webpageUrl = url

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = title
        msg.description = content
        val thumbBmp = BitmapFactory.decodeResource(context.resources, R.drawable.cuow)
        msg.thumbData = RxImageTool.bitmap2Bytes(thumbBmp, Bitmap.CompressFormat.PNG)

        //构造一个Req
        val req = SendMessageToWX.Req()
        req.transaction = "webpage"
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneSession

        //调用api接口，发送数据到微信
        wxApi.sendReq(req)
    }

    fun shareToCircle(context: Activity) {

        //初始化一个WXWebpageObject，填写url
        val webpage = WXWebpageObject()
        webpage.webpageUrl = "www.baidu.com"

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage(webpage)
        msg.title = "网页标题 "
        msg.description = "网页描述"
        val thumbBmp = BitmapFactory.decodeResource(context.resources, R.drawable.cuow)
        msg.thumbData = RxImageTool.bitmap2Bytes(thumbBmp, Bitmap.CompressFormat.PNG)

        //构造一个Req
        val req = SendMessageToWX.Req()
        req.transaction = "webpage"
        req.message = msg
        req.scene = SendMessageToWX.Req.WXSceneTimeline

        //调用api接口，发送数据到微信
        wxApi.sendReq(req)
    }

    fun loginToQQ(activity: Activity, listener: IUiListener) {
        qqApi.login(activity, "all", listener)
    }

    fun loginToWx() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_sdk_demo_test"
        wxApi.sendReq(req)
    }

    /**
     * 调起微信支付的方法
     */
    fun toWXPay(request: PayReq) {
        Thread(Runnable {
            wxApi.sendReq(request)//发送调起微信的请求
        }).start()
    }

    /**
     * 调起微信支付的方法
     */
    fun toAliPay(orderInfo: String, context: Activity, handler: Handler) {
        Thread(Runnable {
            val payTask = PayTask(context)
            val msg = Message()
            msg.what = Constants.ALIPAY
            msg.obj = payTask.payV2(orderInfo, true)
            handler.sendMessage(msg)
        }).start()
    }
}