package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nim.uikit.api.UIKitOptions
import com.netease.nim.uikit.api.model.location.LocationProvider
import com.netease.nim.uikit.api.model.session.SessionCustomization
import com.netease.nim.uikit.business.session.actions.BaseAction
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.util.NIMUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.vondear.rxtool.RxTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import pro.haichuang.learn.home.net.Url
import pro.haichuang.learn.home.ui.activity.message.FriendSettingActivity
import pro.haichuang.learn.home.ui.im.CollectAction
import pro.haichuang.learn.home.utils.SPUtils
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxTool.init(this)
        EasyHttp.init(this)
        EasyHttp.getInstance()
                .addConverterFactory(GsonConverterFactory.create())
                .debug("http_params", true)
                .setCacheMode(CacheMode.NO_CACHE)
                .setBaseUrl(Url.base_url)
        NIMClient.init(this, SPUtils.loginInfo, options())
        initUiKit()
        initRefreshLayout()
    }

    private fun initUiKit() {
        if (NIMUtil.isMainProcess(this)) {
            NimUIKit.init(this, UIKitOptions().apply {

            })
            NimUIKit.setSettingClass(FriendSettingActivity::class.java)
            NimUIKit.setLocationProvider(object :LocationProvider{
                override fun requestLocation(context: Context?, callback: LocationProvider.Callback?) {

                }

                override fun openMap(context: Context?, longitude: Double, latitude: Double, address: String?) {

                }
            })
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
