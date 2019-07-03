package pro.haichuang.learn.home.ui.activity

import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_web.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity

@ContentView(R.layout.activity_web)
class WebActivity : BaseActivity() {

    override fun initData() {
        when {
            intent.getBooleanExtra("user", false) -> {
                web_content.loadUrl("file:///android_asset/user.html")
                web_content.settings.textZoom = 250
            }
            intent.getBooleanExtra("private", false) -> {
                web_content.loadUrl("file:///android_asset/private.html")
                web_content.settings.textZoom = 250
            }
            else -> web_content.loadUrl(intent.getStringExtra("url"))
        }
    }
}