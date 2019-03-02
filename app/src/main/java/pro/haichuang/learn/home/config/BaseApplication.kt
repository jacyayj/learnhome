package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.netease.nim.uikit.api.NimUIKit
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
import com.zhouyou.http.cookie.CookieManger
import pro.haichuang.learn.home.net.CustomInterceptor
import pro.haichuang.learn.home.net.Url
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxTool.init(this)
        EasyHttp.init(this)
        EasyHttp.getInstance()
                .addConverterFactory(GsonConverterFactory.create())
                .debug("http_params", true)
                .addInterceptor(CustomInterceptor())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCookieStore(CookieManger(this))
                .setBaseUrl(Url.base_url)
        NIMClient.init(this, null, options())
        initUiKit()
        initRefreshLayout()
    }

    private fun initUiKit() {
        if (NIMUtil.isMainProcess(this))
            NimUIKit.init(this)
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
