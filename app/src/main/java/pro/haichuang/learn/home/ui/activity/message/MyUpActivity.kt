package pro.haichuang.learn.home.ui.activity.message

import com.jacy.kit.adapter.CommonAdapter
import com.jacy.kit.config.ContentView
import kotlinx.android.synthetic.main.activity_my_up.*
import pro.haichuang.learn.home.R
import pro.haichuang.learn.home.config.BaseActivity

@ContentView(R.layout.activity_my_up)
class MyUpActivity : BaseActivity() {
    override fun initData() {
        titleModel.title = "点赞"
        listView.adapter = CommonAdapter(layoutInflater,R.layout.item_my_up, arrayListOf(1,2,3,4,5))
    }
}
