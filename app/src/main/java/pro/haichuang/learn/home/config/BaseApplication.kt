package pro.haichuang.learn.home.config

import android.app.Application
import android.content.Context
//import android.support.multidex.MultiDex
import com.vondear.rxtool.RxTool

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxTool.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(base)
    }
}
