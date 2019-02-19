package pro.haichuang.learn.home.ui.activity.mine

import com.jacy.kit.config.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_help)
class HelpActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "用户帮助"
    }

}
