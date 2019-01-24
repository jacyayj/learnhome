package pro.haichuang.learn.home.ui.activity.mine

import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_join_group)
class JoinGroupActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "加入社群"
    }
}
