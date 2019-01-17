package pro.haichuang.learn.home.ui.activity.index

import com.jacy.kit.adapter.CommonAdapter
import kotlinx.android.synthetic.main.activity_online_video.*
import pro.haichuang.learn.home.annotation.ContentView
import pro.haichuang.learn.home.config.BaseActivity
import pro.haichuang.learn.home.R


@ContentView(R.layout.activity_online_video)
class OnlineVideoActivity : BaseActivity() {

    override fun initData() {
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_online_video, arrayListOf(1,2,3,4,5,6))
    }
}
