package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_vrdetails.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity

@ContentView(R.layout.activity_vrdetails)
class VRDetailsActivity : BaseActivity() {


    override fun initData() {
        titleModel.title = intent.getStringExtra("name") + "全景"
        content.loadUrl("https://720yun.com/scene/29708074")
    }
}
