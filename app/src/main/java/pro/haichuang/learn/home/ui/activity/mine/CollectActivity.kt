package pro.haichuang.learn.home.ui.activity.mine

import kotlinx.android.synthetic.main.activity_vr.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.adapter.CollectAdapter
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity


@ContentView(R.layout.activity_collect)
class CollectActivity : BaseActivity() {

    override fun initData() {
        listView.adapter = CollectAdapter(this)
    }
}
