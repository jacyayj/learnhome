package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nim.uikit.api.UIKitOptions
import com.netease.nim.uikit.api.model.session.SessionCustomization
import com.netease.nim.uikit.api.model.session.SessionEventListener
import com.netease.nim.uikit.business.session.actions.BaseAction
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.util.NIMUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.smtt.sdk.QbSdk
import com.vondear.rxtool.RxFileTool
import com.vondear.rxtool.RxTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import com.zhouyou.http.cookie.CookieManger
import pro.haichuang.learn.home.adapter.CollectViewHolder
import pro.haichuang.learn.home.im.CollectAttachment
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.message.FriendSettingActivity
import pro.haichuang.learn.home.ui.im.CollectAction
import pro.haichuang.learn.home.ui.im.MsgViewHolderTip
import pro.haichuang.learn.home.ui.im.location.NimDemoLocationProvider
import pro.haichuang.learn.home.utils.HttpUtils
import pro.haichuang.learn.home.utils.SPUtils
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    private val path by lazy { RxFileTool.getDiskCacheDir(this) + "/city_list.json" }

    override fun onCreate() {
        super.onCreate()
        if (!RxFileTool.isFileExists(path))
            RxFileTool.saveFile(assets.open("city_list.json"), path)

        RxTool.init(this)
        QbSdk.initX5Environment(this, null)
        EasyHttp.init(this)
        EasyHttp.getInstance()
                .addConverterFactory(GsonConverterFactory.create())
                .debug("http_params", true)
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0)
                .setReadTimeOut(10 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setConnectTimeout(10 * 1000)
                .setCookieStore(CookieManger(this))
                .setCertificates()
                .setBaseUrl(Url.base_url)
        NIMClient.init(this, SPUtils.loginInfo, options())
        initUiKit()
        initRefreshLayout()
    }

    private fun initUiKit() {
        if (NIMUtil.isMainProcess(this)) {
            NIMClient.toggleNotification(true)
            NIMClient.getService(MsgServiceObserve::class.java).observeCustomNotification({
                val content = JsonParser().parse(it.content).asJsonObject
                HttpUtils.updateOrderTime(this, it.sessionId, content.get("orderTime").asString, content.get("orderId").asInt)
            }, true)
            NIMClient.getService(MsgService::class.java).registerCustomAttachmentParser {
                Gson().fromJson(it, CollectAttachment::class.java)
            }
            NimUIKit.registerMsgItemViewHolder(CollectAttachment::class.java, CollectViewHolder::class.java)
            NimUIKit.init(this, UIKitOptions().apply {
                shouldHandleReceipt = false
                isTeacher = SPUtils.isTeacher
            })
            NimUIKit.setSessionListener(object : SessionEventListener {
                override fun onAcceptOrder(context: Context?, orderId: Int, account: String?) {
                    account?.let { HttpUtils.acceptOrder(this@BaseApplication, orderId, it) }
                }

                override fun onAvatarClicked(context: Context?, message: IMMessage?) {
                }

                override fun onAvatarLongClicked(context: Context?, message: IMMessage?) {
                }

                override fun onAckMsgClicked(context: Context?, message: IMMessage?) {
                }
            })
            NimUIKit.setSettingClass(FriendSettingActivity::class.java)
            NimUIKit.setLocationProvider(NimDemoLocationProvider())
            NimUIKit.registerTipMsgViewHolder(MsgViewHolderTip::class.java)
            NimUIKit.setCommonP2PSessionCustomization(SessionCustomization().apply {
                val action = ArrayList<BaseAction>()
                action.add((CollectAction()))
                actions = action
            })
        }
    }

    private fun initRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> ClassicsHeader(context) }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> ClassicsFooter(context) }
    }

    private fun options(): SDKOptions {
        val options = SDKOptions()
        options.appKey = "60999e6f4a584cc1a99b837b06a40653"
        options.checkManifestConfig = true
        options.statusBarNotificationConfig = StatusBarNotificationConfig()
        return options
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}
