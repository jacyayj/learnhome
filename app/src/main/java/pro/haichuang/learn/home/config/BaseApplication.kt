package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.netease.nim.uikit.api.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.util.NIMUtil
import com.vondear.rxtool.RxTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import pro.haichuang.learn.home.net.CustomInterceptor
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
                .setBaseUrl("http://118.24.80.29:8080/learn-home-server/api/app/")
        NIMClient.init(this,null,options())

        if (NIMUtil.isMainProcess(this))
            NimUIKit.init(this)
    }

    private fun initUiKit(){

    }

    private fun options(): SDKOptions {
        val options = SDKOptions()
        options.appKey="3283d25ee2b13df2312d0741028de692"
        options.checkManifestConfig = true
        options.statusBarNotificationConfig = StatusBarNotificationConfig()
        return options
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}
