package pro.haichuang.learn.home.ui.activity.index

import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_liu_xue_zi_xun)
class LiuXueZiXunActivity : BaseActivity() {

    override fun initData() {
        titleModel.title = "免费咨询"
    }

}
