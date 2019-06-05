package pro.haichuang.learn.home.ui.activity

import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_web.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity

@ContentView(R.layout.activity_web)
class WebActivity: BaseActivity() {

    override fun initData() {
        web_content.loadUrl(intent.getStringExtra("url"))
    }
}