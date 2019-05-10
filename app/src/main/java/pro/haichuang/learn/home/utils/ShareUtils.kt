package pro.haichuang.learn.home.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.alipay.sdk.app.PayTask
import com.jacy.kit.config.toast
import com.tencent.connect.UserInfo
import com.tencent.connect.share.QQShare
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.vondear.rxtool.RxAppTool
import com.vondear.rxtool.RxFileTool
import com.vondear.rxtool.RxImageTool
import com.vondear.rxtool.RxTool
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.Constants
import java.io.File


object ShareUtils {
    val wxApi by lazy {
        WXAPIFactory.createWXAPI(RxTool.getContext(), "wx9287620517c416a8", true).apply {
            registerApp("wx9287620517c416a8")
        }
    }


    private const val PACKAGE_WECHAT = "com.tencent.mm"//微信
    private const val PACKAGE_MOBILE_QQ = "com.tencent.mobileqq"//qq

    private val qqApi by lazy { Tencent.createInstance("1108180837", RxTool.getContext()) }

    /**
     * 分享单张图片到微信好友
     *
     * @param context context
     * @param picFile 要分享的图片文件
     */
    fun sharePictureToWechatFriend(context: Activity?, picFile: File?) {
        if (RxAppTool.isInstallApp(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val cop = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareImgUI")
            intent.component = cop
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            if (picFile != null) {
                if (picFile.isFile && picFile.exists()) {
                    val uri = RxFileTool.getImageContentUri(context, picFile)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                }
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivityForResult(Intent.createChooser(intent, "sharePictureToWechatFriend"), 0x01)
        } else {
            toast("请先安装微信客户端")
        }
    }

    /**
     * 分享单张图片到微信朋友圈
     *
     * @param context context
     * @param picFile 要分享的图片文件
     */
    fun sharePictureToWechatMomments(context: Activity?, picFile: File?) {
        if (RxAppTool.isInstallApp(context, PACKAGE_WECHAT)) {
            val intent = Intent()
            val cop = ComponentName(PACKAGE_WECHAT, "com.tencent.mm.ui.tools.ShareToTimeLineUI")
            intent.component = cop
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            if (picFile != null) {
                if (picFile.isFile && picFile.exists()) {
                    val uri = RxFileTool.getImageContentUri(context, picFile)
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                }
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivityForResult(Intent.createChooser(intent, "sharePictureToWechatMomments"),0x01)
        } else {
            toast("请先安装微信客户端")
        }
    }

    /**
     * 分享单张图片到QQ好友
     *
     * @param context conrtext
     * @param picFile 要分享的图片文件
     */
    fun sharePictureToQQFriend(context: Activity?, picFile: File) {
        if (RxAppTool.isInstallApp(context, PACKAGE_MOBILE_QQ)) {
            val shareIntent = Intent()
            val componentName = ComponentName(PACKAGE_MOBILE_QQ, "com.tencent.mobileqq.activity.JumpActivity")
            shareIntent.component = componentName
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "image/*"
            val uri = RxFileTool.getImageContentUri(context, picFile)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // 遍历所有支持发送图片的应用。找到需要的应用
            context?.startActivityForResult(Intent.createChooser(shareIntent, "shareImageToQQFriend"),0x01)
        } else {
            toast("请先安装QQ客户端")
        }
    }

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

    fun shareToMomments(context: Activity, title: String, url: String, content: String) {

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

    fun setOpenId(openid: String, token: String, expires: String) {
        qqApi.openId = openid
        qqApi.setAccessToken(token, expires)
    }

    fun fetchQQInfo(context: Activity, response: (info: String) -> Unit) {
        val usetInfo = UserInfo(context, qqApi.qqToken)
        usetInfo.getUserInfo(object : IUiListener {
            override fun onComplete(p0: Any?) {
                response(p0?.toString() ?: "")
            }

            override fun onCancel() {
            }

            override fun onError(p0: UiError?) {
            }
        })
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