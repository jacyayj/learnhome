package pro.haichuang.learn.home.ui.activity.mine

import pro.haichuang.learn.home.R
import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity

@ContentView(R.layout.activity_ti_xian)
class TiXianActivity : BaseActivity() {
    override fun initData() {
        titleModel.title = "提现"
    }
}
