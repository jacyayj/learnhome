package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
//import android.support.multidex.MultiDex
import com.vondear.rxtool.RxTool
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpParams
import pro.haichuang.learn.home.net.CustomInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxTool.init(this)
        EasyHttp.init(this)
        EasyHttp.getInstance()
                .debug("http_params", true)
                .addInterceptor(CustomInterceptor())
                .setBaseUrl("http://118.24.80.29:8080/learn-home-server/api/app/")
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(base)
    }
}
