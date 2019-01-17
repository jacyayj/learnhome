package pro.haichuang.learn.home.ui.activity.mine

import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.DataBindingActivity
import pro.haichuang.learn.home.ui.activity.mine.viewmodel.BindVipModel
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_bind_vip)
class BindVipActivity : DataBindingActivity<BindVipModel>() {

    override fun initData() {
        titleModel.title = "绑定新卡"
    }
}
